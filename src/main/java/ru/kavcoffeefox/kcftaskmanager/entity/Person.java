package ru.kavcoffeefox.kcftaskmanager.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Person implements SimpleItem{
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
    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "task_executor",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    @ToString.Exclude
    private List<Task> tasks = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "document_person",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    @ToString.Exclude
    private List<Document> documents = new ArrayList<>();


    public Person(String firstName, String lastName, String patronymic, LocalDate birthDay, String department, String position, String rank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDay = birthDay;
        this.department = department;
        this.position = position;
        this.rank = rank;
    }

    public static String getFIO(Person person){
        StringBuilder fio = new StringBuilder();
        fio.append(person.getLastName()).append(" ");
        if (person.getFirstName() != null && person.getFirstName().length() >= 1){
            fio.append(person.getFirstName().toUpperCase(Locale.ROOT).charAt(0)).append(".");
        }
        if (person.getPatronymic() != null && person.getPatronymic().length() >= 1){
            fio.append(person.getPatronymic().toUpperCase(Locale.ROOT).charAt(0)).append(".");
        }
        return fio.toString();
    }

}
