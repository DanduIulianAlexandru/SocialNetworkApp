package com.example.guisocialnetwork.repository.BDRepository;

import com.example.guisocialnetwork.domain.User;
import com.example.guisocialnetwork.exceptions.RepositoryException;
import com.example.guisocialnetwork.repository.Repository;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserRepositoryDB implements Repository<Long, User> {
    private final String url;
    private final String username;
    private final String password;

    /**
     * constructor
     * @param url, url-ul bazei de date, tipul String
     * @param username, usernameul utilizatorului bazei de date, tipul String
     * @param password, parola utilizatorului bazei de date, tipul String
     */
    public UserRepositoryDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * returneaza userul cu id-ul specificat din repository
     * @param id, id-ul userului pe care dorim sa-l cautam, tipul Long
     * @return userul cu id-ul specificat, tipul User
     * @throws RepositoryException "id inexistent!\n", daca nu exista userul cu id-ul dat
     * @throws IllegalArgumentException "argument invalid!\n", daca id-ul este null
     */
    @Override
    public User findOne(Long id) throws RepositoryException {
        if(id == null)
            throw new IllegalArgumentException("argument invalid!\n");
        User user = null;
        String sql = "select * from users where id = cast(? as int)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id.toString());
            String username;
            String firstName;
            String lastName;
            String email;
            String password;
            try (ResultSet resultSet = ps.executeQuery()) {
                if(!resultSet.next())
                    throw new RepositoryException("id inexistent\n");

                username = resultSet.getString("username");
                firstName = resultSet.getString("firstname");
                lastName = resultSet.getString("lastname");
                email = resultSet.getString("email");
                password = resultSet.getString("password");
            }
            user = new User(id, username, firstName, lastName, email, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * returneaza lista de useri
     * @return lista de useri, tipul Iterable
     */
    @Override
    public Iterable<User> getAll() {
        Set<User> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                User user = new User(id, username, firstName, lastName, email, password);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * adauga un user in repository
     * @param entity userul pe care dorim sa-l adaugam, tipul User
     * @throws IllegalArgumentException "argument invalid!\n", daca user este null
     */
    @Override
    public void add(User entity) {
        if(entity == null)
            throw new IllegalArgumentException("argument invalid!\n");
        String sql = "insert into users (username, firstname, lastname, email, password) values (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getFirstName());
            ps.setString(3, entity.getLastName());
            ps.setString(4, entity.getEmail());
            ps.setString(5, entity.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * sterge un utilizator din repository
     * @param id id-ul userului pe care dorim sa-l stergem, tipul Long
     * @throws IllegalArgumentException "argument invalid!\n", daca id-ul este null
     */
    @Override
    public void delete(Long id) {
        if(id == null)
            throw new IllegalArgumentException("argument invalid!\n");
        String sql = "delete from users where id = cast(? as int)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * actualizeaza un user in repository
     * @param entity userul pe care dorim sa-l actualizam, tipul User
     * @throws IllegalArgumentException "argument invalid!\n", daca user este null
     */
    @Override
    public void update(User entity) {
        if(entity == null)
            throw new IllegalArgumentException("argument invalid!\n");
        String sql = "update users set username = ?, firstname = ?, lastname = ?, email = ?, password = ? where id = cast(? as int )";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getFirstName());
            ps.setString(3, entity.getLastName());
            ps.setString(4, entity.getEmail());
            ps.setString(5, entity.getPassword());
            Long id = entity.getId();
            ps.setString(5, id.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * returneaza numarul de useri din repository
     * @return useri de obiecte, tipul Integer
     */
    @Override
    public int size() {
        int size = 0;
        String sql = "select count(*) as size from users";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            size = resultSet.getInt("size");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size;
    }
}
