package bda.tpi.usuarios.service;

import bda.tpi.usuarios.entity.Interesado;
import bda.tpi.usuarios.repository.InteresadoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InteresadoServicio {
    private final InteresadoRepository interesadoRepository;

    public InteresadoServicio(InteresadoRepository interesadoRepository) { this.interesadoRepository = interesadoRepository; }

    public Optional<Interesado> obtenerInteresadoPorId(Integer id) {
        return interesadoRepository.findById(id);
    }

}
