package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/ajax/admin/meals")
public class MealAjaxController extends AbstractMealController {

  @Override
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<MealWithExceed> getAll() {
    return super.getAll();
  }

  @Override
  @DeleteMapping("/{id}")
  public void delete(@PathVariable("id") int id) {
    super.delete(id);
  }

  @PostMapping
  public void createOrUpdate(@RequestParam("id") Integer id,
                             @RequestParam("localDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
                             @RequestParam("description") String description,
                             @RequestParam("calories") int calories) {

    Meal meal = new Meal(id, localDateTime, description, calories);
    if (meal.isNew()) {
      super.create(meal);
    }
  }

  @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<MealWithExceed> getBetween(
          @RequestParam(value = "startDate", required = false) LocalDate startDate,
          @RequestParam(value = "startTime", required = false) LocalTime startTime,
          @RequestParam(value = "endDate", required = false) LocalDate endDate,
          @RequestParam(value = "endTime", required = false) LocalTime endTime) {
    return super.getBetween(startDate, startTime, endDate, endTime);
  }
}
