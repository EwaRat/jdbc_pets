package com.sda.javapoz24.dao;

import com.sda.javapoz24.model.Pet;
import com.sda.javapoz24.model.Race;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class PetDao {

        private MysqlDBConnection connector;

        public PetDao(MysqlDBConnection connection) {
            this.connector = connection;
            createDatabaseAndTable();
        }

        private void createDatabaseAndTable() {
            try {
                Connection connection = connector.createConnection();
                PreparedStatement statement = connection.prepareStatement(PetQuerries.CREATE_TABLE);
                statement.execute();  //wywolanie powyzszego zapytania - stworzenie tabeli
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        //
        public void insertPet(Pet pet) {
            // dzięki takiemu zapisowi, obiekt connection wywoła metodę close przed zakończeniem/zamykającą klamrą try
            try (Connection connection = connector.createConnection()) {
                // drugi parametr mówi, że po wstawieniu rekrdu spodziewamy się otrzymać wygenerowane ID
                PreparedStatement preparedStatement = connection.prepareStatement(PetQuerries.INSERT_PET, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, pet.getName());
                preparedStatement.setInt(2, pet.getAge());
                preparedStatement.setString(3, pet.getOwnerName());
                preparedStatement.setDouble(4, pet.getWeight());
                preparedStatement.setBoolean(5, pet.isPureRace());  // przy boolean isc a nie get
                preparedStatement.setString(6, pet.getRace().toString());
                preparedStatement.execute();

                int affectedRecords = preparedStatement.executeUpdate();
                // affectedRecords - ile rekordów zostało zmienionych
                System.out.println("Dodanych rekordów: " + affectedRecords);
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if(generatedKeys.next()){
                    Long identifier = generatedKeys.getLong(1);
                    pet.setId(identifier);
                    System.out.println("Generated id: " + pet.getId());

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        //R
        public List<Pet> getAllPets() {
            List<Pet> pets = new ArrayList<>();
            try (Connection connection = connector.createConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(PetQuerries.SELECT_PETS);
                ResultSet rekordy = preparedStatement.executeQuery();
                // dopóki są rekordy
                while (rekordy.next()) {
                    Pet pet = new Pet();
                    pet.setId(rekordy.getLong(1));
                    pet.setName(rekordy.getString(2));
                    pet.setAge(Integer.parseInt(rekordy.getString(3)));
                    pet.setOwnerName(rekordy.getString(4));
                    pet.setWeight(rekordy.getDouble(5));
                    pet.setRace(Race.valueOf(rekordy.getString(6)));
                    // ^^ załadowanie wartości z kolumn do obiektu
                    // umieszczenie obiektu w liście:
                   pets.add(pet);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return pets;
        }
        //D
        public void deletePet(long identifier){
            try (Connection connection = connector.createConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(PetQuerries.DELETE_PET);
                preparedStatement.setLong(1, identifier); //wstawiamy identyfikator usuwanego rekrodu
                int affectedRecords = preparedStatement.executeUpdate();
                System.out.println("Usuniętych rekordów: " + affectedRecords);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        //TODO update

    }
