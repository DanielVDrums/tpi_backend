import jakarta.persistence.*;

public class Modelos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    private int id_marca;
    private String descripcion;

    public void setId(int id) {
        this.id = id;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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



    public Modelos(int id, int id_marca, String descripcion) {
        this.id = id;
        this.id_marca = id_marca;
        this.descripcion = descripcion;
    }

    public Modelos(){

    }



}