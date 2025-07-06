package com.taskforge.controller;

import com.taskforge.config.ProtobufConfig;
import com.taskforge.service.WorkflowService;

import workflow.WorkflowOuterClass.Workflow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkflowController.class)
@Import(ProtobufConfig.class)
class WorkflowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WorkflowService workflowService;

    @Test
    void testCreateWorkflow() throws Exception {
        byte[] protoBytes = Workflow.newBuilder()
            .setId("11111111-1111-1111-1111-111111111111")
            .setName("Test Workflow")
            .build()
                .toByteArray();

        mockMvc.perform(post("/workflow") 
                .contentType("application/x-protobuf")
                .content(protoBytes))
                .andExpect(status().isOk());

        mockMvc.perform(post("/workflow")
                .contentType("application/x-protobuf")
                .content(new byte[0])) 
                .andExpect(status().isBadRequest());
    }
}
