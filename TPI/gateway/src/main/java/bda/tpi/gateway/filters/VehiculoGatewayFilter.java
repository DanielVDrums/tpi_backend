package bda.tpi.gateway.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.core.io.buffer.DataBuffer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class VehiculoGatewayFilter implements GatewayFilter {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        return exchange.getRequest().getBody().collectList().flatMap(data -> {
            try {
                String originalBody = data.get(0).toString(StandardCharsets.UTF_8);
                Map<String, Object> bodyMap = objectMapper.readValue(originalBody, Map.class);

                if (!bodyMap.containsKey("vehiculoPatente") || !bodyMap.containsKey("usuarioDni")) {
                    exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
                    return exchange.getResponse().setComplete();
                }

                String vehiculoPatente = (String) bodyMap.get("vehiculoPatente");
                String url = "http://localhost:8083/vehiculos/patente/" + vehiculoPatente;
                ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    Map<String, Object> bodyMapVehiculo = response.getBody();
                    if (bodyMapVehiculo == null || bodyMapVehiculo.isEmpty()) {
                        exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
                        return exchange.getResponse().setComplete();
                    }
                    bodyMap.put("vehiculo", bodyMapVehiculo);
                    bodyMap.remove("vehiculoPatente");

                     String modifiedBody = objectMapper.writeValueAsString(bodyMap);

                     exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                     DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(modifiedBody.getBytes(StandardCharsets.UTF_8));
                     return exchange.getResponse().writeWith(Mono.just(buffer));
//                     Establecer el nuevo cuerpo en la solicitud
//                    exchange.getRequest().mutate().header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
//                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(modifiedBody.getBytes(StandardCharsets.UTF_8));
//                    exchange.getRequest().mutate().body(Mono.just(buffer));
//                    // Continuar con el flujo normal
//                    return chain.filter(exchange);

//                    // Establecer el nuevo cuerpo en la solicitud
//                     exchange.getAttributes().put("MODIFIED_BODY", modifiedBody);
//                     // Continuar con el flujo normal
//                     return chain.filter(exchange);
                } else {
                    exchange.getResponse().setStatusCode(response.getStatusCode());
                    return exchange.getResponse().setComplete();
                }
            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                return exchange.getResponse().setComplete();
            }
        });
    }
}
