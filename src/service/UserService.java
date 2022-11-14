package service;

import domain.Friendship;
import domain.User;
import exceptions.RepositoryException;
import exceptions.ValidationException;
import repository.Repository;
import validation.UserValidation;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final Repository<Long, User> repoUser;
    private final Repository<Long, Friendship> repoFriendship;
    private final UserValidation userValidation;
    private long id = 1;

    /*
    * pentru a mentine corectitudinea id-urilor user-ilor vom folosi functia de generateId in constructor
    * functia calculeaza cel mai mare id din lista si seteaza parametru "id" al clasei pentru un nou user
    * la functiile de adaugare si stergere modificam id-ul pentru a fi up to date cu user-ul pe care il prelucram
    * */
    private void generateId(){
        Iterable<User> users = repoUser.getAll();
        long max = 0;
        for(User user : users)
            if(user.getId() > max)
                max = user.getId();
        this.id = max + 1;
    }

    public UserService(Repository<Long, User> repoUser, Repository<Long, Friendship> repoFriendship, UserValidation userValidation) {
        this.repoUser = repoUser;
        this.repoFriendship = repoFriendship;
        this.userValidation = userValidation;
        generateId();
    }

    public void addUser(String firstName, String lastName, String mail, String password) throws RepositoryException, ValidationException {
        User user = new User(this.id, firstName, lastName, mail, password);
        this.id ++;
        userValidation.validate(user);
        repoUser.add(user);
    }

    public void deleteUser(long id) throws RepositoryException{
        /* daca scoatem un user din lista, trebuie scoase scoase si relatiile sale de prietenie*/
        User user = repoUser.findOne(id);
        List<Friendship> ls = new ArrayList<>();
        for(Friendship fr: repoFriendship.getAll())
            ls.add(fr); //ultima chiteala
        for(Friendship friendship : ls)
            if(friendship.getFirstFriend() == user.getId() || friendship.getSecondFriend() == user.getId())
                repoFriendship.delete(friendship.getId());
        repoUser.delete(id);
    }

    public Iterable<User> getAll(){
        return repoUser.getAll();
    }

    public User findOne(Long id) throws RepositoryException {
        return repoUser.findOne(id);
    }

    public int size() {
        return repoUser.size();
    }

    public void update(Long id, String firstName, String secondName, String mail, String password) throws RepositoryException, ValidationException{
        User user = new User(id, firstName, secondName, mail, password);
        userValidation.validate(user);
        repoUser.update(user);
    }
}
