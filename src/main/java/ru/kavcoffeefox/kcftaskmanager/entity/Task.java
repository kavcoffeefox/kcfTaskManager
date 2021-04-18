package ru.kavcoffeefox.kcftaskmanager.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Exclude
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "period")
    private int period;
    @Column(name = "deadline")
    private LocalDate deadline;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private String type;
    @Column(name = "complete")
    private boolean complete;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "task_executor",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    @ToString.Exclude
    private List<Person> executors;

    public void addExecutor(Person executor) {
        if (executors == null) {
            executors = new ArrayList<>();
        }
        executors.add(executor);
    }

    public Task(String name, int period, LocalDate deadline, String description, String type, boolean complete) {
        this.name = name;
        this.period = period;
        this.deadline = deadline;
        this.description = description;
        this.type = type;
        this.complete = complete;
    }
}
