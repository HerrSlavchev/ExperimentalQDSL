package com.example;

import com.example.model.Hero;
import com.example.model.HeroRepository;
import com.example.model.Item;
import com.example.model.QHero;
import com.example.model.QItem;
import com.example.model.Skill;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicApplicationTests {

    @Autowired
    HeroRepository repository;
    
    @Test
    public void seeWhat() {
        
        String[] peshoItemTitles = {"gloves", "boots", "helmet", "ring"};
        int[] peshoItemLevels = {7, 5, 2, 9};
        insertRecord("pesho", peshoItemLevels, peshoItemTitles);
        
        String[] goshoItemTitles = {"cape", "helmet"};
        int[] goshoItemLevels = {11, 8};
        insertRecord("gosho", goshoItemLevels, goshoItemTitles);
        
        QHero parent = QHero.hero;
        QItem child = QItem.item;
        
        Predicate pred = JPAExpressions
                .selectOne()
                .from(child)
                .where( 
                      child.hero.id.eq(parent.id),
                      child.title.eq("helmet"),
                      child.level.goe(4)
                ).exists();
        
        Iterable<Hero> res = repository.findAll(pred, new PageRequest(0, 25));
        res.forEach(t -> System.out.println(t.getNickname()));
    }
        
    private void insertRecord(String nickname, int[] itemLevels, String[] itemTitles) {
        Hero hero = new Hero();
        hero.setNickname(nickname);
        Set<Item> items = new HashSet();
        for (int i = 0; i < itemLevels.length; i++) {
            Item item = new Item();
            item.setLevel(itemLevels[i]);
            item.setTitle(itemTitles[i]);
            item.setHero(hero);
            items.add(item);
        }
        hero.setItems(items);
        
        
        String[] skillTitles = {"fly", "armored"};
        Set<Skill> skills = new HashSet();
        for (int i = 0; i < skillTitles.length; i++) {
            Skill skill = new Skill();
            skill.setTitle(skillTitles[i]);
            skill.setLevel(2 - i);
            skill.setHero(hero);
            skills.add(skill);
        }
        hero.setSkills(skills);
        
        repository.saveAndFlush(hero);
    }    

}
