package bda.tpi.vehiculos.dto;

import java.util.List;


public record VehiculoDTO(
        Integer id,
        String patente,
        Integer idModelo,
        List<Integer> posiciones
) {
}

