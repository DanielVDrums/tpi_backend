package bda.tpi.vehiculos.controller;

import bda.tpi.vehiculos.dto.VehiculoDTO;
import bda.tpi.vehiculos.entity.Vehiculo;
import bda.tpi.vehiculos.service.VehiculoServicio;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("/add")
    public Vehiculo agregarVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
        return vehiculoServicio.agregarNuevoVehiculo(vehiculoDTO);
    }

    @GetMapping()
    public List<VehiculoDTO> obtenerTodosVehiculos() {
        return vehiculoServicio.obtenerTodosVehiculos();
    }

    @GetMapping("/{id}")
    public Optional<VehiculoDTO> obtenerVehiculoPorId(@PathVariable Integer id) {
        return vehiculoServicio.obtenerVehiculoPorId(id);
    }


    //ESTO HAY QUE RECONTRA CAMBIARLO PORQUE SE OBTIENE POR API DEL PROFE
    //HAY QUE HACER EL DTO DEL JSON QUE NOS DA LA API DEL PROFE Y PASARSELO
    @PostMapping("/{id}/evaluarPosicion")
    public void evaluarPosicion(@PathVariable Integer id,
                                @RequestParam String nuevaPosicion,
                                @RequestParam String limitePermitido,
                                @RequestParam List<String> zonasPeligrosas) {
        vehiculoServicio.evaluarPosicionVehiculo(id, nuevaPosicion, limitePermitido, zonasPeligrosas);
    }
}

