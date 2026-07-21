package sistemaautogestion;

/**
 *
 * @author alumnoFI
 */
public class Listapaciente implements IListapacientes {

    nodopaciente primero;
    nodopaciente ultimo;
    int cantnodos;
    
    public Listapaciente(){
        this.primero = null;
        this.ultimo = null;
        this.cantnodos = 0;
    }

    public nodopaciente getPrimero() {
        return primero;
    }

    public void setPrimero(nodopaciente primero) {
        this.primero = primero;
    }

    public nodopaciente getUltimo() {
        return ultimo;
    }

    public void setUltimo(nodopaciente ultimo) {
        this.ultimo = ultimo;
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
    public void agregar(String nomp, int nrop, String direccion) {
        
            nodopaciente nuevo = new nodopaciente(nomp, nrop, direccion);
            
            if (this.esVacia()) {
                this.setPrimero(nuevo);
                this.setUltimo(nuevo);
            } else {
                nuevo.setSiguiente(this.getPrimero());
                this.setPrimero(nuevo);
            }
            
            this.cantnodos++;
    }

    @Override
    public void eliminarUltimoPaciente(){
        nodopaciente aux = this.getPrimero();
        
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
    public void eliminar(int nrop) {
        if (!this.esVacia()) {
            nodopaciente encontrado = this.obtenerpaciente(nrop);
            if (encontrado != null) {
                if (this.cantnodos == 1) {
                    this.setPrimero(null);
                    this.setCantnodos(0);
                } else {
                    nodopaciente aux = this.getPrimero();
                    if (this.getPrimero().getNrop() == nrop) {
                        this.setPrimero(this.getPrimero().getSiguiente());
                        this.cantnodos = this.cantnodos - 1;
                    }else if(this.getUltimo().getNrop() == nrop){
                        eliminarUltimoPaciente();
                    } else {
                        while (aux.getSiguiente() != null) {
                            if (aux.getSiguiente().getNrop() == nrop) {
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
    public nodopaciente obtenerpaciente(int nrop) {
        nodopaciente encontrado = null;
        if (!this.esVacia()) {
            nodopaciente aux = this.getPrimero();
            while (aux != null && encontrado == null) {
                if (aux.getNrop() == nrop) {
                    encontrado = aux;
                }
                aux = aux.getSiguiente();
            }
        }
        return encontrado;
    }
    
    

    @Override
    public String listar() {
        nodopaciente aux=this.getPrimero();
        
        String retornar = "" + "\n";
        
        while (aux!=null){
            retornar += aux.getNrop()+ " - " +aux.getNomp() + "\n";
            aux=aux.getSiguiente();
        
        }
        
        return retornar;
    }
    
    

    @Override
    public int cantnodos() {
        return this.getCantnodos();
    }

}
