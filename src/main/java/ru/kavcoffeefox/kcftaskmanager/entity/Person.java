package ru.kavcoffeefox.kcftaskmanager.entity;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

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
    @Column(name = "position")
    private String position;
    @Column(name = "rank")
    private String rank;
    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "department")
    @NotFound(action = NotFoundAction.IGNORE)
    @ToString.Exclude
    private Department department;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "task_person",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    @ToString.Exclude
    private Set<Task> tasks = new HashSet<>();
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "document_person",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    @ToString.Exclude
    private Set<Document> documents = new HashSet<>();

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
