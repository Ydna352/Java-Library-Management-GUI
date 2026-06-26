 


/**
 * Write a description of class Persona here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Persona
{
    public String nombre; //cambiado por andy
    public String id; //cambiado por andy
    private String email;
    private String telefono;
    
    public Persona(String nombre, String id) 
    {
        this.nombre = nombre;
        this.id = id;
        this.email = "usuario@servidor.com";
        this.telefono = "0000000000";
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }
    
    public String getEmail() {
        return new String(this.email);
    }
    
    public String getTelefono() {
        return new String(this.telefono);
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public abstract String obtenerTipo(); //en donde va
    
    public String toString() {
        return "Nombre: " + nombre + " ID: " + id + "\nEmail: " + email + "\nTeléfono: " + telefono + "\nTipo: " + obtenerTipo();
    }
}
