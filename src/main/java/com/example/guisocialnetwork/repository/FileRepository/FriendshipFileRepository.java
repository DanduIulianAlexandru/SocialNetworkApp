package com.example.guisocialnetwork.repository.FileRepository;

import com.example.guisocialnetwork.domain.Friendship;
import com.example.guisocialnetwork.exceptions.RepositoryException;
import com.example.guisocialnetwork.repository.InMemoryRepository.FriendshipInMemoryRepository;


import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class FriendshipFileRepository extends FriendshipInMemoryRepository {
    private final String fileName;

    public FriendshipFileRepository(String fileName) {
        this.fileName = fileName;
        loadData();
    }
    private void writeToFile(Iterable<Friendship> entities) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, false))){
            for(Friendship friendship : entities) {
                String line = friendship.getId() + ";" + friendship.getFirstFriend()+";" + friendship.getSecondFriend()+";" + friendship.getDateTime() + ";";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void appendToFile(Friendship entity) {
        String line = entity.getId() + ";" + entity.getFirstFriend() + ";"+entity.getSecondFriend() + ";" + entity.getDateTime() + ";";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true))){
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadData() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> attributes = Arrays.asList(line.split(";"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                LocalDateTime friendsFrom = LocalDateTime.parse(attributes.get(3), formatter);
                Friendship friendship = new Friendship(Long.parseLong(attributes.get(0)), Long.parseLong(attributes.get(1)), Long.parseLong(attributes.get(2)), friendsFrom);
                boolean ok = true;
                for(Friendship friendship1 : super.getAll())
                    if (friendship1.equals(friendship)) {
                        ok = false;
                        break;
                    }
                if(ok)
                    super.add(friendship);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public Friendship findOne(Long id) throws RepositoryException, IllegalArgumentException {
        loadData();
        return super.findOne(id);
    }
    @Override
    public Iterable<Friendship> getAll() {
        loadData();
        return super.getAll();
    }
    @Override
    public void add(Friendship friendship) throws RepositoryException, IllegalArgumentException {
        loadData();
        super.add(friendship);
        appendToFile(friendship);
    }
    @Override
    public void delete(Long id) throws RepositoryException, IllegalArgumentException {
        loadData();
        super.delete(id);
        writeToFile(super.getAll());
    }
    @Override
    public int size() {
        return super.size();
    }

    @Override
    public void update(Friendship friendship) throws RepositoryException, IllegalArgumentException {
        loadData();
        super.update(friendship);
        writeToFile(super.getAll());
    }
}
