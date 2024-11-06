import jakarta.persistence.*;

public class Marcas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    private String nombre;

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Marcas(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Marcas(){

    }




}