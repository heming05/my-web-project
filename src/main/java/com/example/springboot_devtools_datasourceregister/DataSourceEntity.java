package com.example.springboot_devtools_datasourceregister;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tianyin_web_datasource")
public class DataSourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String host;
    private int port;
    private String username;
    private String password;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at = new Date();

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setHost(String host) {
        this.host = host;
    }

    public String getHost(){
        return host;
    }


    public void setPort(Integer port) { // 或者其它适当的类型，比如String
        this.port = port;
    }

    public int getPort(){
        return port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername(){
        return username;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setcreatedAt(Date created_at) {
        this.created_at = created_at;
    }
    public Date getCreatedAt() {
        return created_at;
    }










    // Getters and Setters
}
