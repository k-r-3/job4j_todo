package ru.job4j.tasks.service;

import ru.job4j.tasks.models.Task;
import ru.job4j.tasks.repository.Storage;

public class Finder {

    public static Task find(String id) {
        Storage storage = Storage.instOf();
        return storage.find(id);
    }
}
