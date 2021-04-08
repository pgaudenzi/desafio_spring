package com.desafio.spring.repositories;

import com.desafio.spring.dtos.UserDto;
import com.desafio.spring.exceptions.ExistingUserException;
import com.desafio.spring.exceptions.UserNotFoundException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation to store and get the users.
 */
@Service
public class UserRepositoryImpl implements UserRepository {

    private final String usersDbPath = "/src/main/resources/dbUsers.csv";
    private final String absPath = new File("").getAbsolutePath();

    /**
     * Write a new user in the csv file
     * @param user
     * @throws IOException
     * @throws ExistingUserException
     */
    @Override
    public void addNewUser(UserDto user) throws IOException, ExistingUserException {
        if (!exists(user.getDni())) {
            String[] userData = parseUserData(user);
            FileWriter file = new FileWriter(absPath + usersDbPath, true);
            CSVWriter writer = new CSVWriter(file);
            writer.writeNext(userData);
            writer.close();
        } else {
            throw new ExistingUserException();
        }

    }

    /**
     * Find a user by dni
     * @param dni
     * @return the user found
     * @throws UserNotFoundException
     */
    @Override
    public UserDto findUserByDni(String dni) throws UserNotFoundException {
        UserDto user = getUser(dni);
        if (user == null) throw new UserNotFoundException(dni);
        return user;
    }

    /**
     * @return all users
     */
    @Override
    public List<UserDto> getAll() {
        return loadDatabase();
    }

    /**
     * Validates if the user already exists
     * @param dni
     * @return
     */
    @Override
    public boolean exists(String dni) {
        UserDto user = getUser(dni);
        return user != null;
    }

    private UserDto getUser(String dni) {
        List<UserDto> users = loadDatabase();
        UserDto result = null;
        for (UserDto user : users) {
            if (user.getDni().equalsIgnoreCase(dni)) {
                result = user;
                break;
            }
        }
        return result;
    }

    private List<UserDto> loadDatabase() {
        List<UserDto> users = new ArrayList<>();
        try {
            FileReader file = new FileReader(absPath + usersDbPath);
            CSVReader reader = new CSVReader(file);
            String[] row;
            while ((row = reader.readNext()) != null) {
                users.add(objectMapper(row));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Aux method to convert a csv line to an object
     * @param data
     * @return
     */
    private UserDto objectMapper(String[] data) {
        String dni, name, province;

        dni = data[0];
        name = data[1];
        province = data[2];

        return new UserDto(dni, name, province);
    }

    /**
     * Aux method to convert an object in a csv line.
     * @param user
     * @return
     */
    private String[] parseUserData(UserDto user) {
        String dni, name, province;

        dni = user.getDni();
        name = user.getName();
        province = user.getProvince();

        return new String[] {dni, name, province};
    }

}
