package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public List<User> getAll() {
        log.info("getAll");
        return null;
    }

    public User get(int id) {
        log.info("get {}", id);
        return null;
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return null;
    }

    public void delete(int id) {
        log.info("delete {}", id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return null;
    }
}