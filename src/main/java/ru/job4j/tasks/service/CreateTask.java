package ru.job4j.tasks.service;

import ru.job4j.tasks.models.Task;
import ru.job4j.tasks.repository.Storage;

public class CreateTask {

    public static void createTask(Task task) {
        Storage storage = Storage.instOf();
            storage.add(task);
    }
}
