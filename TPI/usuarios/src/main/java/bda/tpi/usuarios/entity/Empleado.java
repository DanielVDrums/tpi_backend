package bda.tpi.usuarios.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Empleado {
    @Id
    @Column(name = "emp-legajo")
    private Integer legajo;
    @Column(name = "emp-nombre")
    private String nombre;
    @Column(name = "emp-apellido")
    private String apellido;
    @Column(name = "emp-telefono")
    private Long telefono;
}
