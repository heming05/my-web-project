package com.example.springboot_devtools_datasourceregister;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.transaction.annotation.Transactional;


@Service
public class DataSourceService {
    @Autowired
    private DataSourceRepository dataSourceRepository;

    public DataSourceEntity save(DataSourceEntity entity) {
        return dataSourceRepository.save(entity);
    }


    //读取数据
    @Autowired
    public DataSourceService(DataSourceRepository dataSourceRepository) {
        this.dataSourceRepository = dataSourceRepository;
    }

    public Page<DataSourceEntity> findAll(Pageable pageable) {
        return dataSourceRepository.findAll(pageable);
    }

    //更新数据
    @Transactional
    public DataSourceEntity updateDataSource(String name, DataSourceEntity newDataSource) {
        DataSourceEntity existingDataSource = dataSourceRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("DataSource not found with name: " + name));

        existingDataSource.setName(newDataSource.getName());
        existingDataSource.setDescription(newDataSource.getDescription());
        existingDataSource.setHost(newDataSource.getHost());
        existingDataSource.setPort(newDataSource.getPort());
        existingDataSource.setUsername(newDataSource.getUsername());
        existingDataSource.setPassword(newDataSource.getPassword());

        return existingDataSource; // 或者你可能想要保存并返回更新的实体
    }


}
