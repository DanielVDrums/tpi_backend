package bda.tpi.vehiculos.service;

import bda.tpi.vehiculos.API.ApiResponse;
import bda.tpi.vehiculos.API.ApiService;
import bda.tpi.vehiculos.dto.VehiculoDTO;
import bda.tpi.vehiculos.entity.Modelo;
import bda.tpi.vehiculos.entity.Notificacion;
import bda.tpi.vehiculos.entity.Vehiculo;
import bda.tpi.vehiculos.repository.ModeloRepository;
import bda.tpi.vehiculos.repository.NotificacionRepository;
import bda.tpi.vehiculos.repository.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehiculoServicio {

    private final VehiculoRepository vehiculoRepository;
    private final ModeloRepository modeloRepository;
    private final NotificacionRepository notificacionRepository;
    private final ApiService apiService;

    public VehiculoServicio(VehiculoRepository vehiculoRepository, ModeloRepository modeloRepository, NotificacionRepository notificacionRepository, ApiService apiService) {
        this.vehiculoRepository = vehiculoRepository;
        this.modeloRepository = modeloRepository;
        this.notificacionRepository = notificacionRepository;
        this.apiService = apiService;
    }

    public Vehiculo agregarNuevoVehiculo(VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPatente(vehiculoDTO.patente());

        Optional<Modelo> modeloOpt = modeloRepository.findById(vehiculoDTO.idModelo());
        if (modeloOpt.isPresent()) {
            vehiculo.setModelo(modeloOpt.get());
        } else {
            throw new IllegalArgumentException("Modelo con ID " + vehiculoDTO.idModelo() + " no encontrado.");
        }

        return vehiculoRepository.save(vehiculo);
    }

    public List<VehiculoDTO> obtenerTodosVehiculos() {
        return vehiculoRepository.findAll().stream()
                .map(vehiculo -> new VehiculoDTO(
                        vehiculo.getId(),
                        vehiculo.getPatente(),
                        vehiculo.getModelo().getId(),
                        vehiculo.getPosiciones()
                                .stream()
                                .map(posicion -> Integer.valueOf(posicion.getId())) // Forzamos a Integer
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public Optional<VehiculoDTO> obtenerVehiculoPorId(Integer id) {
        return vehiculoRepository.findById(id)
                .map(vehiculo -> new VehiculoDTO(
                        vehiculo.getId(),
                        vehiculo.getPatente(),
                        vehiculo.getModelo().getId(),
                        vehiculo.getPosiciones()
                                .stream()
                                .map(posicion -> Integer.valueOf(posicion.getId()))
                                .collect(Collectors.toList())
                ));
    }

    public void generarNotificacion(String mensaje, Vehiculo vehiculo) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(mensaje);
        notificacion.setVehiculo(vehiculo);
        notificacion.setFechaHora(LocalDateTime.now());
        notificacionRepository.save(notificacion);
    }










//    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
//        final int RADIO_TIERRA = 6371; // Radio de la Tierra en km
//
//        double dLat = Math.toRadians(lat2 - lat1); // Diferencia en latitud en radianes
//        double dLon = Math.toRadians(lon2 - lon1); // Diferencia en longitud en radianes
//
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
//
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//        return RADIO_TIERRA * c; // Distancia en km
//    }




//    public void evaluarPosicionVehiculo(Integer vehiculoId, double latitud, double longitud) {
//        Optional<Vehiculo> vehiculoOpt = vehiculoRepository.findById(vehiculoId);
//
//
//        if (vehiculoOpt.isPresent()) {
//            Vehiculo vehiculo = vehiculoOpt.get();
//
//            ApiResponse configuracion = apiService.obtenerJSON();
//
//
//            double distancia = calcularDistancia(
//                    latitud, longitud,
//                    configuracion.getCoordenadasAgencia().getLat(),
//                    configuracion.getCoordenadasAgencia().getLon()
//            );
//
//            if (distancia > configuracion.getRadioAdmitidoKm()) {
//                generarNotificacion(
//                        "El vehículo está fuera del radio permitido. Distancia: " + distancia + " km.",
//                        vehiculo
//                );
//            }
//
//            // Verificar si el vehículo está en una zona restringida
//            for (ZonaRestringida zona : configuracion.getZonasRestringidas()) {
//                if (estaEnZonaRestringida(latitud, longitud, zona)) {
//                    generarNotificacion(
//                            "El vehículo está en una zona restringida.",
//                            vehiculo
//                    );
//                    break;
//                }
//            }
//        } else {
//            throw new IllegalArgumentException("Vehículo con ID " + vehiculoId + " no encontrado.");
//        }
//    }


}
