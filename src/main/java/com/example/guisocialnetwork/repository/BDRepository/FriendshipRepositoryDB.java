package com.example.guisocialnetwork.repository.BDRepository;

import com.example.guisocialnetwork.domain.Friendship;
import com.example.guisocialnetwork.exceptions.RepositoryException;
import com.example.guisocialnetwork.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class FriendshipRepositoryDB implements Repository<Long, Friendship> {
    private final String url;
    private final String username;
    private final String password;

    /**
     * constructor
     * @param url, url-ul bazei de date, tipul String
     * @param username, usernameul utilizatorului bazei de date, tipul String
     * @param password, parola utilizatorului bazei de date, tipul String
     */
    public FriendshipRepositoryDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private boolean find(Long id1, Long id2){
        boolean rezult = true;
        String sql = "select * from friendships where firstfriend = cast(? as int) and secondfriend = cast(? as int)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id1.toString());
            ps.setString(2, id2.toString());
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next()) {
                rezult = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rezult;
    }

    /**
     * returneaza prietenia cu id-ul specificat din repository
     * @param id, id-ul prieteniei pe care dorim sa o cautam, tipul Long
     * @return prietenia cu id-ul specificat, tipul Friendship
     * @throws RepositoryException "id inexistent!\n", daca nu exista prietenie cu id-ul dat
     * @throws IllegalArgumentException "argument invalid!\n", daca id-ul este null
     */

    @Override
    public Friendship findOne(Long id) throws RepositoryException {
        if(id == null)
            throw new IllegalArgumentException("argument invalid!\n");
        Friendship friendship = null;
        String sql = "select * from friendships where id = cast(? as int)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id.toString());
            ResultSet resultSet = ps.executeQuery();
            if(!resultSet.next())
                throw new RepositoryException("id inexistent!\n");
            long firstFriend = resultSet.getLong("firstfriend");
            long secondFriend = resultSet.getLong("secondfriend");
            LocalDateTime dateTime = resultSet.getObject("friendsfrom", LocalDateTime.class);
            friendship = new Friendship(id, firstFriend, secondFriend, dateTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(friendship != null)
            return friendship;
        throw new RepositoryException("id inexistent!\n");
    }

    /**
     * returneaza lista de prietenii
     * @return lista de prietenii, tipul Iterable
     */
    @Override
    public Iterable<Friendship> getAll() {
        Set<Friendship> prietenii = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                long firstFriend = resultSet.getLong("firstfriend");
                long secondFriend = resultSet.getLong("secondfriend");
                LocalDateTime dateTime = resultSet.getObject("friendsfrom", LocalDateTime.class);
                int statusInt = resultSet.getInt("status");
                Friendship friendship = new Friendship(id, firstFriend, secondFriend, dateTime);
                friendship.setStatusFromInt(statusInt);
                prietenii.add(friendship);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prietenii;
    }



    /**
     * adauga o prietenie in repository
     * @param entity prietenia pe care dorim sa o adaugam, tipul Friendship
     * @throws IllegalArgumentException "argument invalid!\n", daca friendship este null
     */
    @Override
    public void add(Friendship entity) throws RepositoryException {
        if(entity == null)
            throw new IllegalArgumentException("argument invalid!\n");
        if(find(entity.getFirstFriend(), entity.getSecondFriend()) || find(entity.getSecondFriend(),entity.getFirstFriend()))
            throw new RepositoryException ("prietenie existenta\n");
        String sql = "insert into friendships (firstfriend, secondfriend, friendsfrom, status) values (cast(? as int ), cast(? as int), ?, cast(? as int))";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            Long id1 = entity.getFirstFriend();
            Long id2 = entity.getSecondFriend();

            ps.setString(1, id1.toString());
            ps.setString(2, id2.toString());
            ps.setObject(3, entity.getDateTime());
            ps.setInt(4, entity.statusToInt());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * sterge o prietenie din repository
     * @param id id-ul prieteniei pe care dorim sa o stergem, tipul Long
     * @throws IllegalArgumentException "argument invalid!\n", daca id-ul este null
     */
    @Override
    public void delete(Long id) {
        if(id == null)
            throw new IllegalArgumentException("argument invalid!\n");
        String sql = "delete from friendships where id = cast(? as int)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * actualizeaza o prietenie in repository
     * @param entity prietenia pe care dorim sa o actualizam, tipul Friendship
     * @throws IllegalArgumentException "argument invalid!\n", daca friendship este null
     */
    @Override
    public void update(Friendship entity) throws RepositoryException {
        if(entity == null)
            throw new IllegalArgumentException("argument invalid!\n");
        if(find(entity.getFirstFriend(), entity.getSecondFriend()) || find(entity.getSecondFriend(),entity.getFirstFriend()))
            throw new RepositoryException ("prietenie existenta\n");
        String sql = "update friendships set firstfriend = cast(? as int), secondfriend = cast(? as int), friendsfrom = cast(? as date)  where id = cast(? as int )";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            Long id1 = entity.getFirstFriend();
            Long id2 = entity.getSecondFriend();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            ps.setString(1, id1.toString());
            ps.setString(2, id2.toString());
            ps.setString(3, LocalDateTime.now().format(format));
            Long id = entity.getId();
            ps.setString(4, id.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * returneaza numarul de prietenii din repository
     * @return numarul de obiecte, tipul Integer
     */
    @Override
    public int size() {
        int size = 0;
        String sql = "select count(*) as size from friendships";
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
    public void acceptFriendship(Friendship friendship) throws RepositoryException {
        for(Friendship friendship1 : getAll())
            if(friendship1.equals(friendship)) {
                friendship1.acceptFriendship();
                return;
            }
        throw new RepositoryException("The Friendship doesn t exist!");
    }
}
