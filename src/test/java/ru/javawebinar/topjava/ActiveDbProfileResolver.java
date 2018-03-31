package ru.javawebinar.topjava;

import org.springframework.test.context.ActiveProfilesResolver;

import static ru.javawebinar.topjava.Profiles.*;

//http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver
public class ActiveDbProfileResolver implements ActiveProfilesResolver {

  @Override
  public String[] resolve(Class<?> aClass) {
    String repoType = DATAJPA;
    switch (aClass.getName()) {
      case "ru.javawebinar.topjava.service.JdbcMealServiceTest": {
        repoType = JDBC;
        break;
      }
      case "ru.javawebinar.topjava.service.JpaMealServiceTest": {
        repoType = JPA;
        break;
      }
      case "ru.javawebinar.topjava.service.DataJpaMealServiceTest": {
        repoType = DATAJPA;
        break;
      }
    }
    String[] resolve = new String[]{
            Profiles.getActiveDbProfile(),
            repoType
    };
    return resolve;
  }
}
