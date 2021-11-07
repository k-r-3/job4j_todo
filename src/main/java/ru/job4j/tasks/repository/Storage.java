package ru.job4j.tasks.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.tasks.models.Task;

import java.io.Closeable;
import java.util.List;

public class Storage implements Closeable, Store {
    private final static Logger LOG = LoggerFactory.getLogger(Storage.class);
    private final StandardServiceRegistry reg = new StandardServiceRegistryBuilder()
            .configure().build();
    private SessionFactory sc;

    private Storage() {
        init();
    }

    private static class Lazy {
        private static final Storage STORAGE = new Storage();
    }

    public static Storage instOf() {
        return Lazy.STORAGE;
    }

    @Override
    public void close() {
        if (!sc.isClosed()) {
            sc.close();
            StandardServiceRegistryBuilder.destroy(reg);
        }
    }

    @Override
    public void init() {
        this.sc = new MetadataSources(reg).buildMetadata().buildSessionFactory();
    }

    @Override
    public void add(Task task) {
        Session session = sc.openSession();
        session.beginTransaction();
        int id = (int) session.save(task);
        session.createQuery("insert into Task (descr) select :param from Task")
        .setParameter("param", task.getDescr());
        session.getTransaction().commit();
    }

    @Override
    public Task find(String id) {
        Session session = sc.openSession();
        session.beginTransaction();
        Task rsl = session.find(Task.class, id);
        session.getTransaction().commit();
        return rsl;
    }

    @Override
    public List<Task> findUndone() {
        Session session = sc.openSession();
        session.beginTransaction();
        List rsl = session.createQuery("from ru.job4j.tasks.models.Task where done = :paramDone")
                .setParameter("paramDone", false)
                .list();
        session.getTransaction().commit();
        return rsl;
    }

    @Override
    public List<Task> findDone() {
        Session session = sc.openSession();
        session.beginTransaction();
        List<Task> rsl = session.createQuery("from ru.job4j.tasks.models.Task "
                + "where done = :paramDone")
                .setParameter("paramDone", true)
                .list();
        session.getTransaction().commit();
        return rsl;
    }

    @Override
    public boolean replace(String id, boolean status) {
        boolean rsl = false;
        Session session = sc.openSession();
        session.beginTransaction();
        rsl = session.createQuery("update ru.job4j.tasks.models.Task set done = :paramDone "
                + "where id = :paramId")
                .setParameter("paramId", Integer.parseInt(id))
                .setParameter("paramDone", status)
                .executeUpdate() != 0;
        session.getTransaction().commit();
        return rsl;
    }
}
