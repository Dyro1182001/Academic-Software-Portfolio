package sistemaautogestion;

import java.util.Date;

/**
 *
 * @author alumnoFI
 */
public class Listamedico implements IListamedicos {

    nodomedico primero;
    nodomedico ultimo;
    ListaConsultas consultas;
    ListaDiaConsulta diaconsultas;
    int cantnodos;
    

    public nodomedico getUltimo() {
        return ultimo;
    }

    public void setUltimo(nodomedico ultimo) {
        this.ultimo = ultimo;
    }

    public Listamedico() {
        this.primero = null;
        this.ultimo = null;
        this.cantnodos = 0;
    }

    public nodomedico getPrimero() {
        return primero;
    }

    public void setPrimero(nodomedico primero) {
        this.primero = primero;
    }

   

    public int getCantnodos() {
        return cantnodos;
    }

    public void setCantnodos(int cantnodos) {
        this.cantnodos = cantnodos;
    }



    @Override
    public boolean esVacia() {
        return this.getPrimero() == null;
    }



@Override
public void agregar(String nombre, int codMedico, int tel, int especialidad) {
    nodomedico nuevo = new nodomedico(nombre, codMedico, tel, especialidad);

    if (this.esVacia() || nombre.compareToIgnoreCase(this.getPrimero().getNomM()) < 0) {
        nuevo.setSiguiente(this.getPrimero());
        this.setPrimero(nuevo);
        this.setUltimo(nuevo);
    } else {
        nodomedico actual = this.getPrimero();
        while (actual.getSiguiente() != null && nombre.compareToIgnoreCase(actual.getSiguiente().getNomM()) >= 0) {
            actual = actual.getSiguiente();
        }
        nuevo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevo);
    }

    this.cantnodos++;
}

@Override
    public void eliminarUltimoMedico(){
        nodomedico aux = this.getPrimero();
        
        if(this.cantnodos == 1){
            this.setPrimero(null);
            this.setUltimo(null);
        }else if(this.cantnodos > 1){
            while(aux.getSiguiente() != this.getUltimo()){
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(null);
        }
        
        if(this.cantnodos >0){
            this.cantnodos = this.cantnodos - 1;
        }
    }

    
    @Override
    public void eliminar(int nrom) {
        if (!this.esVacia()) {
            nodomedico encontrado = this.obtenermedico(nrom);
            if (encontrado != null) {
                if (this.cantnodos == 1) {
                    this.setPrimero(null);
                    this.setCantnodos(0);
                } else {
                    nodomedico aux = this.getPrimero();
                    if (this.getPrimero().getNroM() == nrom) {
                        this.setPrimero(this.getPrimero().getSiguiente());
                        this.cantnodos = this.cantnodos - 1;
                    }else if(this.getUltimo().getNroM() == nrom){
                        eliminarUltimoMedico();
                    } else {
                        while (aux.getSiguiente() != null) {
                            if (aux.getSiguiente().getNroM() == nrom) {
                                aux.setSiguiente(aux.getSiguiente().getSiguiente());
                                this.cantnodos = this.cantnodos - 1;
                            }
                            aux = aux.getSiguiente();

                        }
                    }
                }

            } else {

                System.out.println("El elemento buscado no esta en la lista");
            }

        } else {

            System.out.println("Esta vacia");
        }

    }

    @Override
    public nodomedico obtenermedico(int nrom) {
        nodomedico encontrado = null;
        if (!this.esVacia()) {
           
            nodomedico aux = this.primero;
            
            while (aux != null && encontrado == null) {
                if (aux.getNroM() == nrom) {
                    encontrado = aux;
                }
                aux = aux.getSiguiente();
            }
        }
        return encontrado;
    }

    @Override
    public String listar() {
        nodomedico aux=this.getPrimero();
        
        String retornar = "\n";
        
        while (aux!=null){
            retornar = retornar + aux.getNroM()+ " - " +aux.getNomM() + " - " + aux.getTel() + " - " + aux.getEspecialidad() + "\n";
            aux=aux.getSiguiente();
        
        }
        
        return retornar;
        
    }

    @Override
    public int cantnodos() {
        return this.getCantnodos();
    }
    
    @Override
    public int ObtenerCantidadDePacientes(int i, int j, int año){
        nodomedico aux=this.getPrimero();
        
        int retornar = 0;
        
        while (aux!=null){
            if(aux.getEspecialidad() == j){
                retornar += aux.lc.ObtenerCantPacientes(i, año);   
            }
            aux=aux.getSiguiente();
        
        }
        
        return retornar;
    }
}
