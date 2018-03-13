package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));

            MealRestController mealRestBean = appCtx.getBean(MealRestController.class);
            List<MealWithExceed> mealList = mealRestBean.getAll();
            mealList.forEach(mealWithExceed -> System.out.println(mealWithExceed.toString()));

            System.out.println("---------------------------------------------");
            InMemoryUserRepositoryImpl userRepoBean = appCtx.getBean(InMemoryUserRepositoryImpl.class);
            List<User> users = userRepoBean.getAll();
            for (User user : users) {
                System.out.println(user.toString());
            }
            System.out.println("---------------------------------------------");
        }
    }
}
