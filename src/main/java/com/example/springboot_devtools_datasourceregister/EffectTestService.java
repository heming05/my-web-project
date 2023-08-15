// EffectTestService.java
package com.example.springboot_devtools_datasourceregister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EffectTestService {

    private final EffectTestRepository effectTestRepository;

    @Autowired
    public EffectTestService(EffectTestRepository effectTestRepository) {
        this.effectTestRepository = effectTestRepository;
    }

    public EffectTestEntity save(EffectTestEntity entity) {
        return effectTestRepository.save(entity); // 修正了这里
    }

    public Page<EffectTestEntity> findAll(Pageable pageable) {
        return effectTestRepository.findAll(pageable); // 修正了这里
    }

    public Optional<EffectTestEntity> findByJobName(String jobName) {
        return effectTestRepository.findByJobname(jobName);
    }

}
