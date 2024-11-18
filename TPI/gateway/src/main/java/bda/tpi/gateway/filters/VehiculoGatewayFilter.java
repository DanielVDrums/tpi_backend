package bda.tpi.gateway.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import reactor.core.publisher.Mono;
import org.springframework.core.io.buffer.DataBuffer;

import java.net.URI;
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

//                     exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
//                     DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(modifiedBody.getBytes(StandardCharsets.UTF_8));
//                     return exchange.getResponse().writeWith(Mono.just(buffer));
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
//                     Enviar el nuevo JSON a otra URL
//                    return exchange.mutate()
//                            .request(r -> r.uri("http://127.0.0.1:8082/pruebas/add"))
//                            .body(BodyInserters.fromValue(modifiedBody))
//                            .build()
//                            .exchange();

//                    // 6. Crear una nueva solicitud con el body enriquecido
//                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate())
//                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                            .build();
//
//                    // Enviar la solicitud con el body enriquecido
//                    return chain.filter(exchange.mutate().request(mutatedRequest).build());

                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .uri(URI.create("http://127.0.0.1:8082/pruebas/add"))
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .build();

                    // Reemplazamos el body con los datos nuevos
                    ServerWebExchange modifiedExchange = exchange.mutate()
                            .request(modifiedRequest)
                            .build();

                    // Enviamos la solicitud modificada
                    return chain.filter(modifiedExchange);

//                    // Convertimos el nuevo cuerpo (JSON) a DataBuffer
//                    DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(modifiedBody.getBytes(StandardCharsets.UTF_8));
//
//                    // Creamos una nueva solicitud con el cuerpo modificado
//                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
//                            .uri(URI.create("http://127.0.0.1:8082/pruebas/add"))
//                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                            .build();
//
//                    // Creamos un nuevo intercambio con el cuerpo actualizado
//                    ServerWebExchange modifiedExchange = exchange.mutate()
//                            .request(modifiedRequest)
//                            .build();
//
//                    // Reemplazamos el body del request con el nuevo contenido
//                    return chain.filter(modifiedExchange.mutate()
//                            .request(request -> request.body(Flux.just(dataBuffer)))
//                            .build());
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
