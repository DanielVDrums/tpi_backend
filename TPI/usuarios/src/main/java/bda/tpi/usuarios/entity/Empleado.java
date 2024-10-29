package bda.tpi.usuarios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Empleado {
    @Id
    @Column(name = "emplegajo")
    private Integer legajo;
    @Column(name = "empnombre")
    private String nombre;
    @Column(name = "empapellido")
    private String apellido;
    @Column(name = "emptelefono")
    private Long telefono;
}
