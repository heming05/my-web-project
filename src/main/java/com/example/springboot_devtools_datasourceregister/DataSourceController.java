package com.example.springboot_devtools_datasourceregister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataSourceController {

    private final DataSourceService dataSourceService;
    private final DataSourceRepository dataSourceRepository;

    @Autowired
    public DataSourceController(DataSourceService dataSourceService, DataSourceRepository dataSourceRepository) {
        this.dataSourceService = dataSourceService;
        this.dataSourceRepository = dataSourceRepository;
    }

    @GetMapping("/get-data")
    public Page<DataSourceEntity> getData(@PageableDefault(size = 20) Pageable pageable) {
        return dataSourceService.findAll(pageable);
    }

    @PutMapping("/update-datasource/{name}")
    public ResponseEntity<DataSourceEntity> updateDataSource(@PathVariable String name, @RequestBody DataSourceEntity dataSource) {
        DataSourceEntity updatedDataSource = dataSourceService.updateDataSource(name, dataSource);
        return ResponseEntity.ok(updatedDataSource);
    }


    @DeleteMapping("/delete-ssh/{name}")
    public ResponseEntity<?> deleteDataSource(@PathVariable String name) {
        try {
            dataSourceRepository.deleteByName(name); // 通过实例调用方法
            return ResponseEntity.ok().build(); // 返回200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 返回500错误
        }
    }

}
