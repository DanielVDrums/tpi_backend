package bda.tpi.usuarios.service;

import bda.tpi.usuarios.dto.IncidenteDTO;
import bda.tpi.usuarios.dto.PruebaDTO;
import bda.tpi.usuarios.entity.Empleado;
import bda.tpi.usuarios.entity.Interesado;
import bda.tpi.usuarios.entity.Prueba;
import bda.tpi.usuarios.repository.PruebaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PruebaServicio {
    private final PruebaRepository pruebaRepository;
    private final InteresadoServicio interesadoServicio;
    private final EmpleadoService empleadoService;
    private final RestTemplate restTemplate = new RestTemplate();

    public PruebaServicio(PruebaRepository pruebaRepository, InteresadoServicio interesadoServicio, EmpleadoService empleadoService) {
        this.pruebaRepository = pruebaRepository;
        this.interesadoServicio = interesadoServicio;
        this.empleadoService = empleadoService;
    }

    public Prueba agregarNuevaPrueba(PruebaDTO pruebaDTO) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Integer idVehiculo = this.buscarVehiculoPatente(pruebaDTO.vehiculoPatente());
        Empleado empleado = empleadoService.obtenerEmpleadoPorLegajo(pruebaDTO.legajo());
        Interesado interesado = interesadoServicio.obtenerInteresadoPorDocumento(pruebaDTO.usuarioDni());
        if (!interesado.licenciaVigente()) {
            System.out.println("no tiene licencia vigente");
        }
        if (interesado.getRestringido()) {
            System.out.println("Esta restringido");
        }
        try{
            Date fechaHoraInicio = format.parse(pruebaDTO.fechaHoraInicio());
            Date fechaHoraFin = format.parse(pruebaDTO.fechaHoraFin());
            return pruebaRepository.save(new Prueba(
                    fechaHoraInicio,
                    fechaHoraFin,
                    empleado,
                    interesado,
                    idVehiculo
            ));
        }catch (ParseException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // Consigna 1.b
    public List<Prueba> obtenerPruebasEnCursoPorFecha(Date fechaMomento) {
        return pruebaRepository.findPruebasEnCursoByFecha(fechaMomento);
    }

    public List<Prueba> obtenerPruebas() {
        return pruebaRepository.findAll();
    }

    public Integer buscarVehiculoPatente(String patente) {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity("http://127.0.0.1:8083/vehiculos/patente/" + patente, Map.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> bodyMapVehiculo = response.getBody();
                if (bodyMapVehiculo == null || bodyMapVehiculo.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehiculo no Encontrado");
                }
                Integer vehiculoId = (Integer) bodyMapVehiculo.get("id");
                return vehiculoId;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehiculo no Encontrado");
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehiculo no Encontrado");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el vehiculo", e);
            }
        }
    }

    public List<Prueba> obtenerPruebasConIncidentes() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ResponseEntity<List> response = restTemplate.getForEntity("http://127.0.0.1:8083/incidentes", List.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println(response.getBody());
                for (Object incidente : response.getBody()){
                    IncidenteDTO inc = objectMapper.convertValue(incidente, IncidenteDTO.class);
                    System.out.println(incidente);
                    inc.idVehiculo();
                    inc.patente();
                }
//                Map<String, Object> bodyMapIncidentes = response.getBody();
//                // Iterar sobre las entradas del Map
//                 for (Map.Entry<String, Object> entrada : bodyMapIncidentes.entrySet()) {
//                     String clave = entrada.getKey();
//                     Object valor = entrada.getValue();
//                     System.out.println("Clave: " + clave + ", Valor: " + valor); }
                List<Prueba> pruebas = new ArrayList<>();
                return pruebas;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehiculo no Encontrado");
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehiculo no Encontrado");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar el vehiculo", e);
            }
        }
    }
}
