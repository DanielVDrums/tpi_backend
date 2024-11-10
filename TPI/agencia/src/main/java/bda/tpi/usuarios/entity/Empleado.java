package bda.tpi.usuarios.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "empleados")
public class Empleado {
    @Id
    @Column(name = "legajo")
    private Integer legajo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "telefono_contacto")
    private Long telefono;

    @OneToMany(mappedBy = "empleado")
    private List<Prueba> pruebas;
}
