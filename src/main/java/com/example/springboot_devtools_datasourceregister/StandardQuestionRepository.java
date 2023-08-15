package com.example.springboot_devtools_datasourceregister;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StandardQuestionRepository extends JpaRepository<StandardQuestionEntity, Long> {
}
