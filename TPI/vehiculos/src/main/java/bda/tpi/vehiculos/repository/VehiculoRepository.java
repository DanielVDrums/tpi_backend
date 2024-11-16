package bda.tpi.vehiculos.repository;

import bda.tpi.vehiculos.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {
}
