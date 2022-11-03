package repository.FileRepository;

import domain.User;
import exceptions.RepositoryException;
import repository.InMemoryRepository.UserInMemoryRepository;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class UserFileRepository extends UserInMemoryRepository{
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
                User user = new User(Long.parseLong(attributes.get(0)),attributes.get(1),attributes.get(2));
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
    protected void writeToFile(Iterable<User> entities) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, false))){
            for(User e : entities) {
                String line = e.getId() + ";" + e.getFirstName()+";" + e.getLastName()+";";
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected void appendToFile(User entity) {
        String line = entity.getId() + ";" + entity.getFirstName() + ";" + entity.getLastName() + ";";

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
    public int size() {
        return super.size();
    }
}
