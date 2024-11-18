package bda.tpi.usuarios.service;

import bda.tpi.usuarios.dto.PruebaDTO;
import bda.tpi.usuarios.entity.Interesado;
import bda.tpi.usuarios.entity.Prueba;
import bda.tpi.usuarios.repository.PruebaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class PruebaServicio {
    private final PruebaRepository pruebaRepository;
    private final InteresadoServicio interesadoServicio;

    public PruebaServicio(PruebaRepository pruebaRepository, InteresadoServicio interesadoServicio) {
        this.pruebaRepository = pruebaRepository;
        this.interesadoServicio = interesadoServicio;
    }

    public Prueba agregarNuevaPrueba(PruebaDTO pruebaDTO) {
        Optional<Interesado> resultado = interesadoServicio.obtenerInteresadoPorId(pruebaDTO.idInteresado());
        return new Prueba();
    }

    // Consigna 1.b
    public List<Prueba> obtenerPruebasEnCursoPorFecha(Date fechaMomento) {
        return pruebaRepository.findPruebasEnCursoByFecha(fechaMomento);
    }

    public List<Prueba> obtenerPruebas() {
        return pruebaRepository.findAll();
    }

    public Integer buscarVehiculoPatente(String patente) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.getForEntity("http://127.0.0.1:8083/vehiculos/patente/"+patente, Map.class);
        if (response.getStatusCode().is2xxSuccessful()){
            Map<String, Object> bodyMapVehiculo = response.getBody();
            if (bodyMapVehiculo == null || bodyMapVehiculo.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El vehiculo no existe");
            }
        }
    }
}
