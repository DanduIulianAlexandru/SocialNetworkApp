package service;

import domain.Friendship;
import domain.Graph;
import domain.User;
import exceptions.RepositoryException;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class FriendshipService {
    private final Repository<Long, User> repoUser;
    private final Repository<Long, Friendship> repoFriendship;
    private final Graph graph;
    private long id = 1;

    // same thing ca si la user
    private void generateId(){
        Iterable<Friendship> friendships = repoFriendship.getAll();
        long max = 0;
        for (Friendship friendship : friendships)
            if(friendship.getId() > max)
                max = friendship.getId();
        id = max + 1;
    }

    public FriendshipService(Repository<Long, User> repoUser, Repository<Long, Friendship> repoFriendship) {
        this.repoUser = repoUser;
        this.repoFriendship = repoFriendship;
        graph = new Graph(repoFriendship.getAll());
        generateId();
    }

    /**
     * adauga o prietenie in repository
     * @param id1 id-ul primului utilizator, tipul Long
     * @param id2 id-ul celui de-al doilea utilizator, tipul Long
     * @throws RepositoryException daca prietenia a fost deja adaugata sau daca nu exista utilizatori cu id-urile id1 sau id2
     *@throws IllegalArgumentException daca id1 si/sau id2 este null
     */
    public void addFriendship(long id1, long id2) throws RepositoryException{
        repoUser.findOne(id1);
        repoUser.findOne(id2);
        Friendship friendship = new Friendship(id, id1, id2);
        this.id ++;
        repoFriendship.add(friendship);
        graph.addEdge(id1, id2);
    }
    /**
     * sterge o prietenie repository
     * @param id1 id-ul primului utilizator tipul Long
     * @param id2 id-ul celui de-al doilea utilizator, tipul Long
     * @throws RepositoryException daca nu exista prietenie intre userii cu id-urile id1, id2 sau daca nu exista userii cu id-urile id1 si id2
     * @throws IllegalArgumentException daca id1 si/sau id2 este null
     * */
    public void deleteFriendship(long id1, long id2) throws RepositoryException{
        User user1 = repoUser.findOne(id1);
        User user2 = repoUser.findOne(id2);
        this.id = 0;
        List<Friendship> ls = new ArrayList<>();
        for(Friendship fr: repoFriendship.getAll())
            ls.add(fr); //ultima chiteala
        for(Friendship friendship : ls)
            if((friendship.getFirstFriend() == user1.getId() && friendship.getSecondFriend() == user2.getId())
             || (friendship.getFirstFriend() == user2.getId() && friendship.getSecondFriend() == user1.getId()))
                this.id = friendship.getId();
        repoFriendship.delete(id);
        graph.deleteEdge(id1, id2);
    }

    public Iterable<Friendship> getAll(){
        return repoFriendship.getAll();
    }

    public Friendship findOne(long id) throws RepositoryException {
        return repoFriendship.findOne(id);
    }

    public int size(){
        return repoFriendship.size();
    }

    public void update(Long id, Long firstFriend, Long secondFriend) throws RepositoryException{
        Friendship friendship = new Friendship(id, firstFriend, secondFriend);
        Friendship friendship1 = repoFriendship.findOne(id);
        repoUser.findOne(firstFriend);
        repoUser.findOne(secondFriend);
        repoFriendship.update(friendship);
        graph.deleteEdge(friendship1.getFirstFriend(), friendship1.getSecondFriend());
        graph.addEdge(firstFriend, secondFriend);
    }

    public int theNumberOfComunities(){
        return graph.theNumberOfComunities(repoUser.getAll());
    }

    public List<Long> theMostSociableComunity(){
        return graph.theMostSociableComunity(repoUser.getAll());
    }
}
