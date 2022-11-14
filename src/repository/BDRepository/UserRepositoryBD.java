package repository.BDRepository;

import domain.User;
import exceptions.RepositoryException;
import repository.Repository;

public class UserRepositoryBD implements Repository<Long, User> {
    private final String url;
    private final String password;
    private final String username;


    public UserRepositoryBD(String url, String password, String username) {
        this.url = url;
        this.password = password;
        this.username = username;
    }


    @Override
    public User findOne(Long id) throws RepositoryException, IllegalArgumentException {
        if(id == null)
            throw new IllegalArgumentException("user.findOne(): Argument invalid!\n");
        User user = null;
        String sql = "select * from ";
        return user;
    }

    @Override
    public Iterable<User> getAll() {
        return null;
    }

    @Override
    public void add(User entity) throws RepositoryException, IllegalArgumentException {

    }

    @Override
    public void delete(Long aLong) throws RepositoryException, IllegalArgumentException {

    }

    @Override
    public void update(User entity) throws RepositoryException, IllegalArgumentException {

    }

    @Override
    public int size() {
        return 0;
    }
}
