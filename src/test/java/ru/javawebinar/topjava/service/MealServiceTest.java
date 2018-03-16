package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatch;

/**
 * Created by arthur on 3/16/18.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
  @Autowired
  private MealService service;

  @Test
  public void get() throws Exception {
    Meal meal;
// ------------   User meal -----------
    meal = service.get(MEAL_ID + 0, USER_ID);
    assertMatch(meal, MEAL00);

    meal = service.get(MEAL_ID + 1, USER_ID);
    assertMatch(meal, MEAL01);

    meal = service.get(MEAL_ID + 2, USER_ID);
    assertMatch(meal, MEAL02);

    meal = service.get(MEAL_ID + 3, USER_ID);
    assertMatch(meal, MEAL10);

    meal = service.get(MEAL_ID + 4, USER_ID);
    assertMatch(meal, MEAL11);

    meal = service.get(MEAL_ID + 5, USER_ID);
    assertMatch(meal, MEAL12);

// ------------   Admin meal -----------
    meal = service.get(MEAL_ID + 6, ADMIN_ID);
    assertMatch(meal, MEAL20);

    meal = service.get(MEAL_ID + 7, ADMIN_ID);
    assertMatch(meal, MEAL21);

    meal = service.get(MEAL_ID + 8, ADMIN_ID);
    assertMatch(meal, MEAL22);

    meal = service.get(MEAL_ID + 9, ADMIN_ID);
    assertMatch(meal, MEAL30);

    meal = service.get(MEAL_ID + 10, ADMIN_ID);
    assertMatch(meal, MEAL31);

    meal = service.get(MEAL_ID + 11, ADMIN_ID);
    assertMatch(meal, MEAL32);
  }


  @Test
  public void getBetweenDates() throws Exception {
    LocalDate localDate = dt00.toLocalDate();
    List<Meal> mealsBetweenDates = service.getBetweenDates(localDate, localDate, USER_ID);
    assertMatch(mealsBetweenDates, MEAL00, MEAL01, MEAL02);
  }

  @Test
  public void getBetweenDateTimes() throws Exception {
    List<Meal> mealsBetweenDateTimes = service.getBetweenDateTimes(dt01, dt11, USER_ID);
    assertMatch(mealsBetweenDateTimes, MEAL01, MEAL02, MEAL10, MEAL11);
  }

  @Test
  public void getAll() throws Exception {
    List<Meal> meals;

    meals = service.getAll(USER_ID);
    assertMatch(meals, MEAL00, MEAL01, MEAL02, MEAL10, MEAL11, MEAL12);  // get all user meat and check it

    meals = service.getAll(ADMIN_ID);
    assertMatch(meals, MEAL20, MEAL21, MEAL22, MEAL30, MEAL31, MEAL32);  // get all admin meat and check it
  }

  @Test
  public void delete() throws Exception {
    List<Meal> meals;

    meals = service.getAll(USER_ID);
    assertMatch(meals, MEAL00, MEAL01, MEAL02, MEAL10, MEAL11, MEAL12);  // check that all meals are present

    service.delete(MEAL_ID + 2, USER_ID); // remove meal with id=MEAL_ID+2

    meals = service.getAll(USER_ID);
    assertMatch(meals, MEAL00, MEAL01, /*MEAL02,*/ MEAL10, MEAL11, MEAL12);  // check that this meal is absent in list
  }

  @Test
  public void update() throws Exception {
    Meal meal, updatedMeal;

    meal = service.get(MEAL_ID + 3, USER_ID);
    assertMatch(meal, MEAL10);

    meal.setDateTime(MEAL_TEST0.getDateTime());
    meal.setCalories(MEAL_TEST0.getCalories());
    meal.setDescription(MEAL_TEST0.getDescription());

    updatedMeal = service.update(meal, USER_ID);   // meal with id=MEAL_ID+3 is updated. Parameters of meal are taken from MEAL_TEST0
    assertMatchIgnoreId(updatedMeal, MEAL_TEST0);  // check that meal is updated
  }

  @Test
  public void create() throws Exception {
    List<Meal> meals;

    meals = service.getAll(USER_ID);
    assertMatch(meals, MEAL00, MEAL01, MEAL02, MEAL10, MEAL11, MEAL12);  // MEAL00 is created
    service.create(MEAL_TEST0, USER_ID);

    meals = service.getAll(USER_ID);
    assertMatch(meals, MEAL_TEST0, MEAL00, MEAL01, MEAL02, MEAL10, MEAL11, MEAL12);  // MEAL00 is present in meal list
  }

  @Test(expected = NotFoundException.class)
  public void getNotFoundMealId() throws Exception {
//    Incorrect mealId
    service.get(MEAL_ID + 100, ADMIN_ID);
  }

  @Test(expected = NotFoundException.class)
  public void getNotFoundUserId() throws Exception {
//    Incorrect userId
    service.get(MEAL_ID + 1, ADMIN_ID + 10);
  }

  @Test(expected = NotFoundException.class)
  public void incorrectCallGet() throws Exception {
//    Getting meal with correct parameters and incorrect
    service.get(MEAL_ID + 3, USER_ID);   // it's correct call
    service.get(MEAL_ID + 3, ADMIN_ID);  // it's incorrect call
  }

  @Test(expected = NotFoundException.class)
  public void incorrectCallDelete() throws Exception {
//    Getting meal with correct parameters and incorrect
    service.delete(MEAL_ID + 3, USER_ID);   // it's correct call
    service.delete(MEAL_ID + 3, ADMIN_ID);  // it's incorrect call
  }

  @Test(expected = NotFoundException.class)
  public void incorrectCallUpdate() throws Exception {
    Meal meal;

    meal = service.get(MEAL_ID + 3, USER_ID);
    assertMatch(meal, MEAL10);

    meal.setDateTime(MEAL_TEST0.getDateTime());
    meal.setCalories(MEAL_TEST0.getCalories());
    meal.setDescription(MEAL_TEST0.getDescription());

    service.update(meal, ADMIN_ID);   // it's incorrect call
  }
}
