//Rodrigo delgado 299328
//Bruno iglesias 305558
package Estructuras;


public class ABB<T extends Comparable<T>> {

    private Nodo<T> raiz;
    private int cantidad;
    public ABB() {
    }

    public void agregar(T dato) {
        if (raiz == null) {
            raiz = new Nodo<>(dato);
        } else {
            agregarRec(raiz, dato);
        }
        cantidad++;
    }

    private void agregarRec(Nodo<T> nodo, T dato) {
        if (dato.compareTo(nodo.getDato()) > 0) { //der
            if (nodo.getDer() == null) {
                nodo.setDer(new Nodo<>(dato));
            } else {
                agregarRec(nodo.getDer(), dato);
            }
        } else { //izq
            if (nodo.getIzq() == null) {
                nodo.setIzq(new Nodo<>(dato));
            } else {
                agregarRec(nodo.getIzq(), dato);
            }
        }
    }

    public int largo() {
        return cantidad;
    }

    public boolean existe(T dato) {
        return existeRec(raiz, dato);
    }

    private boolean existeRec(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return false;
        } else {
            int comparacion = dato.compareTo(nodo.getDato());
            if (comparacion == 0) {
                return true;
            } else if (comparacion > 0) {
                return existeRec(nodo.getDer(), dato);
            } else {
                return existeRec(nodo.getIzq(), dato);
            }
        }
    }
    public T obtener(T dato) {
        return obtener(raiz, dato);
    }

    private T obtener(Nodo<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        } else if (dato.compareTo(nodo.getDato())==0) {
            return nodo.getDato();
        } else {
            if (dato.compareTo(nodo.getDato()) > 0) {
                return obtener(nodo.getDer(), dato);
            } else {
                return obtener(nodo.getIzq(), dato);
            }
        }
    }
    public int contador(T dato) {
        return contadorRec(raiz, dato, 0);
    }

    private int contadorRec(Nodo<T> nodo, T dato, int contador) {
        if (nodo == null) {
            return -1;
        } else if (dato.compareTo(nodo.getDato()) == 0) {
            return contador;
        } else {
            contador++;
            if (dato.compareTo(nodo.getDato()) > 0) {
                return contadorRec(nodo.getDer(), dato, contador);
            } else {
                return contadorRec(nodo.getIzq(), dato, contador);
            }
        }
    }
    public String listarAsc() {
      String retorno=listarAsc(raiz);
        if (retorno == null || retorno.isEmpty()) {
            return retorno;
        }

        return retorno.substring(0, retorno.length() - 1);


    }

    private String listarAsc(Nodo<T> nodo) {

        if (nodo == null) {
            return "";
        }
        String retorno = "";
        retorno += listarAsc(nodo.getIzq());
        retorno += nodo.getDato().toString();
        retorno += listarAsc(nodo.getDer());
        return retorno;

    }
    public String listarDesc() {
        String retorno=listarDesc(raiz);
        if (retorno == null || retorno.isEmpty()) {
            return retorno;
        }

        return retorno.substring(0, retorno.length() - 1);


    }

    private String listarDesc(Nodo<T> nodo) {

        if (nodo == null) {
            return "";
        }
        String retorno = "";
        retorno += listarDesc(nodo.getDer());
        retorno += nodo.getDato().toString();
        retorno += listarDesc(nodo.getIzq());
        return retorno;

    }




    public void mostrar() {
        mostrar(raiz);
    }

    private void mostrar(Nodo<T> nodo) {
        if (nodo != null) {
            mostrar(nodo.getIzq());
            System.out.println(nodo.getDato());
            mostrar(nodo.getDer());
        }
    }

    private static class Nodo<T> {
        private T dato;
        private Nodo<T> izq;
        private Nodo<T> der;

        public Nodo(T dato) {
            this.dato = dato;
        }

        public Nodo(T dato, Nodo<T> izq, Nodo<T> der) {
            this.dato = dato;
            this.izq = izq;
            this.der = der;
        }

        public T getDato() {
            return dato;
        }

        public void setDato(T dato) {
            this.dato = dato;
        }

        public Nodo<T> getIzq() {
            return izq;
        }

        public void setIzq(Nodo<T> izq) {
            this.izq = izq;
        }

        public Nodo<T> getDer() {
            return der;
        }

        public void setDer(Nodo<T> der) {
            this.der = der;
        }
    }
}