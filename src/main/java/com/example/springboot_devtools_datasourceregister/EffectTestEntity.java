package com.example.springboot_devtools_datasourceregister;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tianyin_web_effect_test")
public class EffectTestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobname;

    @Column(name = "product_name")
    private String productName;

    private String endpoint;
    private String api;
    private String authorization;
    private String parallel;

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date create_time = new Date();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getJobname() {
        return jobname;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getApi() {
        return api;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setParallel(String parallel) {
        this.parallel = parallel;
    }

    public String getParallel() {
        return parallel;
    }

    public void setCreateTime(Date create_time) {
        this.create_time = create_time;
    }

    public Date getCreateTime() {
        return create_time;
    }
}
