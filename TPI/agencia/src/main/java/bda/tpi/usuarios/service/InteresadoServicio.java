package bda.tpi.usuarios.service;

import bda.tpi.usuarios.dto.InteresadoDTO;
import bda.tpi.usuarios.entity.Interesado;
import bda.tpi.usuarios.repository.InteresadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class InteresadoServicio {
    private final InteresadoRepository interesadoRepository;

    public InteresadoServicio(InteresadoRepository interesadoRepository) { this.interesadoRepository = interesadoRepository; }

    public Optional<Interesado> obtenerInteresadoPorId(Integer id) {
        return interesadoRepository.findById(id);
    }

    public Interesado obtenerInteresadoPorDocumento(Long documento) {
        Optional<Interesado> interesado = interesadoRepository.findByDocumento(documento);
        if (interesado.isPresent()) {
            return interesado.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no Encontrado");
        }
    }

    public List<Interesado> obtenerTodosLosInteresados() {
        return interesadoRepository.findAll();
    }

    public Interesado agregarNuevoInteresado(InteresadoDTO interesadoDTO) {
        Interesado interesado = new Interesado(
                interesadoDTO.tipoDocumento(),
                interesadoDTO.documento(),
                interesadoDTO.nombre(),
                interesadoDTO.apellido(),
                interesadoDTO.restringido(),
                interesadoDTO.nroLicencia(),
                interesadoDTO.fechaVencimientoLicencia()
        );
        return interesadoRepository.save(interesado);
    }
}
