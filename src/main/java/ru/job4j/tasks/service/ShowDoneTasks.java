package ru.job4j.tasks.service;

import ru.job4j.tasks.models.Task;
import ru.job4j.tasks.repository.Storage;

import java.util.List;

public class ShowDoneTasks {

    public static List<Task> showDone() {
        List<Task> rsl;
        Storage storage = Storage.instOf();
            rsl = storage.findDone();
        return rsl;
    }
}