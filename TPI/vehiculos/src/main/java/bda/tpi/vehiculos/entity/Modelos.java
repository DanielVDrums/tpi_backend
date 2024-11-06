import jakarta.persistence.*;
import java.util.List;

@Entity
public class Modelos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_marca", referencedColumnName = "id")
    private Marcas marca;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vehiculos> vehiculos;

    public void setId(int id) {
        this.id = id;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setVehiculos(List<Vehiculos> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public int getId() {
        return id;
    }

    public int getId_marca() {
        return id_marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Vehiculos> getVehiculos() {
        return vehiculos;
    }

    public Modelos(int id, int id_marca, String descripcion) {
        this.id = id;
        this.id_marca = id_marca;
        this.descripcion = descripcion;
    }

    public Modelos() {
    }
}
