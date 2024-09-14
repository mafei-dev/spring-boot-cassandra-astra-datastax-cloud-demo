package com.example.springbootcassandraastradatastaxclouddemo;

import com.datastax.oss.driver.api.core.CqlSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class CassandraTestController {

    private final CqlSession cqlSession;

    @GetMapping("/test")
    public Object test() {
        this.cqlSession.execute("CREATE TABLE IF NOT EXISTS example (id uuid, name text, PRIMARY KEY (id))");
        this.cqlSession.execute("INSERT INTO example (id, name) VALUES (" + UUID.randomUUID() + ", 'test')");
        return this.cqlSession.execute("SELECT * FROM example").all().
                stream()
                .map(row -> ExampleDto.builder()
                        .id(row.getUuid("id").toString())
                        .name(row.getString("name"))
                        .build()
                )
                .toList();
    }
}
