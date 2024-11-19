package bda.tpi.usuarios.service;

import bda.tpi.usuarios.dto.PruebaDTO;
import bda.tpi.usuarios.entity.Empleado;
import bda.tpi.usuarios.entity.Interesado;
import bda.tpi.usuarios.entity.Prueba;
import bda.tpi.usuarios.repository.EmpleadoRepository;
import bda.tpi.usuarios.repository.PruebaRepository;
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
    private final EmpleadoService empleadoService;

    public PruebaServicio(PruebaRepository pruebaRepository, InteresadoServicio interesadoServicio, EmpleadoService empleadoService) {
        this.pruebaRepository = pruebaRepository;
        this.interesadoServicio = interesadoServicio;
        this.empleadoService = empleadoService;
    }

    public Prueba agregarNuevaPrueba(PruebaDTO pruebaDTO) {
        Integer idVehiculo = this.buscarVehiculoPatente(pruebaDTO.vehiculoPatente());
        Empleado empleado = empleadoService.obtenerEmpleadoPorLegajo(pruebaDTO.legajo());
        Interesado interesado = interesadoServicio.obtenerInteresadoPorDocumento(pruebaDTO.usuarioDni());
        if (!interesado.licenciaVigente()) {
            System.out.println("no tiene licencia vigente");
        }
        if (interesado.getRestringido()) {
            System.out.println("Esta restringido");
        }
        return pruebaRepository.save(new Prueba(
                pruebaDTO.fechaHoraInicio(),
                pruebaDTO.fechaHoraFin(),
                empleado,
                interesado,
                idVehiculo
        ));
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
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehiculo no Encontrado");
            }
            Integer vehiculoId = (Integer) bodyMapVehiculo.get("id");
            return vehiculoId;
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehiculo no Encontrado");
        }
    }
}
