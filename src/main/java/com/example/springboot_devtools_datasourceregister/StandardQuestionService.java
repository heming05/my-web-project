package com.example.springboot_devtools_datasourceregister;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StandardQuestionService {
    private final StandardQuestionRepository standardQuestionRepository;

    public StandardQuestionService(StandardQuestionRepository standardQuestionRepository) {
        this.standardQuestionRepository = standardQuestionRepository;
    }

    public void saveStandardQuestions(List<StandardQuestionEntity> questions) {
        if (!questions.isEmpty()) {
            String jobname = questions.get(0).getJobname();
            deleteByJobname(jobname);
            standardQuestionRepository.saveAll(questions);
        }
    }

    public void deleteByJobname(String jobname) {
        standardQuestionRepository.deleteByJobname(jobname);
    }
}
