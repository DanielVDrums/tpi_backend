package bda.tpi.vehiculos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "marcas")
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "marca", fetch = FetchType.LAZY)
    private List<Modelo> modelos;
}
