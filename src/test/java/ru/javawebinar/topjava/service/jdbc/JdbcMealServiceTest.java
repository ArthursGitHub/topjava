package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.service.MealServiceTest;

/**
 * Created by arthur on 3/28/18.
 */

@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
public class JdbcMealServiceTest extends MealServiceTest {

}
