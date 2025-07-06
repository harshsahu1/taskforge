package com.taskforge.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "steps")
@Getter
@Setter
@RequiredArgsConstructor
public class StepEntity {

    @Id
    private UUID id;

    private String name;
    private String service;

    @Enumerated(EnumType.STRING)
    private StepStatus status;

    private int retryCount;
    private int maxRetries;

    @ElementCollection
    private List<String> dependsOn; // Store step UUIDs

    private Instant createdAt;
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id")
    private WorkflowEntity workflow;
}
