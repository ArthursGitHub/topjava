package ru.javawebinar.topjava;

import org.springframework.test.context.ActiveProfilesResolver;

import static ru.javawebinar.topjava.Profiles.*;

//http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver
public class ActiveDbProfileResolver implements ActiveProfilesResolver {

  @Override
  public String[] resolve(Class<?> aClass) {
    String repoType = "";
    switch (aClass.getName()) {
      case "ru.javawebinar.topjava.service.jdbc.JdbcUserServiceTest" :
      case "ru.javawebinar.topjava.service.jdbc.JdbcMealServiceTest": {
        repoType = JDBC;
        break;
      }
      case "ru.javawebinar.topjava.service.jpa.JpaUserServiceTest" :
      case "ru.javawebinar.topjava.service.jpa.JpaMealServiceTest": {
        repoType = JPA;
        break;
      }
      case "ru.javawebinar.topjava.service.datajpa.DataJpaUserServiceTest" :
      case "ru.javawebinar.topjava.service.datajpa.DataJpaMealServiceTest": {
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
