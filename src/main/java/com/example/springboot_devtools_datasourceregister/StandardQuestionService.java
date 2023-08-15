package com.example.springboot_devtools_datasourceregister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandardQuestionService {
    @Autowired
    private StandardQuestionRepository standardQuestionRepository;

    public void saveStandardQuestions(List<StandardQuestionEntity> questions) {
        standardQuestionRepository.saveAll(questions);
    }
}

