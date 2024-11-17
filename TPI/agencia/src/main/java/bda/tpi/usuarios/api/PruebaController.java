package bda.tpi.usuarios.api;

import bda.tpi.usuarios.dto.PruebaDTO;
import bda.tpi.usuarios.entity.Prueba;
import bda.tpi.usuarios.service.PruebaServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pruebas")
public class PruebaController {
    private final PruebaServicio pruebaServicio;

    public PruebaController(PruebaServicio pruebaServicio) {this.pruebaServicio = pruebaServicio;}

    @GetMapping
    public ResponseEntity<?> obtenerPruebas() {
        List<Prueba> pruebas = pruebaServicio.obtenerPruebas();
        if (pruebas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay pruebas disponibles");
        } else {
            return ResponseEntity.ok(pruebas);
        }
    }

    // consigna 1.b
    @GetMapping("/enCurso")
    public ResponseEntity<?> obtenerPruebaEnCurso(@RequestParam(value = "fechaHora", required = false) String fechaIngresada) throws ParseException{
        Date fechaHora;
        if (fechaIngresada == null) {
            fechaHora = new Date();
        }else{
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            fechaHora = format.parse(fechaIngresada);
        }
        List<Prueba> pruebas = pruebaServicio.obtenerPruebasEnCursoPorFecha(fechaHora);
        if (pruebas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay pruebas disponibles");
        } else {
            return ResponseEntity.ok(pruebas);
        }
    }

    @PostMapping("/add")
    private Prueba agregarPrueba(@RequestBody @Valid PruebaDTO pruebaDTO) {
        //return pruebaServicio.agregarNuevaPrueba(pruebaDTO);
        return new Prueba();
    }
}
