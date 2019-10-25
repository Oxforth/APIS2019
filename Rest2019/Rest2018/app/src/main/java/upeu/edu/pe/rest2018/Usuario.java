package upeu.edu.pe.rest2018;

public class Usuario {
    private int id;
    private String name;
    private String correo;
    private String pass;
    private String origen;

    public Usuario() {
    }

    public Usuario(int id, String name, String correo, String pass, String origen) {
        this.id = id;
        this.name = name;
        this.correo = correo;
        this.pass = pass;
        this.origen = origen;
    }

    public Usuario(String correo, String pass, String origen, String name) {
        this.name = name;
        this.correo = correo;
        this.pass = pass;
        this.origen = origen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    @Override
    public String toString() {
        return name + " - " + correo + " - " + origen;
    }
}
