package ru.kavcoffeefox.kcftaskmanager.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity @Table(name = "people")
@Getter @Setter
@NoArgsConstructor
@ToString
public class People {
    @ToString.Exclude private int id;
    private  String firstName;
    private  String lastName;
    private  String patronymic;
    private LocalDate birthDay;
    private  String department;
    private  String position;
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
