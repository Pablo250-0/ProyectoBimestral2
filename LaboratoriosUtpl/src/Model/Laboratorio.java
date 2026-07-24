package Model;

import java.util.ArrayList;

public class Laboratorio {
 
    private String codigoLab;
    private String nombreLab;
    private String edificio;
    private String aula;
    private int capacidad;
    private ArrayList<Activo> activos;

    public Laboratorio(String codigoLab, String nombreLab, String edificio, String aula, int capacidad, ArrayList<Activo> activos) {
        this.codigoLab = codigoLab;
        this.nombreLab = nombreLab;
        this.edificio = edificio;
        this.aula = aula;
        this.capacidad = capacidad;
        this.activos = new ArrayList<>();
    }

    public String getCodigoLab() {
        return codigoLab;
    }

    public void setCodigoLab(String codigoLab) {
        this.codigoLab = codigoLab;
    }

    public String getNombreLab() {
        return nombreLab;
    }

    public void setNombreLab(String nombreLab) {
        this.nombreLab = nombreLab;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public ArrayList<Activo> getActivos() {
        return activos;
    }

    public void setActivos(ArrayList<Activo> activos) {
        this.activos = activos;
    }

    @Override
    public String toString() {
        return "Laboratorio{" + "codigoLab=" + codigoLab + ", nombreLab=" + nombreLab + ", edificio=" + edificio + ", aula=" + aula + ", capacidad=" + capacidad + ", activos=" + activos + '}';
    }
    
    
}
