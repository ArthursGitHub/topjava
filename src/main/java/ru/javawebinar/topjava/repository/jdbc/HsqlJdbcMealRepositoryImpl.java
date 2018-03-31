package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by arthur on 3/28/18.
 */

@Repository
@Profile("hsqldb")
public class HsqlJdbcMealRepositoryImpl extends JdbcMealRepositoryImpl {
  public HsqlJdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    super(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
  }

  @Override
  public Meal save(Meal meal, int userId) {
    MapSqlParameterSource map = new MapSqlParameterSource()
            .addValue("id", meal.getId())
            .addValue("description", meal.getDescription())
            .addValue("calories", meal.getCalories())
            .addValue("date_time", Timestamp.valueOf(meal.getDateTime()))
            .addValue("user_id", userId);

    if (meal.isNew()) {
      Number newId = insertMeal.executeAndReturnKey(map);
      meal.setId(newId.intValue());
    } else {
      if (namedParameterJdbcTemplate.update("" +
                      "UPDATE meals " +
                      "   SET description=:description, calories=:calories, date_time=:date_time " +
                      " WHERE id=:id AND user_id=:user_id"
              , map) == 0) {
        return null;
      }
    }
    return meal;
  }

  @Override
  public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
    return jdbcTemplate.query(
            "SELECT * FROM meals WHERE user_id=?  AND date_time BETWEEN  ? AND ? ORDER BY date_time DESC",
            ROW_MAPPER, userId, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
  }
}
