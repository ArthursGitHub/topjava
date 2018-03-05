package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.service.MealDao;
import ru.javawebinar.topjava.service.impl.MemoryMealDaoImpl;

/**
 * Created by arthur on 3/3/18.
 */
public class DaoFactory {
  private static MealDao mealDao;

  public static MealDao getDao(){
    if (mealDao == null) {
      mealDao = new MemoryMealDaoImpl();
    }
    return mealDao;
  }
}
