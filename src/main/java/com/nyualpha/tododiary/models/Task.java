package com.nyualpha.tododiary.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 64, nullable = false)
    private String name;

    @Column(length = 128)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private Integer taskOrder;

    private LocalDate deadline;

    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id", nullable = false)
    private Block block;

    /* If the task belongs to a task then it doesn't belong to a block */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_task_id", nullable = true)
    private Task parentTask;

    /*A task can have from 0 to many subtask */
    @OneToMany(mappedBy = "parentTask" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Task> subtasks = new HashSet<>();


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }
}
