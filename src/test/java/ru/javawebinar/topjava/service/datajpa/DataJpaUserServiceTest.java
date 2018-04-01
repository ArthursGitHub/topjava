package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
public class DataJpaUserServiceTest extends UserServiceTest {

  @Test
  public void testGetWithMeal() throws Exception {
    User user = service.getWithMeals(USER_ID);
    assertMatch(user, USER);
    MealTestData.assertMatch(user.getMeals(), MEALS);
  }
}
