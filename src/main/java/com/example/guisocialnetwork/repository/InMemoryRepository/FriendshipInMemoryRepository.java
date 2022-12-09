package com.example.guisocialnetwork.repository.InMemoryRepository;

import com.example.guisocialnetwork.domain.Friendship;
import com.example.guisocialnetwork.exceptions.RepositoryException;
import com.example.guisocialnetwork.repository.Repository;

import java.util.HashMap;
import java.util.Map;

public class FriendshipInMemoryRepository implements Repository<Long, Friendship> {
    private Map<Long, Friendship> friendships;
    public FriendshipInMemoryRepository() {
        this.friendships = new HashMap<Long, Friendship>();
    }

    @Override
    public Friendship findOne(Long id) throws RepositoryException, IllegalArgumentException {
        if(id == null)
            throw new IllegalArgumentException("friendship.findOne(): Argument invalid!\n");
        else {
            Friendship friendship = (Friendship) friendships.get(id);
            if (friendship != null)
                return friendship;
            else
                throw new RepositoryException("friendship.findOne(): Id inexistent!\n");
        }
    }
    @Override
    public Iterable<Friendship> getAll() {
        return this.friendships.values();
    }
    @Override
    public void add(Friendship friendship) throws RepositoryException, IllegalArgumentException {
        if(friendship == null)
            throw new IllegalArgumentException("friendship.add(): Argument invalid!\n");
        else if(this.friendships.get(friendship.getId()) != null)
            throw new RepositoryException("friendship.add(): Friendship-ul se afla deja in map!\n");
        else
            this.friendships.put(friendship.getId(), friendship);
    }
    @Override
    public void delete(Long id) throws RepositoryException, IllegalArgumentException {
        if(id == null)
            throw  new IllegalArgumentException("argument invalid!\n");
        if(friendships.get(id) != null) {
            friendships.remove(id);
            return;
        }
        throw new RepositoryException("id inexistent!\n");

    }
    @Override
    public int size() {
        return this.friendships.size();
    }

    @Override
    public void update(Friendship friendship) throws RepositoryException, IllegalArgumentException {
        if(friendship == null)
            throw new IllegalArgumentException("friendship.update(): Argument invalid!\n");
        for(Friendship friendship1 : friendships.values()){
            if(friendship.equals(friendship1))
                throw new RepositoryException("firendship.update(): Prietenie existenta\n");
            if(friendship.getId() == friendship1.getId()){
                friendships.replace(friendship.getId(), friendship);
                return;
            }
        }
        throw new RepositoryException("friendsship.update(): Id-ul prieteniei este inexistent\n");
    }
}
