package com.example.springboot_devtools_datasourceregister;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface EffectTestRepository extends JpaRepository<EffectTestEntity, Integer> {

    Optional<EffectTestEntity> findByJobname(String jobName);

    @Transactional
    @Modifying
    @Query("delete from EffectTestEntity s where s.jobname = ?1")
    void deleteByJobname(String jobname);
}


