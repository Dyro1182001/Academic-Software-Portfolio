
package sistemaautogestion;

public class nodopaciente {
    String nomp;
    int nrop;
    String direccion;
    nodopaciente siguiente;
    Listaconsultapendiente lcp;
    Listahistoria lh;

    public nodopaciente(String nomp, int nrop, String direccion) {
        this.nrop = nrop;
        this.nomp = nomp;
        this.direccion = direccion;
        this.siguiente = null;
        this.lcp = new Listaconsultapendiente();
        this.lh = new Listahistoria();
    }

    public Listahistoria getLh() {
        return lh;
    }

    public void setLh(Listahistoria lh) {
        this.lh = lh;
    }

    public Listaconsultapendiente getLcp() {
        return lcp;
    }

    public void setLcp(Listaconsultapendiente lcp) {
        this.lcp = lcp;
    }

    public int getNrop() {
        return nrop;
    }

    public void setNrop(int nrop) {
        this.nrop = nrop;
    }

    public String getNomp() {
        return nomp;
    }

    public void setNomp(String nomp) {
        this.nomp = nomp;
    }

    public nodopaciente getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(nodopaciente siguiente) {
        this.siguiente = siguiente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    
}
