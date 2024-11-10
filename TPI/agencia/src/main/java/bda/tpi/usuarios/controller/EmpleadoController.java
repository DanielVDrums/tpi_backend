package bda.tpi.usuarios.controller;

import bda.tpi.usuarios.entity.Empleado;
import bda.tpi.usuarios.service.EmpleadoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
    private final EmpleadoService empleadoService;
    public EmpleadoController(EmpleadoService empleadoService) {this.empleadoService = empleadoService;}
    @GetMapping
    public List<Empleado> getAllEmpleados() {return empleadoService.getAll(); }
}
