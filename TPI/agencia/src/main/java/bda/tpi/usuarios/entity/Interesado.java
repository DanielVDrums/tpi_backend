package bda.tpi.usuarios.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "interesados")
public class Interesado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tipo_documento")
    private String tipoDocumento;
    @Column(name = "documento")
    private Long documento;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "restringido")
    private Boolean restringido;
    @Column(name = "nro_licencia")
    private Integer nroLicencia;
    @Column(name = "fecha_vencimiento_licencia")
    private Date fechaVencimientoLicencia;


    @OneToMany(mappedBy = "interesado")
    private List<Prueba> pruebas;
}
