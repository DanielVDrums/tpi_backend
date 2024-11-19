package bda.tpi.usuarios.dto;

import java.sql.Date;

public record PruebaDTO(
        Integer legajo,
        String vehiculoPatente,
        Long usuarioDni,
        Date fechaHoraInicio,
        Date fechaHoraFin
) {}
