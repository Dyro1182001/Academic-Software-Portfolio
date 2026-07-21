//Rodrigo delgado 299328
//Bruno iglesias 305558
package Estructuras;

import dominio.Aeropuerto;

public class ListaAeropuerto  extends Lista<Aeropuerto> {

    public int existe(String codigo) {
        int pos = 0;
        Nodo<Aeropuerto> aux = inicio;
        while (aux != null) {
            if (aux.getDato().getCodigo().equals(codigo)) {
                return pos;
            }
            aux = aux.getSiguiente();
            pos++;
        }
        return -1;
    }
    public void agregarOrdenado(Aeropuerto nuevoAeropuerto) {
        Nodo<Aeropuerto> nuevoNodo = new Nodo<Aeropuerto>(nuevoAeropuerto);

        if (this.esVacia() || nuevoAeropuerto.compareTo(this.inicio.getDato()) < 0) {
            this.agregarInicio(nuevoAeropuerto);
            return;
        }

        if (nuevoAeropuerto.compareTo(this.fin.getDato()) > 0) {
            this.agregarFinal(nuevoAeropuerto);
            return;
        }
        Nodo<Aeropuerto> actual = this.inicio;

        while (actual.getSiguiente() != null && nuevoAeropuerto.compareTo(actual.getSiguiente().getDato()) > 0) {
            actual = actual.getSiguiente();
        }

        nuevoNodo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevoNodo);

        this.cantidad++;
    }
    public String guardarLista() {
        String retorno="";
        Nodo<Aeropuerto> actual = inicio;

        while (actual != null) {
            retorno+=actual.getDato().toString();
            actual = actual.getSiguiente();
        }

        return retorno;
    }




}
