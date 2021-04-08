package com.desafio.spring.services;

import com.desafio.spring.dtos.UserDto;
import com.desafio.spring.exceptions.ExistingUserException;
import com.desafio.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public void createUser(UserDto user) throws IOException, ExistingUserException {
        if (user.getDni() == null || user.getName() == null || user.getProvince() == null) {
            throw new IllegalArgumentException("No se puede dar de alta un nuevo cliente si no " +
                    "se tienen todos los datos del mismo");
        }
        repository.addNewUser(user);
    }

    @Override
    public List<UserDto> getAllUsers(String province) {
        if (province != null) {
            return filterUsersByProvince(repository.getAll(), province);
        }
        return repository.getAll();
    }

    private List<UserDto> filterUsersByProvince(List<UserDto> users, String province) {
        return users.stream()
                .filter(user -> user.getProvince().equalsIgnoreCase(province))
                .collect(Collectors.toList());
    }
}
