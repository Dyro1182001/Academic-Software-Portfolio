//Rodrigo delgado 299328
//Bruno iglesias 305558
package Estructuras;

public class Lista<T extends Comparable<T>> {




    protected class Nodo<T extends Comparable<T>> {
        // Atributos
        private T dato;
        private Nodo<T> siguiente;
        private Nodo<T> anterior;


        // Constructor
        public Nodo(T elDato) {
            this.setDato(elDato);
            this.setSiguiente(null);
            this.setAnterior(null);
        }

        // set y get
        public T getDato() {
            return dato;
        }

        public void setDato(T dato) {
            this.dato = dato;
        }

        public Nodo<T> getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo<T> siguiente) {
            this.siguiente = siguiente;
        }

        public Nodo<T> getAnterior() {
            return anterior;
        }

        public void setAnterior(Nodo<T> anterior) {
            this.anterior = anterior;
        }

    }
    protected  boolean existe;
    protected Nodo<T> inicio;
    protected Nodo<T> fin;
    protected int cantidad;

    public Nodo<T> getInicio() {
        return inicio;
    }

    public void setInicio(Nodo<T> inicio) {
        this.inicio = inicio;
    }

    public Lista() {
        this.inicio = null;
        fin = null;
        cantidad = 0;
        existe=false;
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public int largo() {
        return cantidad;
    }

    public boolean esVacia() {
        return cantidad == 0;
    }

    public void agregarInicio(T n) {
        Nodo<T> nuevo = new Nodo<T>(n);
        if (esVacia()) {
            inicio = nuevo;
            fin = inicio;
            cantidad++;
        } else {
            nuevo.setSiguiente(getInicio());
            inicio.setAnterior(nuevo);
            inicio = nuevo;
            cantidad++;
        }
    }

    public void agregarFinal(T n) {
        Nodo<T> nuevo = new Nodo<T>(n);
        if (this.esVacia()) {
            agregarInicio(n);
        } else {
            fin.setSiguiente(nuevo);
            nuevo.setAnterior(fin);
            fin = nuevo;
            cantidad++;
        }
    }

    public T obtenerDato(int pos) {
        if (pos > cantidad || pos < 0) {
            return null;
        }
        Nodo<T> aux = inicio;
        for (int i = 0; i < pos; i++) {
            aux = aux.getSiguiente();
        }
        return aux.getDato();
    }

}
