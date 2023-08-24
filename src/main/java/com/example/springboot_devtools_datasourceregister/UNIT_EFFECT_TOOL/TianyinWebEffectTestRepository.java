package com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TianyinWebEffectTestRepository extends JpaRepository<TianyinWebEffectTestEntity, Long> {
    List<TianyinWebEffectTestEntity> findByJobname(String jobname);
}
