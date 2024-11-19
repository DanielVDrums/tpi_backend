package bda.tpi.usuarios.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "pruebas")
public class Prueba {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "fecha_hora_inicio")
    private Date fechaHoraInicio;
    @Column(name = "fecha_hora_fin")
    private Date fechaHoraFin;
    @Column(name = "comentarios")
    private String comentarios;

    @ManyToOne
    @JoinColumn(name = "id_empleado", referencedColumnName = "legajo")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "id_interesado", referencedColumnName = "id")
    private Interesado interesado;

    @Column(name = "id_vehiculo")
    private Integer idVehiculo;

    public Prueba() {
    }

    public Prueba(Date fechaHoraInicio, Date fechaHoraFin, Empleado empleado, Interesado interesado, Integer idVehiculo) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.empleado = empleado;
        this.interesado = interesado;
        this.idVehiculo = idVehiculo;
    }
}
