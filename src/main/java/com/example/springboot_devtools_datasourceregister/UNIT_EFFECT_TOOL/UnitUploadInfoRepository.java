package com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitUploadInfoRepository extends JpaRepository<UnitUploadInfoEntity, Long> {
    List<UnitUploadInfoEntity> findByJobname(String jobname);
}
