package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

/**
 * Created by arthur on 3/28/18.
 */
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
public class DataJpaMealServiceTest extends MealServiceTest {

  @Test
  public void testGetWithUser() throws Exception {
    Meal adminMeal = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
    assertMatch(adminMeal, ADMIN_MEAL1);
    UserTestData.assertMatch(adminMeal.getUser(), UserTestData.ADMIN);
  }

  @Test(expected = NotFoundException.class)
  public void testGetWithUserNotFound() throws Exception {
    service.getWithUser(MEAL1_ID, ADMIN_ID);
  }
}
