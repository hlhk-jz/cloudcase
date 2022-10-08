package com.mapper;

import com.pojo.GatewayRoute;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GatewayRouteMapper {
    List<GatewayRoute> queryAllRoutes();
}
