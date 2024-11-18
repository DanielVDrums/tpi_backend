package bda.tpi.vehiculos.service;

import bda.tpi.vehiculos.dto.VehiculoDTO;
import bda.tpi.vehiculos.entity.Posicion;
import bda.tpi.vehiculos.entity.Vehiculo;
import bda.tpi.vehiculos.repository.VehiculoRepository;
import bda.tpi.vehiculos.API.ApiService;
import bda.tpi.vehiculos.API.ApiResponse;
import bda.tpi.vehiculos.entity.Notificacion;
import bda.tpi.vehiculos.repository.PosicionRepository;
import bda.tpi.vehiculos.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehiculoServicio {

    private final VehiculoRepository vehiculoRepository;
    private final PosicionRepository posicionRepository;
    private final NotificacionRepository notificacionRepository;
    private final ApiService apiService;

    public VehiculoServicio(
            VehiculoRepository vehiculoRepository,
            PosicionRepository posicionRepository,
            NotificacionRepository notificacionRepository,
            ApiService apiService) {
        this.vehiculoRepository = vehiculoRepository;
        this.posicionRepository = posicionRepository;
        this.notificacionRepository = notificacionRepository;
        this.apiService = apiService;
    }

//    public Vehiculo agregarNuevoVehiculo(VehiculoDTO vehiculoDTO) {
//        Vehiculo vehiculo = new Vehiculo();
//        vehiculo.setPatente(vehiculoDTO.patente());
//        vehiculo.setModelo(vehiculoDTO.idModelo());
//        return vehiculoRepository.save(vehiculo);
//    }

    public List<VehiculoDTO> obtenerTodosVehiculos() {
        return vehiculoRepository.findAll().stream()
                .map(vehiculo -> new VehiculoDTO(
                        vehiculo.getId(),
                        vehiculo.getPatente(),
                        vehiculo.getModelo().getId(),
                        vehiculo.getPosiciones().stream()
                                .map(Posicion::getId)
                                .collect(Collectors.toList())
                ))
                .toList();
    }

    public Optional<VehiculoDTO> obtenerVehiculoPorId(Integer id) {
        return vehiculoRepository.findById(id)
                .map(vehiculo -> new VehiculoDTO(
                        vehiculo.getId(),
                        vehiculo.getPatente(),
                        vehiculo.getModelo().getId(),
                        vehiculo.getPosiciones().stream()
                                .map(Posicion::getId)
                                .collect(Collectors.toList())
                ));
    }

    //servicio para evaluar las restricciones que dieron por API
    public void evaluarRestricciones(Integer vehiculoId) {

        //busca el vehiculo por el id
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo con ID " + vehiculoId + " no encontrado"));

        //avisar que las posiciones estan vacias, se muestra por consola
//        if (vehiculo.getPosiciones().isEmpty()) {
//            throw new IllegalStateException("Cargue posiciones");
//        }


        ApiResponse configuracion = apiService.obtenerJSON();

        // evalua restricciones para las posiciones del vehículo
        for (Posicion posicion : vehiculo.getPosiciones()) {
            double distancia = calcularDistancia(
                    configuracion.getCoordenadasAgencia().getLat(),
                    configuracion.getCoordenadasAgencia().getLon(),
                    posicion.getLatitud(),
                    posicion.getLongitud()
            );

            // evalua si está fuera del radio permitido
            if (distancia > configuracion.getRadioAdmitidoKm()) {
                generarNotificacion("El vehículo está fuera del radio permitido.", vehiculo);
            }

            // evalua si está en una zona restringida
            for (ApiResponse.ZonaRestringida zona : configuracion.getZonasRestringidas()) {
                if (estaEnZonaRestringida(posicion, zona)) {
                    generarNotificacion("El vehículo está en una zona restringida.", vehiculo);
                }
            }
        }
    }

    //calcula la distancia con una formula de Haversine (la primera que me tiro gpt)
    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radio de la Tierra en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    //valida si esta en zona
    private boolean estaEnZonaRestringida(Posicion posicion, ApiResponse.ZonaRestringida zona) {
        return posicion.getLatitud() <= zona.getNoroeste().getLat() &&
                posicion.getLatitud() >= zona.getSureste().getLat() &&
                posicion.getLongitud() >= zona.getNoroeste().getLon() &&
                posicion.getLongitud() <= zona.getSureste().getLon();
    }

    //genera la notificacion y guarda en BD
    private void generarNotificacion(String mensaje, Vehiculo vehiculo) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(mensaje);
        notificacion.setVehiculo(vehiculo);
        notificacionRepository.save(notificacion);
    }
}
