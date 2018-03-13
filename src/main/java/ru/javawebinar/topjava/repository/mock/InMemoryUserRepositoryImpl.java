package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UserUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Comparator.comparing;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private static final Comparator<User> USER_COMPARATOR_BY_NAME = comparing(User::getName);
    private static final Comparator<User> USER_COMPARATOR_BY_ID = comparing(User::getId);
    private static final Comparator<User> USER_COMPARATOR = (user1, user2) -> {
        String userName1 = user1.getName();
        String userName2 = user2.getName();
        return !userName1.equals(userName2) ? userName1.compareTo(userName2) : user1.getId().compareTo(user2.getId());
    };
    private static final Comparator<User> USER_COMPARATOR_BY_NAME_BY_ID = Comparator
            .comparing(User::getName)
            .thenComparing(User::getId);

    {
        UserUtils.USERS.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return (repository.remove(id) != null);
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);

        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        ArrayList<User> users = new ArrayList<>(repository.values());
        users.sort(USER_COMPARATOR_BY_NAME_BY_ID);
        return users;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);

        return getAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
