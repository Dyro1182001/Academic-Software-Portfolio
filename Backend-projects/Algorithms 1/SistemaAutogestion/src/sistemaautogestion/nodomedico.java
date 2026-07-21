
package sistemaautogestion;

public class nodomedico {
    String nomM;
    int nroM;
    int tel;
    int especialidad;
    ListaConsultas lc;
    nodomedico siguiente;
    

    public nodomedico(String nomM, int nroM, int tel, int especialidad) {
        this.nomM = nomM;
        this.nroM = nroM;
        this.tel = tel;
        this.especialidad = especialidad;
        this.siguiente = null;
        this.lc = new ListaConsultas();
    }

    public String getNomM() {
        return nomM;
    }

    public void setNomM(String nomM) {
        this.nomM = nomM;
    }

    public int getNroM() {
        return nroM;
    }

    public void setNroM(int nroM) {
        this.nroM = nroM;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public int getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(int especialidad) {
        this.especialidad = especialidad;
    }

    public ListaConsultas getLc() {
        return lc;
    }

    public void setLc(ListaConsultas lc) {
        this.lc = lc;
    }

    public nodomedico getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(nodomedico siguiente) {
        this.siguiente = siguiente;
    }
    
}
