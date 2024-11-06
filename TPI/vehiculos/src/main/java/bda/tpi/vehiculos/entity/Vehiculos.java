
import jakarta.persistence.*;

@Entity
@Table(name="vehiculos")
public class Vehiculos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    private String patente;
    private int id_modelo;

    public void setId(int id) {
        this.id = id;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public void setId_modelo(int id_modelo) {
        this.id_modelo = id_modelo;
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

    public Vehiculos(int id, String patente, int id_modelo) {
        this.id = id;
        this.patente = patente;
        this.id_modelo = id_modelo;
    }

    public Vehiculos(){

    }


}