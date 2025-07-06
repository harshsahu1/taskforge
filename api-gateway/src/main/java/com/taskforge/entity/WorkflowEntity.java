package com.taskforge.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "workflows")
@Getter
@Setter
@RequiredArgsConstructor
public class WorkflowEntity {
    @Id
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private WorkflowStatus status;

    private Instant createdAt;
    private Instant updatedAt;

    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StepEntity> steps;
}
