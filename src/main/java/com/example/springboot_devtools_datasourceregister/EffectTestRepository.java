package com.example.springboot_devtools_datasourceregister;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EffectTestRepository extends JpaRepository<EffectTestEntity, Integer> {

    Optional<EffectTestEntity> findByJobname(String jobName);


}

