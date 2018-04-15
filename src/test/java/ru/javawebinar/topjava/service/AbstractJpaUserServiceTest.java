package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.javawebinar.topjava.repository.JpaUtil;

public abstract class  AbstractJpaUserServiceTest extends AbstractUserServiceTest {
  @Autowired
  private JpaUtil jpaUtil;

  @Before
  public void setUp() throws Exception {
    cacheManager.getCache("users").clear();
    jpaUtil.clear2ndLevelHibernateCache();
  }
}
