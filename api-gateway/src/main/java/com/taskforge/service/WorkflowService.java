package com.taskforge.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskforge.entity.StepEntity;
import com.taskforge.entity.StepStatus;
import com.taskforge.entity.WorkflowEntity;
import com.taskforge.entity.WorkflowStatus;
import com.taskforge.repository.WorkflowRepository;

import jakarta.persistence.EntityNotFoundException;
import workflow.WorkflowOuterClass.Workflow;

@Service
public class WorkflowService {

    @Autowired
    private WorkflowRepository workflowRepo;

    public void saveWorkflow(Workflow proto) {
        WorkflowEntity entity = new WorkflowEntity();
        entity.setId(UUID.fromString(proto.getId()));
        entity.setName(proto.getName());
        entity.setStatus(WorkflowStatus.PENDING);
        entity.setCreatedAt(Instant.now());
        entity.setUpdatedAt(Instant.now());

        List<StepEntity> stepEntities = proto.getStepsList().stream().map(step -> {
            StepEntity s = new StepEntity();
            s.setId(UUID.randomUUID());
            s.setName(step.getName());
            s.setService(step.getService());
            s.setStatus(StepStatus.PENDING);
            s.setRetryCount(0);
            s.setMaxRetries(step.getMaxRetries());
            s.setDependsOn(new ArrayList<>(step.getDependsOnList()));
            s.setCreatedAt(Instant.now());
            s.setUpdatedAt(Instant.now());
            s.setWorkflow(entity);
            return s;
        }).collect(Collectors.toList());

        entity.setSteps(stepEntities);
        workflowRepo.save(entity);
    }

    public WorkflowEntity getWorkflow(UUID id) {
        return workflowRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workflow not found with id: " + id));
    }

    public List<UUID> getFailedWorkflowIds() {
        return workflowRepo.findByStatus("FAILED")
                .stream()
                .map(WorkflowEntity::getId)
                .collect(Collectors.toList());

    }
}
