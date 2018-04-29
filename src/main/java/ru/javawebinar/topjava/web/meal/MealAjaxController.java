package ru.javawebinar.topjava.web.meal;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;

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
                             @RequestParam("localDateTime") String localDateTime,
                             @RequestParam("description") String description,
                             @RequestParam("calories") String calories) {

    Meal meal = new Meal(id, DateTimeUtil.parseLocalDateTime(localDateTime), description, Integer.valueOf(calories));
    if (meal.isNew()) {
      super.create(meal);
    }
  }
}
