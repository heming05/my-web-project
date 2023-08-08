package com.example.springboot_devtools_datasourceregister;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
public class SSHController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SSHController.class);

    @PostMapping("/test-ssh")
    public ResponseEntity<Map<String, String>> testSSH(@RequestBody SSHRequest sshRequest) {
        JSch jsch = new JSch();
        Session session = null;
        Map<String, String> response = new HashMap<>();

        try {
            LOGGER.info("开始进行SSH连接测试");
            LOGGER.info("host: {}, port: {}, username: {}, password: {}", sshRequest.getHost(), sshRequest.getPort(), sshRequest.getUsername(), sshRequest.getPassword());

            session = jsch.getSession(sshRequest.getUsername(), sshRequest.getHost(), sshRequest.getPort());
            session.setPassword(sshRequest.getPassword());
            session.setConfig("StrictHostKeyChecking", "no");

            LOGGER.info("尝试连接到服务器");
            session.connect();
            LOGGER.info("连接成功");

            response.put("message", "ok，连接成功");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("连接失败", e);
            response.put("message", "对不起，测试连接失败，原因：" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } finally {
            if (session != null && session.isConnected()) {
                LOGGER.info("断开连接");
                session.disconnect();
            }
        }
    }



    @Autowired
    private DataSourceService dataSourceService;

    @PostMapping("/create-ssh")
    public ResponseEntity<Map<String, String>> createSSH(@RequestBody SSHRequest sshRequest) {
        DataSourceEntity entity = new DataSourceEntity();
        entity.setName(sshRequest.getName());
        entity.setDescription(sshRequest.getDescription());
        entity.setHost(sshRequest.getHost());
        entity.setPort(sshRequest.getPort());
        entity.setUsername(sshRequest.getUsername());
        entity.setPassword(sshRequest.getPassword());
        // 可以添加其他字段的设置

        dataSourceService.save(entity);

        // 响应前端
        Map<String, String> response = new HashMap<>();
        response.put("message", "SSH源连接创建成功");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }






}
