package com.saad.chatserver.service;

import com.google.common.hash.Hashing;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.saad.chatserver.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import static com.saad.chatserver.service.CsvFiles.USERS_FILE;

public class UserService {

    public User searchForUsername(String searchUsername) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(USERS_FILE));
            ColumnPositionMappingStrategy<User> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(User.class);
            strategy.setColumnMapping("username", "password", "noc");
            CsvToBean csvToBean = new CsvToBeanBuilder(reader).withType(User.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            for (User csvUser : (Iterable<User>) csvToBean) {
                if (csvUser.getUsername().equals(searchUsername)) {
                    System.out.println(csvUser.toString() + " Logged in.");
                    return csvUser;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User searchForUsernameAndPassword(String searchUsername, String searchPassword) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(USERS_FILE));
            ColumnPositionMappingStrategy<User> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(User.class);
            strategy.setColumnMapping("username", "password", "noc");
            CsvToBean csvToBean = new CsvToBeanBuilder(reader).withType(User.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            for (User csvUser : (Iterable<User>) csvToBean) {
                if (csvUser.getUsername().equals(searchUsername) && csvUser.getPassword().equals(hash(searchPassword))) {
                    System.out.println(csvUser.toString() + " Logged in.");
                    return csvUser;
                }
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User createNewUser(String username, String password) {
        try {
            FileWriter fileWriter = new FileWriter(USERS_FILE, true);
            CSVWriter csvWriter = new CSVWriter(fileWriter,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            csvWriter.writeNext(new String[]{username, hash(password), "0"});
            csvWriter.flushQuietly();
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setNoc(0);
            return user;
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String hash(String password) throws NoSuchAlgorithmException {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }
}
