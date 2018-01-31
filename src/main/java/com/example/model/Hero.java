/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.BatchSize;


@Entity
public class Hero {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String nickname;
    
    @BatchSize(size = 50)
    @OneToMany(mappedBy = "hero", fetch = FetchType.EAGER, cascade = CascadeType.ALL)    
    private Set<Item> items;
    
    @BatchSize(size = 50)
    @OneToMany(mappedBy = "hero", fetch = FetchType.EAGER, cascade = CascadeType.ALL)    
    private Set<Skill> skills;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }
}
