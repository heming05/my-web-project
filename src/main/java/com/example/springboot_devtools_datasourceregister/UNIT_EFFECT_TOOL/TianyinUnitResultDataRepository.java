package com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TianyinUnitResultDataRepository extends JpaRepository<TianyinUnitResultDataEntity, Long> {
    List<TianyinUnitResultDataEntity> findByJobname(String jobname);

    @Query("SELECT MAX(runBatchNo) FROM TianyinUnitResultDataEntity")
    Long findMaxRunBatchNo();

    Long countByRunBatchNo(Long runBatchNo);

    Long countByRunBatchNoAndCompareResult(Long runBatchNo, String compareResult);

    List<TianyinUnitResultDataEntity> findByJobnameAndRunBatchNo(String jobname, Long runBatchNo);

}

