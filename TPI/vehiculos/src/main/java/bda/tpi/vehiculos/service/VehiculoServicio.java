package bda.tpi.vehiculos.service;

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

    public VehiculoServicio(VehiculoRepository vehiculoRepository, ModeloRepository modeloRepository, NotificacionRepository notificacionRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.modeloRepository = modeloRepository;
        this.notificacionRepository = notificacionRepository;
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

    public void evaluarPosicionVehiculo(Integer vehiculoId, String nuevaPosicion, String limitePermitido, List<String> zonasPeligrosas) {
        Optional<Vehiculo> vehiculoOpt = vehiculoRepository.findById(vehiculoId);

        if (vehiculoOpt.isPresent()) {
            Vehiculo vehiculo = vehiculoOpt.get();

            if (!nuevaPosicion.equals(limitePermitido)) {
                generarNotificacion(
                        "El vehículo ha salido del límite permitido. Posición actual: " + nuevaPosicion,
                        vehiculo
                );
            }

            if (zonasPeligrosas.contains(nuevaPosicion)) {
                generarNotificacion(
                        "¡El vehículo está en una zona peligrosa! Posición actual: " + nuevaPosicion,
                        vehiculo
                );
            }
        } else {
            throw new IllegalArgumentException("Vehículo con ID " + vehiculoId + " no encontrado.");
        }
    }


    //PEDAZO DE CODIGO EN TEORIA MODIFICADO YA CON EL DTO DEL JSON DE LA API DEL PROFE

//    public void evaluarPosicionVehiculo(Integer vehiculoId, EvaluarPosicionDTO evaluarPosicionDTO) {
//        Optional<Vehiculo> vehiculoOpt = vehiculoRepository.findById(vehiculoId);
//
//        if (vehiculoOpt.isPresent()) {
//            Vehiculo vehiculo = vehiculoOpt.get();
//
//            String nuevaPosicion = evaluarPosicionDTO.getNuevaPosicion();
//            String limitePermitido = evaluarPosicionDTO.getLimitePermitido();
//            List<String> zonasPeligrosas = evaluarPosicionDTO.getZonasPeligrosas();
//
//            if (!nuevaPosicion.equals(limitePermitido)) {
//                generarNotificacion(
//                        "El vehículo ha salido del límite permitido. Posición actual: " + nuevaPosicion,
//                        vehiculo
//                );
//            }
//
//            if (zonasPeligrosas != null && zonasPeligrosas.contains(nuevaPosicion)) {
//                generarNotificacion(
//                        "¡El vehículo está en una zona peligrosa! Posición actual: " + nuevaPosicion,
//                        vehiculo
//                );
//            }
//        } else {
//            throw new IllegalArgumentException("Vehículo con ID " + vehiculoId + " no encontrado.");
//        }
//    }

}
