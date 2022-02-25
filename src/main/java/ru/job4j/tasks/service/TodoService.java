package ru.job4j.tasks.service;

import org.hibernate.exception.ConstraintViolationException;
import ru.job4j.tasks.models.Task;
import ru.job4j.tasks.models.User;
import ru.job4j.tasks.repository.Storage;

import java.util.List;
import java.util.Optional;

public class TodoService {

    private TodoService() { }

    public static TodoService getInstance() {
        return Inner.INST;
    }

    private static class Inner {
        private static final TodoService INST = new TodoService();
    }

    public void changeStatus(String id, boolean status) {
        Storage storage = Storage.instOf();
        storage.replace(id, status);
    }

    public void createTask(Task task) {
        Storage storage = Storage.instOf();
        storage.add(task);
    }

    public List<Task> showAll() {
        List<Task> rsl;
        Storage storage = Storage.instOf();
        rsl = storage.findUndone();
        return rsl;
    }

    public List<Task> showDone() {
        List<Task> rsl;
        Storage storage = Storage.instOf();
        rsl = storage.findDone();
        return rsl;
    }

    public void addUser(User user) {
        Storage storage = Storage.instOf();
        try {
            storage.addUser(user);
        } catch (ConstraintViolationException cve) {
            throw cve;
        }
    }

    public List<User> getUser(User user) {
        Storage storage = Storage.instOf();
        return storage.getUser(user);
    }
}
