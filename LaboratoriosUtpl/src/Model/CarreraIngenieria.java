package Model;

public class CarreraIngenieria {

    private String codigo;
    private String nombre;
    private String facultad;

    public CarreraIngenieria(String codigo, String nombre, String facultad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.facultad = facultad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }
    
    
}
