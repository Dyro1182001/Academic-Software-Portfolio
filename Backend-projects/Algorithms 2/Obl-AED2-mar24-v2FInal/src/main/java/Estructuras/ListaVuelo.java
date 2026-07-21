//Rodrigo delgado 299328
//Bruno iglesias 305558
package Estructuras;

import dominio.Aeropuerto;
import dominio.Vuelo;

public class ListaVuelo  extends Lista<Vuelo> {

    public int existe(String codigo) {
        int pos = 0;
        Nodo<Vuelo> aux = inicio;
        while (aux != null) {
            if (aux.getDato().getCodigoDeVuelo().equals(codigo)) {
                return pos;
            }
            aux = aux.getSiguiente();
            pos++;
        }
        return -1;
    }

    public void agregarOrdenado(Vuelo nuevoVuelo) {
        Nodo<Vuelo> nuevoNodo = new Nodo<Vuelo>(nuevoVuelo);

        if (this.esVacia() || nuevoVuelo.compareTo(this.inicio.getDato()) < 0) {
            this.agregarInicio(nuevoVuelo);
            return;
        }

        if (nuevoVuelo.compareTo(this.fin.getDato()) > 0) {
            this.agregarFinal(nuevoVuelo);
            return;
        }

        Nodo<Vuelo> actual = this.inicio;

        while (actual.getSiguiente() != null && nuevoVuelo.compareTo(actual.getSiguiente().getDato()) > 0) {
            actual = actual.getSiguiente();
        }

        nuevoNodo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevoNodo);

        if (nuevoNodo.getSiguiente() == null) {
            fin = nuevoNodo;
        }

        this.cantidad++;
    }
    public double getminutos(){
        Nodo<Vuelo> aux = inicio;
        if(aux!=null){
        return aux.getDato().getMinutos();
        }
        return Double.MAX_VALUE;
    }
    public boolean existeAerolinea(String codigo) {

        Nodo<Vuelo> aux = inicio;
        while (aux != null) {
            if (aux.getDato().getCodigoAerolinea().equals(codigo)) {
                return true;

            }
            aux = aux.getSiguiente();

        }
        return false;
  }


}
