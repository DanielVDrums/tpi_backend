package bda.tpi.vehiculos.controller;

import bda.tpi.vehiculos.dto.IncidenteDTO;
import bda.tpi.vehiculos.dto.NotificacionPromDTO;
import bda.tpi.vehiculos.entity.Notificacion;
import bda.tpi.vehiculos.service.NotificacionServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacion")
public class NotificacionController {

    private final NotificacionServicio notificacionServicio;

    public NotificacionController(NotificacionServicio notificacionServicio) {
        this.notificacionServicio = notificacionServicio;
    }

    // Esta no se llama desde el gateway
    @GetMapping("/incidentes")
    public List<IncidenteDTO> obtenerVehiculosConIncidentes(){
        return notificacionServicio.obtenerVehiculoConIncidente()
                .stream().map(Notificacion::toIncidenteDTO).toList();
    }

    // permisos de empleado /notificacion/promocion
    @PostMapping("/enviarPromocion")
    public void enviarPromocion(@RequestBody NotificacionPromDTO notificacionPromDTO) {
        notificacionServicio.enviarNotificacionesPromocion(notificacionPromDTO);
    }
}
