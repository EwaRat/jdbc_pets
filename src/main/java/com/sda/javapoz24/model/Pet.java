package com.sda.javapoz24.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    private String name;
    private int age;
    private String ownerName;
    private double weight;
    private boolean pureRace;
    private Race race;

}
