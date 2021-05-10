package ru.kavcoffeefox.kcftaskmanager.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@ToString
@Table(name = "department")
@Getter @Setter
public class Department implements SimpleItem{
    @ToString.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "fullname")
    private String fullname;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
    mappedBy = "department")
    @ToString.Exclude
    Set<Person> persons = new HashSet<>();

    public void addPerson(Person person){
        if (person != null){
            persons.add(person);
            person.setDepartment(this);
        }
    }

}
