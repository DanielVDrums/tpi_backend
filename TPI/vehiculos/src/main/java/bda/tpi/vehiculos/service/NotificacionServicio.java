package bda.tpi.vehiculos.service;

import bda.tpi.vehiculos.entity.Notificacion;
import bda.tpi.vehiculos.repository.NotificacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionServicio {
    private final NotificacionRepository notificacionRepository;

    public NotificacionServicio(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public List<Notificacion> obtenerVehiculoConIncidente() {
        return notificacionRepository.findAll();
    }
}
