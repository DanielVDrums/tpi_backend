import jakarta.persistence.*;
import java.util.List;

@Entity
public class Marcas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "marca", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Modelos> modelos;

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setModelos(List<Modelos> modelos) {
        this.modelos = modelos;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Modelos> getModelos() {
        return modelos;
    }

    public Marcas(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Marcas() {
    }
}
