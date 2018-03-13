package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by arthur on 3/9/18.
 */
public class UserUtils {
  public static final List<User> USERS = Arrays.asList(
          new User("Alex", "mail1@mail.ru", "pswd1"),
          new User("Felix",  "mail2@mail.ru", "pswd2"),
          new User("Alex",  "mail3@mail.ru", "pswd3")
  );
}
