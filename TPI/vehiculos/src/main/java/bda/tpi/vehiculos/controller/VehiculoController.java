package bda.tpi.vehiculos.controller;

import bda.tpi.vehiculos.dto.VehiculoDTO;
import bda.tpi.vehiculos.entity.Vehiculo;
import bda.tpi.vehiculos.service.VehiculoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/patente/{patente}")
    public ResponseEntity<?> obtenerVehiculoPorPatente(@PathVariable String patente) {
        Optional<Vehiculo> vehiculo = vehiculoServicio.obtenerVehiculoPorPatente(patente);
        if(vehiculo.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(vehiculo.get().toDTO());
        }
    }

    //ESTO HAY QUE RECONTRA CAMBIARLO PORQUE SE OBTIENE POR API DEL PROFE
    //HAY QUE HACER EL DTO DEL JSON QUE NOS DA LA API DEL PROFE Y PASARSELO
    @GetMapping("/{id}/evaluarPosicion")
    public void evaluarRestriccionesPorVehiculo(@PathVariable Integer id) {
        vehiculoServicio.evaluarRestricciones(id);
    }
}

