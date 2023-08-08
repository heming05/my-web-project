package com.example.springboot_devtools_datasourceregister;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface DataSourceRepository extends JpaRepository<DataSourceEntity, Long> {
    Optional<DataSourceEntity> findByName(String name);

    @Modifying
    @Transactional
    @Query("delete from DataSourceEntity e where e.name = ?1")
    void deleteByName(String name);
}

