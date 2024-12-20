package bda.tpi.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(p -> p
//                        .path("/empleado")
//                        .filters(f->f.addRequestHeader("hello", "world"))
//                        .uri("http://127.0.0.1:8082/empleados"))
//                .route(p -> p
//                        .path("/pruebas")
//                        .filters(f->f.addRequestHeader("hello", "world"))
//                        .uri("http://127.0.0.1:8082/pruebas"))
//                .build();
//    }
}
