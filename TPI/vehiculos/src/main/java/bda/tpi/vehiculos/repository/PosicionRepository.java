package bda.tpi.vehiculos.repository;

import bda.tpi.vehiculos.entity.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosicionRepository extends JpaRepository<Posicion, Integer> {
}
