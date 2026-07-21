//Rodrigo delgado 299328
//Bruno iglesias 305558

package Estructuras;


import dominio.Aeropuerto;
import dominio.Conexion;
import dominio.Vuelo;

public class Grafo {

    //Definir atributos
    private int cantidad;
    private int tope;
    private ListaAeropuerto vertices;
    private Lista<Conexion>[][] matriz;


    public Grafo(int cantMaxDeVertices, boolean esDirigido) {
        cantidad = 0;
        tope = cantMaxDeVertices;
        vertices = new ListaAeropuerto();
        matriz = new Lista[tope][tope];
        if (esDirigido) {
            for (int i = 0; i < tope; i++) {
                for (int j = 0; j < tope; j++) {
                    matriz[i][j] = new Lista<>();
                }
            }
        } else {
            for (int i = 0; i < tope; i++) {
                for (int j = i; j < tope; j++) {
                    Lista objAux = new Lista<>();
                    matriz[i][j] = objAux;
                    matriz[j][i] = objAux;
                }
            }
        }
    }

    public void agregarArista(String origen, String destino,double kilometros) {
        Conexion conexion=new Conexion(origen,destino,kilometros);
        int posOrigen = getVertices().existe(origen);
        int posDestino = getVertices().existe(destino);
        matriz[posOrigen][posDestino].agregarInicio(conexion);
        matriz[posOrigen][posDestino].setExiste(true);

    }
    public boolean existerConexion(String origen, String destino) {
        int posOrigen = vertices.existe(origen);
        int posDestino = vertices.existe(destino);
        return matriz[posOrigen][posDestino].isExiste();
    }

    public boolean ExisteAeropuerto(String codigo) {
        for (int i = 0; i < vertices.largo(); i++) {
            Aeropuerto aeropuerto = vertices.obtenerDato(i);
            if (aeropuerto != null && aeropuerto.getCodigo() != null && aeropuerto.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }
    public Conexion existeConexion(String origen, String destino) {
        int posOrigen = getVertices().existe(origen);
        int posDestino = getVertices().existe(destino);
        if (posOrigen!=-1 && posDestino!=-1) {
            Lista<Conexion> conexiones = matriz[posOrigen][posDestino];


            if (conexiones.existe==true) {
                Conexion c= conexiones.getInicio().getDato();
                return c;
            }
        }
        return null;
    }
    public boolean existeVuelo(String origen, String destino,String codigoVuelo) {
        int posOrigen = getVertices().existe(origen);
        int posDestino = getVertices().existe(destino);
        if (posOrigen!=-1 && posDestino!=-1) {
            Lista<Conexion> conexiones = matriz[posOrigen][posDestino];
            if (conexiones.existe==true) {
                Conexion c= conexiones.getInicio().getDato();
                int pos=c.vuelos.existe(codigoVuelo);

                if (pos!=-1){
                    return true;
                }

            }
        }
        return false;
    }
    public void agregarVuelo(Vuelo v, Conexion c){
        c.vuelos.agregarOrdenado(v);
    }

    private int verticeMenorValor(boolean[] visitados, double[] Valores) {
        int retorno = -1;
        double min = Double.MAX_VALUE;
        for (int i = 0; i < tope; i++) {
            if (!visitados[i] && Valores[i] < min) {
                min = Valores[i];
                retorno = i;
            }
        }
        return retorno;
    }


    public ListaAeropuerto bfs2(String vert, int escalamax, String codigoAerolinea) {
        Cola<Tupla> cola = new Cola<>();
        boolean[] visitados = new boolean[tope];
        int inicio = vertices.existe(vert);
        cola.encolar(new Tupla(inicio, 0));
        visitados[inicio] = true;
        ListaAeropuerto aeropuertosEnEscala = new ListaAeropuerto();

        while (!cola.esVacia()) {
            Tupla aux = cola.desencolar();
            int pos = aux.getPos();
            int nivel = aux.getNivel();

            if (nivel <= escalamax) {
                aeropuertosEnEscala.agregarOrdenado(vertices.obtenerDato(pos));
                for (int j = 0; j < tope; j++) {
                    if (matriz[pos][j].isExiste() && !visitados[j]) {
                        Lista<Conexion> conexiones = matriz[pos][j];
                        Conexion c = conexiones.getInicio().getDato();
                        if (c.vuelos.existeAerolinea(codigoAerolinea)) {
                            cola.encolar(new Tupla(j, nivel + 1));
                            visitados[j] = true;
                        }
                    }
                }
            }
        }

        return aeropuertosEnEscala;
    }

    public RetornoValor viajeCostoMinimoKilometros(String codigoOrigen, String codigoDestino,boolean Eskilometros) {
        int origen = vertices.existe(codigoOrigen);
        int destino = vertices.existe(codigoDestino);
        boolean[] visitados = new boolean[tope];
        double[] kilometros = new double[tope];
        int[] posiciones = new int[tope];
        double kilometrosnuevos=0;
        RetornoValor ret= new RetornoValor(0,"");
        String string=vertices.obtenerDato(origen).toString() +vertices.obtenerDato(destino).toString();
        if (matriz[origen][destino].isExiste()){
            if (Eskilometros){
                kilometrosnuevos=matriz[origen][destino].getInicio().getDato().getKilometros();
                ret=new RetornoValor(kilometrosnuevos,string);
                return ret;
            }else {
                kilometrosnuevos=matriz[origen][destino].getInicio().getDato().vuelos.getminutos();
                ret=new RetornoValor(kilometrosnuevos,string);
                return ret;
            }

        }



        for (int i = 0; i < tope; i++) {
            kilometros[i] = Double.MAX_VALUE;
        }
        kilometros[origen] = 0;

        for (int v = 0; v < tope; v++) {
            int pos = verticeMenorValor(visitados, kilometros);
            if (pos != -1) {
                visitados[pos] = true;

                for (int j = 0; j < tope; j++) {
                    if (matriz[pos][j].isExiste() && !visitados[j]) {
                        Lista<Conexion> conexiones = matriz[pos][j];
                        if (Eskilometros) {
                             kilometrosnuevos = conexiones.getInicio().getDato().getKilometros();
                        }else {
                             kilometrosnuevos = conexiones.getInicio().getDato().vuelos.getminutos();

                        }
                        if (kilometros[pos] + kilometrosnuevos < kilometros[j]) {
                            kilometros[j] = kilometros[pos] + kilometrosnuevos;
                            posiciones[j] = pos;
                        }
                    }
                }
            }
        }

        if (kilometros[destino] == Double.MAX_VALUE) {
            return null;
        }
        String retorno = "";
        int nodoActual = destino;
        while (nodoActual != -1 && nodoActual != origen) {

            retorno = vertices.obtenerDato(nodoActual).toString() + retorno;
            nodoActual = posiciones[nodoActual];
        }
        retorno = vertices.obtenerDato(origen).toString() + retorno;

        RetornoValor ret2 = new RetornoValor(kilometros[destino], retorno);

        return ret2;
    }






    public boolean esLleno() {
        return cantidad == tope;
    }

    public boolean esVacio() {
        return cantidad == 0;
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getTope() {
        return tope;
    }

    public void setTope(int tope) {
        this.tope = tope;
    }

    public ListaAeropuerto getVertices() {
        return vertices;
    }

    public void setVertices(ListaAeropuerto vertices) {
        this.vertices = vertices;
    }
    public class RetornoValor{
        private double ValorDouble;
        private String valorString;

        public RetornoValor(double ValorDouble, String valorString) {
            this.ValorDouble = ValorDouble;
            this.valorString = valorString;
        }

        public double getValorDouble() {
            return ValorDouble;
        }

        public void setValorDouble(double ValorDouble) {
            this.ValorDouble = ValorDouble;
        }

        public String getvalorString() {
            return valorString;
        }

        public void setvalorString(String valorString) {
            this.valorString = valorString;
        }

    }

}
