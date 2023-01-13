package com.example.guisocialnetwork.repository.FileRepository;

import com.example.guisocialnetwork.domain.User;
import com.example.guisocialnetwork.exceptions.RepositoryException;
import com.example.guisocialnetwork.repository.InMemoryRepository.UserInMemoryRepository;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class UserFileRepository extends UserInMemoryRepository {
    private final String fileName;

    public UserFileRepository(String fileName) {
        this.fileName = fileName;
        loadData();
    }
    private void loadData() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> attributes = Arrays.asList(line.split(";"));
                User user = new User(Long.parseLong(attributes.get(0)),attributes.get(1),attributes.get(2),attributes.get(3), attributes.get(4), attributes.get(5));
                boolean ok = true;
                for(User user1 : super.getAll())
                    if (user1.equals(user)) {
                        ok = false;
                        break;
                    }
                if(ok)
                    super.add(user);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void writeToFile(Iterable<User> entities) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, false))){
            for(User e : entities) {
                String line = e.getId() + ";" + e.getPassword() + ";" + e.getFirstName()+ ";" + e.getLastName()+";" + e.getEmail() + ";" + e.getPassword() + ";";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void appendToFile(User entity) {
        String line = entity.getId() + ";" + entity.getUsername() + ";" + entity.getFirstName() + ";" + entity.getLastName() + ";" + entity.getEmail() + ";" + entity.getPassword() + ";";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true))){
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findOne(Long id) throws RepositoryException, IllegalArgumentException {
        loadData();
        return super.findOne(id);
    }
    @Override
    public Iterable<User> getAll() {
        loadData();
        return super.getAll();
    }
    @Override
    public void add(User user) throws RepositoryException, IllegalArgumentException {
        loadData();
        super.add(user);
        appendToFile(user);
    }
    @Override
    public void delete(Long id) throws RepositoryException, IllegalArgumentException {
        loadData();
        super.delete(id);
        writeToFile(super.getAll());
    }
    @Override
    public void update(User user) throws RepositoryException, IllegalArgumentException {
        loadData();
        super.update(user);
        writeToFile(super.getAll());
    }
    @Override
    public int size() {
        return super.size();
    }
}
