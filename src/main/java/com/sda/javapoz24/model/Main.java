package com.sda.javapoz24.model;


import com.sda.javapoz24.dao.MysqlDBConnection;
import com.sda.javapoz24.dao.PetDao;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        PetDao dao = new PetDao(new MysqlDBConnection());

        Scanner scanner = new Scanner(System.in);
        String command;
        do {
            System.out.println("Podaj komendę [insert,list,delete]");
            command = scanner.nextLine();

            //todo dodac metode update

            if (command.startsWith("insert")) {// insert Paweł Gaweł 20 true MALE
                // 0     1     2     3  4    5
                String[] words = command.split(" ");
                Pet pet = Pet.builder()
                        .name(words[1])
                        .age(Integer.parseInt(words[2]))
                        .ownerName(words[3])
                        .weight(Double.parseDouble(words[4]))
                        .pureRace(Boolean.parseBoolean(words[5]))
                        .race(Race.valueOf(words[6].toUpperCase()))
                        .build();
                dao.insertPet(pet);
            } else if (command.startsWith("list")) {
                // list
                List<Pet> list = dao.getAllPets();
                System.out.println("Rekordy: ");
                list.forEach(System.out::println); // wypisz rekordy na ekran (linia pod linią)
                System.out.println(); // dopisz jedną linię odstępu
            }else if(command.startsWith("delete")){
                // delete 1
                String[] words = command.split(" ");
                dao.deletePet(Long.parseLong(words[1]));
            }
        } while (!command.equalsIgnoreCase("quit"));
    }
}



