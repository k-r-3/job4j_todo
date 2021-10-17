package ru.job4j.tasks.repository;

import ru.job4j.tasks.models.Task;

import java.util.Collection;
import java.util.List;

public interface Store {

    void init();

    void add(Task task);

    Task find(String id);

    List<Task> findUndone();

    Collection<Task> findDone();

    boolean replace(String id, boolean status);
}
