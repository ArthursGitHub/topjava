package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UserUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserUtils.USERS.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        User remove = repository.remove(id);
        return (remove != null);
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);

        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }

        User computedUser = repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
        return computedUser;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return new ArrayList<User>(repository.values());    // TODO: user sorting
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);

        List<User> allUsers = getAll();
        for (User user : allUsers) {
            String useremail = user.getEmail();
            if (email.equals(useremail)) {
                return user;
            }
        }
        return null;
    }
}
