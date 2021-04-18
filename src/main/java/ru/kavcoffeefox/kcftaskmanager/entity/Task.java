package ru.kavcoffeefox.kcftaskmanager.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity @Table(name = "task")
@Getter @Setter @NoArgsConstructor
@ToString
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Exclude private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "period")
    private int period;
    @Column(name = "deadline")
    private LocalDate deadline;
    @Column(name = "description")
    private String description;
    @ToString.Exclude private List<People> executor;
    @Column(name = "type")
    private String type;
    @Column(name = "complete")
    private boolean complete;

}
