package com.example.springboot_devtools_datasourceregister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {
    @Autowired
    private EffectTestRepository effectTestRepository;
    @Autowired
    private StandardQuestionRepository standardQuestionRepository;

    @Transactional
    public void deleteTask(String jobname) {
        effectTestRepository.deleteByJobname(jobname);
        standardQuestionRepository.deleteByJobname(jobname);
    }
}
