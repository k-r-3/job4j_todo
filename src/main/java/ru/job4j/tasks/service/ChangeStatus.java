package ru.job4j.tasks.service;

import ru.job4j.tasks.repository.Storage;

public class ChangeStatus {

    public static void changeStatus(String id, boolean status) {
        Storage storage = Storage.instOf();
        storage.replace(id, status);
    }
}
