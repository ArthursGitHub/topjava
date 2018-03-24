package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id and m.user.id=:user_id"),
        @NamedQuery(name = Meal.GET, query = "SELECT m FROM Meal m WHERE m.id=:id and m.user.id=:user_id"),
        @NamedQuery(name = Meal.GET_ALL, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user where m.user.id=:user_id"),
        @NamedQuery(name = Meal.GET_FILTERED_BY_DATE, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user where m.user.id=:user_id and m.dateTime>=:start_date and m.dateTime<=:end_date")
})

@Entity
@Table(name = "meals")
public class Meal extends AbstractBaseEntity {
    public static final String GET = "Meal.get";
    public static final String GET_ALL = "Meal.getAll";
    public static final String DELETE = "Meal.delete";
    public static final String GET_FILTERED_BY_DATE = "Meal.getFilteredByDate";

    @Column(name = "date_time")
    @NotNull
    private LocalDateTime dateTime;

    @NotBlank
    @Size(max = 100)
    private String description;

    @NotNull
    private int calories;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories, User user) {
        this(id, dateTime, description, calories);
        setUser(user);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", User:" + (user == null ? "null user" : user.getId()) +
                '}';
    }
}
