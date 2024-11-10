package bda.tpi.usuarios.controller;

import bda.tpi.usuarios.dto.PruebaDTO;
import bda.tpi.usuarios.entity.Prueba;
import bda.tpi.usuarios.service.PruebaServicio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pruebas")
public class PruebaController {
    private final PruebaServicio pruebaServicio;

    public PruebaController(PruebaServicio pruebaServicio) {this.pruebaServicio = pruebaServicio;}

    @PostMapping("/add")
    private Prueba addPrueba(@RequestBody PruebaDTO pruebaDTO) {
        //return pruebaServicio.agregarNuevaPrueba(pruebaDTO);
        return new Prueba();
    }
}
