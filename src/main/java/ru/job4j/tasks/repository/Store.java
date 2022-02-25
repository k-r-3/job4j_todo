package ru.job4j.tasks.repository;

import ru.job4j.tasks.models.Task;
import ru.job4j.tasks.models.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface Store {

    void init();

    void add(Task task);

    Task find(String id);

    List<Task> findUndone();

    Collection<Task> findDone();

    boolean replace(String id, boolean status);

    void addUser(User user);

    List<User> getUser(User user);
}
