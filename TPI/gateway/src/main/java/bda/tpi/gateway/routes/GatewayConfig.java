package bda.tpi.gateway.routes;

import bda.tpi.gateway.filters.VehiculoGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, VehiculoGatewayFilter filter) {
        return builder.routes()
                .route("pruebas-add-route", r -> r
                        .path("/pruebas/agregar")
                        .uri("http://127.0.0.1:8083/vehiculos"))
                .build();
    }
}

//@Configuration
//public class GatewayConfig {
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, VehiculoGatewayFilter filter) {
//        return builder.routes()
//                .route("pruebas-add-route", r -> r
//                        .path("/pruebas/agregar")
//                        .and()
//                        .method("POST")
//                        .filters(f -> f.filter(filter))
//                        .uri("http://127.0.0.1:8082/pruebas/add"))
//                .build();
//    }
//}
