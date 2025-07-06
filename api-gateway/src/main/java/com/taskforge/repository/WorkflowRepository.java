package com.taskforge.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskforge.entity.WorkflowEntity;

@Repository
public interface WorkflowRepository extends JpaRepository<WorkflowEntity, UUID> {
}

