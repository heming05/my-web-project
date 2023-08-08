package com.example.springboot_devtools_datasourceregister;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSHRequest {
    private String host;
    private int port;
    private String username;
    private String password;

    private String name;

    private String description;

    // getters
    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    // setters
    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(SSHController.class);

    public void setName(String name) {
        this.name = name;
    }


}
