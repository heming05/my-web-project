package com.example.springboot_devtools_datasourceregister;

import jakarta.persistence.*;

@Entity
@Table(name = "tianyin_effect_unit_upload_query")
public class StandardQuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobname;
    private String query;

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for jobname
    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    // Getter and Setter for query
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}

