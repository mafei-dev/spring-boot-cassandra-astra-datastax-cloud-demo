package com.example.springbootcassandraastradatastaxclouddemo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExampleDto {
    private String id;
    private String name;
}
