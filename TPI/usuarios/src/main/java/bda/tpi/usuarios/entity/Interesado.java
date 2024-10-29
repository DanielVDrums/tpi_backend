package bda.tpi.usuarios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Interesado {
    @Id
    @Column(name = "int-id")
    private Integer id;
    @Column(name = "int-documento")
    private Long documento;
}
