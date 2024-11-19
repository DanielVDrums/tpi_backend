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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Interesado(String tipoDocumento, Long documento, String nombre, String apellido, Boolean restringido, Integer nroLicencia, Date fechaVencimientoLicencia) {
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.restringido = restringido;
        this.nroLicencia = nroLicencia;
        this.fechaVencimientoLicencia = fechaVencimientoLicencia;
    }

    public Interesado() {
    }

    public boolean licenciaVigente() {
        Date fechaActual = new Date();
        return fechaActual.after(fechaVencimientoLicencia);
    }
}
