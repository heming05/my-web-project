package com.example.springboot_devtools_datasourceregister;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface StandardQuestionRepository extends JpaRepository<StandardQuestionEntity, Long> {

    @Transactional
    @Modifying
    @Query("delete from StandardQuestionEntity s where s.jobname = ?1")
    void deleteByJobname(String jobname);
}

