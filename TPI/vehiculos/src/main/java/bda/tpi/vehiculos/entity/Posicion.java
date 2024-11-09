package bda.tpi.vehiculos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "posiciones")
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id")
    private Vehiculo vehiculo;

    @Column(name = "fecha_hora")
    private Date fecha_hora;

    @Column(name = "latitud")
    private int latitud;

    @Column(name = "longitud")
    private int longitud;

}
