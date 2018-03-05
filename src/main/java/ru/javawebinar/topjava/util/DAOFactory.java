package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.service.MealDAO;
import ru.javawebinar.topjava.service.impl.MemoryMealDAOImpl;

/**
 * Created by arthur on 3/3/18.
 */
public class DAOFactory {
  private static MealDAO meal;

  public static MealDAO getDAO(){
    if (meal == null) {
      meal = new MemoryMealDAOImpl();
    }
    return meal;
  }
}
