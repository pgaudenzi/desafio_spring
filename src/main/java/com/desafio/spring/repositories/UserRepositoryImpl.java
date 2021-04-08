package com.desafio.spring.repositories;

import com.desafio.spring.dtos.UserDto;
import com.desafio.spring.exceptions.ExistingUserException;
import com.desafio.spring.exceptions.UserNotFoundException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserRepositoryImpl implements UserRepository {

    @Override
    public void addNewUser(UserDto user) throws IOException, ExistingUserException {
        if (!exists(user.getDni())) {
            String[] userData = parseUserData(user);
            FileWriter file = new FileWriter("src/main/resources/dbUsers.csv", true);
            CSVWriter writer = new CSVWriter(file);
            writer.writeNext(userData);
            writer.close();
        } else {
            throw new ExistingUserException();
        }

    }

    @Override
    public UserDto findUserByDni(String dni) throws UserNotFoundException {
        UserDto user = getUser(dni);
        if (user == null) throw new UserNotFoundException(dni);
        return user;
    }

    @Override
    public List<UserDto> getAll() {
        return loadDatabase();
    }

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
            FileReader file = new FileReader("src/main/resources/dbUsers.csv");
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

    private UserDto objectMapper(String[] data) {
        String dni, name, province, id;

        dni = data[0];
        name = data[1];
        province = data[2];

        return new UserDto(dni, name, province);
    }

    private String[] parseUserData(UserDto user) {
        String id, dni, name, province;

        dni = user.getDni();
        name = user.getName();
        province = user.getProvince();

        return new String[] {dni, name, province};
    }

}
