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
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by arthur on 3/16/18.
 */
@ContextConfiguration({
        "classpath:spring/spring-app-common.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
  @Autowired
  private MealService service;

  @Test
  public void get() throws Exception {
// ------------   User meal -----------
    Meal meal = service.get(MEAL00.getId(), USER_ID);
    assertMatch(meal, MEAL00);
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
    List<Meal> meals = service.getAll(USER_ID);
    assertMatch(meals, MEAL00, MEAL01, MEAL02, MEAL10, MEAL11, MEAL12);  // get all user meat and check it
  }

  @Test
  public void delete() throws Exception {
    service.delete(MEAL02.getId(), USER_ID);

    List<Meal> meals = service.getAll(USER_ID);
    assertMatch(meals, MEAL00, MEAL01, /*MEAL02,*/ MEAL10, MEAL11, MEAL12);  // check that this meal is absent in list
  }

  @Test
  public void update() throws Exception {
    Meal modifiedMeal = service.get(MEAL_ID, USER_ID);

    Meal newMeal = new Meal(MEAL_ID, modifiedMeal.getDateTime(), modifiedMeal.getDescription(), modifiedMeal.getCalories());
    newMeal.setDescription("Новое описание");
    newMeal.setCalories(200);

    Meal updated = service.update(newMeal, USER_ID);// meal with id=MEAL_ID is updated. Parameters of meal are taken from MEAL_TEST0
    assertMatch(updated, newMeal);  // check that meal is updated
  }

  @Test
  public void create() throws Exception {
    Meal meal = new Meal(null, LocalDateTime.of(2015, 5, 30, 7, 0), "вкусняшки", 555);

    service.create(meal, USER_ID);
    List<Meal> meals = service.getAll(USER_ID);
    assertMatch(meals, meal, MEAL00, MEAL01, MEAL02, MEAL10, MEAL11, MEAL12);  // new meal is created
  }

  @Test(expected = NotFoundException.class)
  public void getNotFoundMealId() throws Exception {
//    Incorrect mealId
    service.get(MEAL_ID + 100, ADMIN_ID);
  }

  @Test(expected = NotFoundException.class)
  public void getNotFoundUserId() throws Exception {
//    Incorrect userId
    service.get(MEAL01.getId(), ADMIN_ID);
  }

  @Test(expected = NotFoundException.class)
  public void incorrectCallGet() throws Exception {
//    Getting meal with correct parameters and incorrect
    service.get(MEAL10.getId(), ADMIN_ID);  // it's incorrect call
  }

  @Test(expected = NotFoundException.class)
  public void incorrectCallDelete() throws Exception {
//    Getting meal with correct parameters and incorrect
    service.delete(MEAL10.getId(), ADMIN_ID);  // it's incorrect call
  }

  @Test(expected = NotFoundException.class)
  public void incorrectCallUpdate() throws Exception {
    service.update(MEAL10, ADMIN_ID);   // it's incorrect call
  }
}
