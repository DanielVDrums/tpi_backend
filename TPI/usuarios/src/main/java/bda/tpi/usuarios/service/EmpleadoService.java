package bda.tpi.usuarios.service;

import bda.tpi.usuarios.entity.Empleado;
import bda.tpi.usuarios.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> getAll() {return empleadoRepository.findAll();}
}
