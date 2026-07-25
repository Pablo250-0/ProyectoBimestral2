package Model;

public class Usuario extends Persona{

    private String codigoUsuario;
    private CarreraIngenieria carrera;
    private String correo;
    private Rol rol;

    public Usuario(String codigoUsuario, CarreraIngenieria carrera, String correo, Rol rol, String cedula, String nombres, String telefono, String direccion) {
        super(cedula, nombres, telefono, direccion);
        this.codigoUsuario = codigoUsuario;
        this.carrera = carrera;
        this.correo = correo;
        this.rol = rol;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public CarreraIngenieria getCarrera() {
        return carrera;
    }

    public void setCarrera(CarreraIngenieria carrera) {
        this.carrera = carrera;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" + "codigoUsuario=" + codigoUsuario + ", carrera=" + carrera + ", correo=" + correo + ", rol=" + rol + '}';
    }
 
}
