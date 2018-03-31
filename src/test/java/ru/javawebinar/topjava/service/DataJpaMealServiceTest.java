package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

/**
 * Created by arthur on 3/28/18.
 */

@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
public class DataJpaMealServiceTest extends MealServiceTest {

}
