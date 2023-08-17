package com.example.springboot_devtools_datasourceregister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

        @DeleteMapping("/deleteTask")
        public ResponseEntity<Map<String, Object>> deleteTask(@RequestParam String jobname) {
            Map<String, Object> response = new HashMap<>();
            try {
                taskService.deleteTask(jobname);
                response.put("success", true);
                response.put("message", "Task deleted successfully");
            } catch (Exception e) {
                response.put("success", false);
                response.put("error", e.getMessage());
            }
            return ResponseEntity.ok(response);
        }


    }
