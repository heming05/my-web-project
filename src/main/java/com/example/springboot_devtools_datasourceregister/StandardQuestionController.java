package com.example.springboot_devtools_datasourceregister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class StandardQuestionController {
    @Autowired
    private StandardQuestionService standardQuestionService;

    @PostMapping("/uploadStandardQuestion")
    public ResponseEntity<Map<String, Object>> uploadStandardQuestion(@RequestBody List<StandardQuestionEntity> questions) {
        try {
            standardQuestionService.saveStandardQuestions(questions);
            return ResponseEntity.ok(Collections.singletonMap("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }



}
