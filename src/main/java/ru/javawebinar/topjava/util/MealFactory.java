package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.service.MealAction;
import ru.javawebinar.topjava.service.impl.MemoryMeal;

/**
 * Created by arthur on 3/3/18.
 */
public class MealFactory {
  private static MealAction meal;

  public static MealAction getMeal(){
    if (meal == null) {
      meal = new MemoryMeal();
    }
    return meal;
  }
}
