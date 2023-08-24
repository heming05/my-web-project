package com.example.springboot_devtools_datasourceregister.UNIT_EFFECT_TOOL;

import jakarta.persistence.*;

@Entity
@Table(name = "tianyin_unit_uploadinfo")
public class UnitUploadInfoEntity {
    // Assuming the fields are 'id', 'jobname', 'standard_query', 'standard_answer', and corresponding getters and setters

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobname;
    @Column(columnDefinition="TEXT")
    private String standard_query;
    @Column(columnDefinition="TEXT")
    private String standard_answer;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getStandard_query() {
        return standard_query;
    }

    public void setStandard_query(String standard_query) {
        this.standard_query = standard_query;
    }

    public String getStandard_answer() {
        return standard_answer;
    }

    public void setStandard_answer(String standard_answer) {
        this.standard_answer = standard_answer;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    // Getters and setters
}
