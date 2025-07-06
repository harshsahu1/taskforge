package com.taskforge.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskforge.entity.WorkflowEntity;
import com.taskforge.service.WorkflowService;

import workflow.WorkflowOuterClass.Workflow;

@RestController
@RequestMapping("/workflow")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @PostMapping(consumes = "application/x-protobuf")
    public ResponseEntity<String> createWorkflow(@RequestBody Workflow workflowProto) {
        workflowService.saveWorkflow(workflowProto);
        return ResponseEntity.ok("Workflow created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowEntity> getWorkflow(@PathVariable UUID id) {
        WorkflowEntity workflow = workflowService.getWorkflow(id);
        return ResponseEntity.ok(workflow);
    }

    @PostMapping("/{id}/execute")
    public ResponseEntity<String> executeWorkflow(@PathVariable UUID id) {
        return ResponseEntity.accepted().body("Workflow execution started");
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancleWorkflow(@PathVariable UUID id) {
        return ResponseEntity.accepted().body("Workflow execution cancelled");
    }

    @PostMapping("/workflows?status=FAILED")
    public ResponseEntity<List<UUID>> getFailedWorkflows() {
        List<UUID> failedWorkflowIds = workflowService.getFailedWorkflowIds();
        return ResponseEntity.ok(failedWorkflowIds);
    }

    @PostMapping("/{id}/retry")
    public ResponseEntity<String> retryWorkflow(@PathVariable UUID id) {
        return ResponseEntity.accepted().body("Workflow execution restarted");
    }
}
