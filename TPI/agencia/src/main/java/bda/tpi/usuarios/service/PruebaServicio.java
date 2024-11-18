package bda.tpi.usuarios.service;

import bda.tpi.usuarios.api.dto.PruebaDTO;
import bda.tpi.usuarios.entity.Interesado;
import bda.tpi.usuarios.entity.Prueba;
import bda.tpi.usuarios.repository.PruebaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PruebaServicio {
    private final PruebaRepository pruebaRepository;
    private final InteresadoServicio interesadoServicio;

    public PruebaServicio(PruebaRepository pruebaRepository, InteresadoServicio interesadoServicio) {
        this.pruebaRepository = pruebaRepository;
        this.interesadoServicio = interesadoServicio;
    }

    public Prueba agregarNuevaPrueba(PruebaDTO pruebaDTO) {
        Optional<Interesado> resultado = interesadoServicio.obtenerInteresadoPorId(pruebaDTO.usuarioDni());
        return new Prueba();
    }

    // Consigna 1.b
    public List<Prueba> obtenerPruebasEnCursoPorFecha(Date fechaMomento) {
        return pruebaRepository.findPruebasEnCursoByFecha(fechaMomento);
    }

    public List<Prueba> obtenerPruebas() {
        return pruebaRepository.findAll();
    }
}
