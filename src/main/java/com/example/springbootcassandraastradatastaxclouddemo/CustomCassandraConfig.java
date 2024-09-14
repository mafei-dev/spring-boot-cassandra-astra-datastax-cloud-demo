package com.example.springbootcassandraastradatastaxclouddemo;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.config.ProgrammaticDriverConfigLoaderBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.time.Duration;

@Configuration
public class CustomCassandraConfig {


    @Value("${cassandra.cloud-secure-connect-bundle}")
    private File cloudSecureConnectBundle;
    @Value("${cassandra.keyspace:default_keyspace}")
    private String keyspace;
    @Value("${cassandra.client-id}")
    private String clientId;
    @Value("${cassandra.secret}")
    private String secret;
    @Value("${spring.application.name}")
    private String appName;



    @Bean
    public CqlSession cqlSession() {

        ProgrammaticDriverConfigLoaderBuilder configLoaderBuilder = DriverConfigLoader.programmaticBuilder()
                .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(10))
                .withDuration(DefaultDriverOption.CONNECTION_INIT_QUERY_TIMEOUT, Duration.ofSeconds(10));

        CqlSession cqlSession = CqlSession.builder()
                .withApplicationName(this.appName)
                .withConfigLoader(configLoaderBuilder.build())
                .withCloudSecureConnectBundle(this.cloudSecureConnectBundle.toPath())
                .withKeyspace(this.keyspace)
                .withAuthCredentials(
                        this.clientId,
                        this.secret
                )
                .build();
        System.out.println("cqlSession = " + cqlSession.getName());
        return cqlSession;
    }
}
