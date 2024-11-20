package bda.tpi.vehiculos.controller;

import bda.tpi.vehiculos.dto.IncidenteDTO;
import bda.tpi.vehiculos.dto.PosicionDTO;
import bda.tpi.vehiculos.dto.VehiculoDTO;
import bda.tpi.vehiculos.entity.Vehiculo;
import bda.tpi.vehiculos.service.VehiculoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {

    private final VehiculoServicio vehiculoServicio;

    @Autowired
    public VehiculoController(VehiculoServicio vehiculoServicio) {
        this.vehiculoServicio = vehiculoServicio;
    }

//    @PostMapping("/add")
//    public Vehiculo agregarVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
//        return vehiculoServicio.agregarNuevoVehiculo(vehiculoDTO);
//    }

    @GetMapping()
    public ResponseEntity<?> obtenerTodosVehiculos() {
        List<Vehiculo> vehiculos = vehiculoServicio.obtenerTodosVehiculos();
        if (vehiculos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(vehiculos.stream().map(v->v.toDTO()).toList());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerVehiculoPorId(@PathVariable Integer id) {
        Optional<Vehiculo> vehiculo = vehiculoServicio.obtenerVehiculoPorId(id);
        if (vehiculo.isPresent()) {
            return ResponseEntity.ok(vehiculo.get().toDTO());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // Permisos de vehiculo
    @GetMapping("/patente/{patente}")
    public ResponseEntity<?> obtenerVehiculoPorPatente(@PathVariable String patente) {
        Optional<Vehiculo> vehiculo = vehiculoServicio.obtenerVehiculoPorPatente(patente);
        if(vehiculo.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(vehiculo.get().toDTO());
        }
    }
    // Permisos de Vehiculo /vehiculos/recibirPosicion
    @PostMapping("/evaluarPosicion")
    public ResponseEntity<Void> evaluarRestricciones(@RequestBody PosicionDTO posicionDTO) {
        vehiculoServicio.evaluarRestricciones(posicionDTO);
        return ResponseEntity.ok().build();
    }

    // Permisos de administrador    /reportes/kilometros
    @GetMapping("/kilometrosRecorridos/{id}")
    public ResponseEntity<Double> kilometrosRecorridos(
            @PathVariable Integer id,
            @RequestParam(value = "fecha-inicio") String fechaInicio,
            @RequestParam(value = "fecha-fin") String fechaFin) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date fechaFinDate = format.parse(fechaFin);
        Date fechaInicioDate = format.parse(fechaInicio);
        double kilometros = vehiculoServicio.calcularKilometrosRecorridos(id, fechaInicioDate, fechaFinDate);
        return ResponseEntity.ok(kilometros);
    }
}

