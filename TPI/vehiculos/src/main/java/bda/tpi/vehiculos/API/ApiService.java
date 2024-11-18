package bda.tpi.vehiculos.API;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ApiResponse obtenerJSON() {
        String url = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";
        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);
        System.out.println("Respuesta obtenida: " + response);
        return response;
    }

}
