package ru.job4j.tasks.models;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "item")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descr;
    private Timestamp created;
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Task of(Task newTask, User user) {
        Task task = new Task();
        task.descr = newTask.getDescr();
        task.created = newTask.getCreated();
        task.user = user;
        return task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Task{"
                + "id=" + id
                + ", descr='" + descr
                + '\'' + ", created="
                + created + ", done=" + done
                + '}';
    }
}
