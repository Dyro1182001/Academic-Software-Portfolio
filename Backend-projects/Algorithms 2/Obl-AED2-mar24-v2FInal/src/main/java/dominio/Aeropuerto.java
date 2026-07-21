//Rodrigo delgado 299328
//Bruno iglesias 305558
package dominio;

public class Aeropuerto implements Comparable<Aeropuerto> {
    public String nombre;
    public String codigo;

    public Aeropuerto(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int compareTo(Aeropuerto a) {
        return this.codigo.compareTo(a.codigo);
    }

    @Override
    public String toString() {
        return getCodigo()+";"+getNombre()+"|";
    }
}
