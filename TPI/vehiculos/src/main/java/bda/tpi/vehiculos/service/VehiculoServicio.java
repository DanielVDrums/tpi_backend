package bda.tpi.vehiculos.service;

import bda.tpi.vehiculos.dto.VehiculoDTO;
import bda.tpi.vehiculos.entity.Modelo;
import bda.tpi.vehiculos.entity.Vehiculo;
import bda.tpi.vehiculos.repository.ModeloRepository;
import bda.tpi.vehiculos.repository.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehiculoServicio {

    private final VehiculoRepository vehiculoRepository;
    private final ModeloRepository modeloRepository;

    public VehiculoServicio(VehiculoRepository vehiculoRepository, ModeloRepository modeloRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.modeloRepository = modeloRepository;
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

    public Optional<VehiculoDTO> obtenerVehiculoPorPatente(String patente) {
        return vehiculoRepository.findByPatente(patente)
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
}
