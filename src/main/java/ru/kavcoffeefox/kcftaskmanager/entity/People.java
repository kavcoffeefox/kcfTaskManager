package ru.kavcoffeefox.kcftaskmanager.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "people")
@Getter @Setter
@NoArgsConstructor
@ToString
public class People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Exclude private int id;
    @Column(name = "firstName")
    private  String firstName;
    @Column(name = "lastName")
    private  String lastName;
    @Column(name = "patronymic")
    private  String patronymic;
    @Column(name = "birthDay")
    private LocalDate birthDay;
    @Column(name = "department")
    private  String department;
    @Column(name = "position")
    private  String position;
    @Column(name = "rank")
    private  String rank;

    public People(String firstName, String lastName, String patronymic, LocalDate birthDay, String department, String position, String rank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDay = birthDay;
        this.department = department;
        this.position = position;
        this.rank = rank;
    }
}
