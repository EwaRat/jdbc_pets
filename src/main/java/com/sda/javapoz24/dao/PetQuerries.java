package com.sda.javapoz24.dao;

public interface PetQuerries {

    public static final String CREATE_TABLE =
            "create table if not exists `pet`  (\n" +
                    "`id` integer not null primary key auto_increment,\n" +
                    "`name` varchar(255),\n" +
                    "`age` integer,\n" +
                    "`ownerName` varchar(255),\n" +
                    "`weight` double, \n" +
                    "`pureRace` boolean,\n" +
                    "`race` varchar(255));";

    public static final String INSERT_PET =
            "INSERT INTO `pet`\n" +
                    "(`name`, `age`, `ownerName`, `weight`, `pureRace`, `race`) VALUES (?, ?, ?, ?, ?,?);";


    public static final String SELECT_PETS =
            "SELECT * from `pet``;";

    public static final String DELETE_PET =
            "DELETE FROM `pet` WHERE `id`=?;";

    // TODO - dopisac metode update
}
