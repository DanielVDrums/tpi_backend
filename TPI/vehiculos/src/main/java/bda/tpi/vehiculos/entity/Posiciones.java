import jakarta.persistence.*;

public class Posiciones {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    private int id_vehiculo;
    private Date fecha_hora;
    private int latitud;
    private int longitud;

    public void setId(int id) {
        this.id = id;
    }

    public void setId_vehiculo(int id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public void setFecha_hora(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public void setLatitud(int latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }


    public int getId() {
        return id;
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public Date getFecha_hora() {
        return fecha_hora;
    }

    public int getLatitud() {
        return latitud;
    }

    public int getLongitud() {
        return longitud;
    }



    public Posiciones(int id, int id_vehiculo, Date fecha_hora, int latitud, int longitud) {
        this.id = id;
        this.id_vehiculo = id_vehiculo;
        this.fecha_hora = fecha_hora;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Posiciones(){

    }
}