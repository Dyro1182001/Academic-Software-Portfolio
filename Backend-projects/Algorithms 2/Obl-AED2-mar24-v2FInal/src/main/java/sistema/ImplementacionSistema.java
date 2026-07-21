//Rodrigo delgado 299328
//Bruno iglesias 305558
package sistema;

import Estructuras.ABB;
import Estructuras.Grafo;
import Estructuras.ListaAeropuerto;
import dominio.*;
import interfaz.*;

public class ImplementacionSistema implements Sistema {
    private int maximoAerolineas;
    private int maximoAeropuertos;
    private ABB<Pasajero> ABBPasajero;
    private ABB<Pasajero> ABBPlatino;
    private ABB<Pasajero> ABBFrecuente;
    private ABB<Pasajero> ABBEstandar;
    private ABB<Aerolinea> ABBAerolinea;
    private Grafo grafo;

    @Override
    public Retorno inicializarSistema(int maxAeropuertos, int maxAerolineas) {

        if (maxAeropuertos<=5){
            return Retorno.error1("El maximo aeropueros tiene que ser mayor a 5");

        }
        if (maxAerolineas<=3){
            return Retorno.error2("El maximo aerolineas tiene que ser mayor a 3");

        }

        this.ABBPasajero=new ABB<Pasajero>();
        this.ABBEstandar=new ABB<Pasajero>();
        this.ABBFrecuente=new ABB<Pasajero>();
        this.ABBPlatino=new ABB<Pasajero>();
        this.ABBAerolinea=new ABB<Aerolinea>();
        this.grafo=new Grafo(maxAeropuertos,true);
        maximoAeropuertos=maxAeropuertos;
        maximoAerolineas=maxAerolineas;

        return Retorno.ok();
    }

    @Override
    public Retorno registrarPasajero(String cedula, String nombre, String telefono, Categoria categoria) {
        Pasajero pasajero=new Pasajero(cedula,nombre,telefono,categoria);

        if (cedula==null||cedula.isEmpty() || nombre==null || nombre.isEmpty() || telefono==null || telefono.isEmpty()|| categoria==null){

            return Retorno.error1("Los parametros no pueden ser nulos");

        }

        if (!pasajero.ValidarCedula(cedula)) {
            return Retorno.error2("Formato de cedula incorrecta");
        }
        if (this.ABBPasajero.existe(pasajero)) {
            return Retorno.error3("El pasajero ya existe");
        }
        ABBPasajero.agregar(pasajero);
        if(pasajero.getCategoria().getTexto().equals("Platino")){
            ABBPlatino.agregar(pasajero);
        }
        else if(pasajero.getCategoria().getTexto().equals("Frecuente")){
            ABBFrecuente.agregar(pasajero);
        } else{
            ABBEstandar.agregar(pasajero);
        }
        return Retorno.ok();
    }

    @Override
    public Retorno buscarPasajero(String cedula) {

        if (cedula==null||cedula.isEmpty()){

            return Retorno.error1("La cedula no puede ser nula");

        }
        Pasajero p=new Pasajero(cedula,null,null,null);
        Pasajero encontrado=this.ABBPasajero.obtener(p);

        if (!p.ValidarCedula(cedula)) {
            return Retorno.error2("Formato de cedula incorrecta");
        }

        if (encontrado==null){

            return Retorno.error3("No existe un pasajero registrado con esa cédula");

        }
        String valorString=encontrado.cedula+";"+encontrado.nombre+";"+encontrado.telefono+";"+encontrado.categoria.getTexto();
        int ValorEntero=this.ABBPasajero.contador(p);
        return Retorno.ok(ValorEntero,valorString);
    }

    @Override
    public Retorno listarPasajerosAscendente() {
       String valorString= this.ABBPasajero.listarAsc();
        return Retorno.ok(valorString);
    }

    @Override
    public Retorno listarPasajerosPorCategoria(Categoria categoria) {
        String retorno="";
        if (categoria!=null) {
            if (categoria.getTexto().equals("Platino")) {
                retorno = ABBPlatino.listarAsc();
            } else if (categoria.getTexto().equals("Frecuente")) {
                retorno = ABBFrecuente.listarAsc();
            } else {
                retorno = ABBEstandar.listarAsc();
            }
            return Retorno.ok(retorno);
        }
        return Retorno.error1("");
    }

    @Override
    public Retorno registrarAerolinea(String codigo, String nombre) {
        Aerolinea aerolinea=new Aerolinea(nombre,codigo);
        if (this.ABBAerolinea.largo()==maximoAerolineas){

            return Retorno.error1("en el sistema ya hay registrados el maximo de aerolineas");

        }
        if (nombre==null || nombre.isEmpty() || codigo==null || codigo.isEmpty()){

            return Retorno.error2("Los parametros no pueden ser nulos");

        }
        if (this.ABBAerolinea.existe(aerolinea)) {
            return Retorno.error3("La aerolinea ya existe");
        }
        this.ABBAerolinea.agregar(aerolinea);
        return Retorno.ok();
    }

    @Override
    public Retorno listarAerolineasDescendente() {

        String valorString= this.ABBAerolinea.listarDesc();
        return Retorno.ok(valorString);

    }

    @Override
    public Retorno registrarAeropuerto(String codigo, String nombre) {

        Aeropuerto aeropuerto=new Aeropuerto(nombre,codigo);
       if (this.grafo.getVertices().largo()==maximoAeropuertos){

            return Retorno.error1("en el sistema ya hay registrados el maximo de aeropuertos");

        }
        if (nombre==null || nombre.isEmpty() || codigo==null || codigo.isEmpty()){

            return Retorno.error2("Los parametros no pueden ser nulos");

        }
       if (this.grafo.getVertices().existe(codigo)!=-1) {
          return Retorno.error3("El aeropuerto ya existe");
       }
        this.grafo.getVertices().agregarFinal(aeropuerto);
        return Retorno.ok();

    }

    @Override
    public Retorno registrarConexion(String codigoAeropuertoOrigen, String codigoAeropuertoDestino, double kilometros) {

        if (kilometros<=0){
            return Retorno.error1("Kilometros tiene que ser mayor a 0");
        }
        if (codigoAeropuertoOrigen==null || codigoAeropuertoOrigen.isEmpty() || codigoAeropuertoDestino==null || codigoAeropuertoDestino.isEmpty()){
            return Retorno.error2("Los parametros no pueden ser nulos");
        }
        if (this.grafo.getVertices().existe(codigoAeropuertoOrigen)==-1) {
            return Retorno.error3("El aeropuerto de origen no existe");
        }
        if (this.grafo.getVertices().existe(codigoAeropuertoDestino)==-1) {
            return Retorno.error4("El aeropuerto de destino no existe");
        }
      if (this.grafo.existerConexion(codigoAeropuertoOrigen,codigoAeropuertoDestino)) {
         return Retorno.error5("La conexion ya existe");
       }
      this.grafo.agregarArista(codigoAeropuertoOrigen,codigoAeropuertoDestino,kilometros);
     return Retorno.ok();
    }

    @Override
    public Retorno registrarVuelo(String codigoCiudadOrigen, String codigoAeropuertoDestino, String codigoDeVuelo, double combustible, double minutos, double costoEnDolares, String codigoAerolinea) {
        if (combustible<=0 ||minutos<=0||costoEnDolares<=0){
            return Retorno.error1("Error los parametros numericos deben ser mayores a 0");
        }

        if (codigoCiudadOrigen==null || codigoCiudadOrigen.isEmpty() || codigoAeropuertoDestino==null || codigoAeropuertoDestino.isEmpty() || codigoAerolinea==null || codigoAerolinea.isEmpty() || codigoDeVuelo==null|| codigoDeVuelo.isEmpty()){
            return Retorno.error2("Los parametros no pueden ser nulos");
        }
        if (!grafo.ExisteAeropuerto(codigoCiudadOrigen)){

            return Retorno.error3("El aeropuerto de origen no existe");

        }
        if (!grafo.ExisteAeropuerto(codigoAeropuertoDestino)){

            return Retorno.error4("El aeropuerto de destino no existe");

        }
        Aerolinea a=new Aerolinea(null,codigoAerolinea);
        if(!ABBAerolinea.existe(a)){
            return Retorno.error5("La aerolinea no existe");

        }
        if(grafo.existeConexion(codigoCiudadOrigen,codigoAeropuertoDestino)==null){
            return Retorno.error6("La conexion no existe");

        }
        if(grafo.existeVuelo(codigoCiudadOrigen,codigoAeropuertoDestino,codigoDeVuelo)){
            return Retorno.error7("El vuelo ya existe");

        }
        Conexion c=grafo.existeConexion(codigoCiudadOrigen,codigoAeropuertoDestino);
        Vuelo v=new Vuelo(codigoCiudadOrigen,codigoAeropuertoDestino,codigoDeVuelo,combustible,minutos,costoEnDolares,codigoAerolinea);
        grafo.agregarVuelo(v,c);
        return Retorno.ok();
    }

    @Override
    public Retorno listadoAeropuertosCantDeEscalas(String codigoAeropuertoOrigen, int cantidad, String codigoAerolinea) {
        if (cantidad<0){
            return Retorno.error1("Error los parametros numericos deben ser mayores a 0");
        }
        if (!grafo.ExisteAeropuerto(codigoAeropuertoOrigen)){

            return Retorno.error2("El aeropuerto de origen no existe");

        }
        Aerolinea a=new Aerolinea(null,codigoAerolinea);
        if(!ABBAerolinea.existe(a)){
            return Retorno.error3("La aerolinea no existe");

        }
        ListaAeropuerto lista= grafo.bfs2(codigoAeropuertoOrigen,cantidad,codigoAerolinea);
        String valorString=lista.guardarLista();
        return Retorno.ok(valorString.substring(0, valorString.length() - 1));

    }

    @Override
    public Retorno viajeCostoMinimoKilometros(String codigoCiudadOrigen, String codigoCiudadDestino) {
        if (codigoCiudadOrigen==null || codigoCiudadOrigen.isEmpty() || codigoCiudadDestino==null || codigoCiudadDestino.isEmpty()){
            return Retorno.error1("Los parametros no pueden ser nulos");
        }
        if (this.grafo.getVertices().existe(codigoCiudadOrigen)==-1) {
            return Retorno.error3("El aeropuerto de origen no existe");
        }
        if (this.grafo.getVertices().existe(codigoCiudadDestino)==-1) {
            return Retorno.error4("El aeropuerto de destino no existe");
        }

        Grafo.RetornoValor r= grafo.viajeCostoMinimoKilometros(codigoCiudadOrigen,codigoCiudadDestino,true);
        if (r==null){
            return Retorno.error2("El aeropuerto de destino no existe");
        }
        int valorEntero=(int)r.getValorDouble();
        return Retorno.ok(valorEntero,r.getvalorString().substring(0, r.getvalorString().length() - 1));

    }

    @Override
    public Retorno viajeCostoMinimoEnMinutos(String codigoAeropuertoOrigen, String codigoAeropuertoDestino) {
        if (codigoAeropuertoOrigen==null || codigoAeropuertoOrigen.isEmpty() || codigoAeropuertoDestino==null || codigoAeropuertoDestino.isEmpty()){
            return Retorno.error1("Los parametros no pueden ser nulos");
        }
        if (this.grafo.getVertices().existe(codigoAeropuertoOrigen)==-1) {
            return Retorno.error3("El aeropuerto de origen no existe");
        }
        if (this.grafo.getVertices().existe(codigoAeropuertoDestino)==-1) {
            return Retorno.error4("El aeropuerto de destino no existe");
        }

        Grafo.RetornoValor r= grafo.viajeCostoMinimoKilometros(codigoAeropuertoOrigen,codigoAeropuertoDestino,false);
        if (r==null){
            return Retorno.error2("El aeropuerto de destino no existe");
        }
        int valorEntero=(int)r.getValorDouble();
        return Retorno.ok(valorEntero,r.getvalorString().substring(0, r.getvalorString().length() - 1));
    }


}
