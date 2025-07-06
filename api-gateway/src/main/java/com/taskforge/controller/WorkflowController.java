package com.taskforge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
