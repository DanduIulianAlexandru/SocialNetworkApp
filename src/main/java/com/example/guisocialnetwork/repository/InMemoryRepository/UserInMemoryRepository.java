package com.example.guisocialnetwork.repository.InMemoryRepository;

import com.example.guisocialnetwork.domain.User;
import com.example.guisocialnetwork.exceptions.RepositoryException;
import com.example.guisocialnetwork.repository.Repository;

import java.util.HashMap;
import java.util.Map;

public class UserInMemoryRepository  implements Repository<Long, User> {
    private Map<Long, User> users;

    public UserInMemoryRepository() {
        this.users = new HashMap<Long, User>();
    }

    @Override
    public User findOne(Long id) throws RepositoryException, IllegalArgumentException{
        if(id == null)
            throw new IllegalArgumentException("user.findOne(): Argument invalid!\n");
        else{
            User user = (User) this.users.get(id);
            if(user != null)
                return user;
            else
                throw new RepositoryException("user.findOne(): Id inexistent!\n");
        }
    }
    @Override
    public Iterable<User> getAll() {
        return this.users.values();
    }
    @Override
    public void add(User user) throws RepositoryException, IllegalArgumentException{
        if(user == null)
            throw new IllegalArgumentException("user.add(): Argument invalid!\n");
        else if(this.users.get(user.getId()) != null)
            throw new RepositoryException("user.add(): User-ul se afla deja in map!\n");
        else
            this.users.put(user.getId(), user);
    }
    @Override
    public void delete(Long id) throws RepositoryException, IllegalArgumentException {
        if(id == null)
            throw new IllegalArgumentException("user.delete(): Argument invalid!\n");
        else if(this.users.get(id) != null) {
            this.users.remove(id);
        }
        else
            throw new RepositoryException("user.delete(): User-ul nu se afla in map!\n");
    }
    @Override
    public int size() {
        return 0;
    }

    @Override
    public void update(User user) throws RepositoryException, IllegalArgumentException {
        if(user == null)
            throw new IllegalArgumentException("user.update(): Argument invalid!\n");

        if(users.get(user.getId()) != null){
            users.replace(user.getId(), user);
            return;
        }
        throw new RepositoryException("user.update(): Id-ul utilizatorului este inexistent!\n");
    }
}
