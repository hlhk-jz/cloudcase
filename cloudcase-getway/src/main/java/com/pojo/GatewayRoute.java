package com.pojo;
import lombok.Data;
@Data
public class GatewayRoute {
    private Long id;

    private String serviceId;

    private String url;

    private String predicates;

    private String filters;
}
