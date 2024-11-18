package bda.tpi.vehiculos.repository;

import bda.tpi.vehiculos.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
}
