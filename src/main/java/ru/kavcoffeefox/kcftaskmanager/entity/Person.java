package ru.kavcoffeefox.kcftaskmanager.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Exclude
    private int id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "birthDay")
    private LocalDate birthDay;
    @Column(name = "department")
    private String department;
    @Column(name = "position")
    private String position;
    @Column(name = "rank")
    private String rank;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "task_executor",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    @ToString.Exclude
    private List<Task> tasks;

    public void addTask(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }

    public Person(String firstName, String lastName, String patronymic, LocalDate birthDay, String department, String position, String rank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDay = birthDay;
        this.department = department;
        this.position = position;
        this.rank = rank;
    }

}
