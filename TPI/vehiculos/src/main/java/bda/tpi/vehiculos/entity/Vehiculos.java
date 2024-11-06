import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="vehiculos")
public class Vehiculos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "patente")
    private String patente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo", referencedColumnName = "id")
    private Modelos modelo;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Posiciones> posiciones;

    public void setId(int id) {
        this.id = id;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public void setId_modelo(int id_modelo) {
        this.id_modelo = id_modelo;
    }

    public void setPosiciones(List<Posiciones> posiciones) {
        this.posiciones = posiciones;
    }

    public int getId() {
        return id;
    }

    public String getPatente() {
        return patente;
    }

    public int getId_modelo() {
        return id_modelo;
    }

    public List<Posiciones> getPosiciones() {
        return posiciones;
    }

    public Vehiculos(int id, String patente, int id_modelo) {
        this.id = id;
        this.patente = patente;
        this.id_modelo = id_modelo;
    }

    public Vehiculos() {
    }
}
