package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;


/**
 * Created by arthur on 3/16/18.
 */
public class MealTestData {
  public static final int MEAL_ID = ADMIN_ID + 1;
  
  public static final LocalDateTime dt00 = LocalDateTime.of(2015, 5, 30, 10, 0);
  public static final LocalDateTime dt01 = LocalDateTime.of(2015, 5, 30, 13, 0);
  public static final LocalDateTime dt02 = LocalDateTime.of(2015, 5, 30, 20, 0);

  public static final LocalDateTime dt10 = LocalDateTime.of(2015, 5, 31, 10, 0);
  public static final LocalDateTime dt11 = LocalDateTime.of(2015, 5, 31, 13, 0);
  public static final LocalDateTime dt12 = LocalDateTime.of(2015, 5, 31, 20, 0);

  public static final LocalDateTime dt20 = LocalDateTime.of(2015, 5, 27, 10, 0);
  public static final LocalDateTime dt21 = LocalDateTime.of(2015, 5, 27, 13, 0);
  public static final LocalDateTime dt22 = LocalDateTime.of(2015, 5, 27, 20, 0);

  public static final LocalDateTime dt30 = LocalDateTime.of(2015, 5, 28, 10, 0);
  public static final LocalDateTime dt31 = LocalDateTime.of(2015, 5, 28, 13, 0);
  public static final LocalDateTime dt32 = LocalDateTime.of(2015, 5, 28, 20, 0);

  public static final String BREAKFAST = "Завтрак";
  public static final String LUNCH = "Обед";
  public static final String SUPPER = "Ужин";
  public static final int BREAKFAST_COLORIES1 = 500;
  public static final int LUNCH_COLORIES1 = 1000;
  public static final int SUPPER_COLORIES1 = 500;
  public static final int BREAKFAST_COLORIES2 = 1000;
  public static final int LUNCH_COLORIES2 = 500;
  public static final int SUPPER_COLORIES2 = 510;

  public static final Meal MEAL00 = new Meal(MEAL_ID + 0, dt00, BREAKFAST, BREAKFAST_COLORIES1);
  public static final Meal MEAL01 = new Meal(MEAL_ID + 1, dt01, LUNCH, LUNCH_COLORIES1);
  public static final Meal MEAL02 = new Meal(MEAL_ID + 2, dt02, SUPPER, SUPPER_COLORIES1);

  public static final Meal MEAL10 = new Meal(MEAL_ID + 3, dt10, BREAKFAST, BREAKFAST_COLORIES2);
  public static final Meal MEAL11 = new Meal(MEAL_ID + 4, dt11, LUNCH, LUNCH_COLORIES2);
  public static final Meal MEAL12 = new Meal(MEAL_ID + 5, dt12, SUPPER, SUPPER_COLORIES2);

  public static final Meal MEAL20 = new Meal(MEAL_ID + 6, dt20, BREAKFAST, BREAKFAST_COLORIES1);
  public static final Meal MEAL21 = new Meal(MEAL_ID + 7, dt21, LUNCH, LUNCH_COLORIES1);
  public static final Meal MEAL22 = new Meal(MEAL_ID + 8, dt22, SUPPER, SUPPER_COLORIES1);

  public static final Meal MEAL30 = new Meal(MEAL_ID + 9, dt30, BREAKFAST, BREAKFAST_COLORIES2);
  public static final Meal MEAL31 = new Meal(MEAL_ID + 10, dt31, LUNCH, LUNCH_COLORIES2);
  public static final Meal MEAL32 = new Meal(MEAL_ID + 11, dt32, SUPPER, SUPPER_COLORIES2);

  public static final LocalDateTime dtTest0 = LocalDateTime.of(2010, 01, 04, 12, 0);
  public static final String DESCRIPTION0 = "пица";
  public static final int COLORIES0 = 1580;
  public static final Meal MEAL_TEST0 = new Meal(dtTest0, DESCRIPTION0, COLORIES0);


  public static void assertMatch(Meal actual, Meal expected) {
    assertThat(actual).isEqualToComparingFieldByField(expected);
  }

  public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
    assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
  }

  public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
    List<Meal> meals = Arrays.asList(expected);
    Collections.reverse(meals);
    assertMatch(actual, meals);
  }

  public static void assertMatchIgnoreId(Meal actual, Meal expected) {
    assertThat(actual).isEqualToIgnoringGivenFields(expected, "id");
  }
}
