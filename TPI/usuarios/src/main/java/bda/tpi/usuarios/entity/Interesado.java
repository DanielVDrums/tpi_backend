package bda.tpi.usuarios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Interesado {
    @Id
    @Column(name = "clitipo")
    private Integer id;
}
