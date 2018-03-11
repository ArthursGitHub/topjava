package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;

@Controller
public class ProfileRestController extends AbstractUserController {

    public User get() {
        return super.get(AuthorizedUser.getId());
    }

    public void delete() {
        super.delete(AuthorizedUser.getId());
    }

    public void update(User user) {
        super.update(user, AuthorizedUser.getId());
    }
}