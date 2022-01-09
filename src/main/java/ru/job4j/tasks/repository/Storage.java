package ru.job4j.tasks.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.tasks.models.Task;

import java.io.Closeable;
import java.util.List;
import java.util.function.Function;

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

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sc.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void add(Task task) {
        this.tx(
                session -> {
                    session.save(task);
                    return session.createQuery("insert into Task (descr) select :param from Task")
                            .setParameter("param", task.getDescr());
                }
        );
    }

    @Override
    public Task find(String id) {
        return this.tx(
                session -> session.find(Task.class, id)
        );
    }

    @Override
    public List<Task> findUndone() {
        return this.tx(
                session -> session.createQuery("from ru.job4j.tasks.models.Task "
                        + "where done = :paramDone")
                .setParameter("paramDone", false)
                .list()
        );
    }

    @Override
    public List<Task> findDone() {
        return this.tx(
                session -> session.createQuery("from ru.job4j.tasks.models.Task "
                + "where done = :paramDone")
                .setParameter("paramDone", true)
                .list()
        );
    }

    @Override
    public boolean replace(String id, boolean status) {
        return this.tx(
                session -> session.createQuery("update ru.job4j.tasks.models.Task"
                        + " set done = :paramDone where id = :paramId")
                .setParameter("paramId", Integer.parseInt(id))
                .setParameter("paramDone", status)
                .executeUpdate() != 0
        );
    }
}
