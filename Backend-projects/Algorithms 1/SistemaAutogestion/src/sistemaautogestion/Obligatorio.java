
package sistemaautogestion;

import java.util.Date;



/**
 *
 * @author alumnoFI
 */
public class Obligatorio implements IObligatorio {
    Listamedico lm;
    Listapaciente lp;
    int tope;
    
    

    @Override
    public Retorno crearSistemaDeAutogestion(int maxPacientesporMedico) {
        lm = new Listamedico();
        lp = new Listapaciente();
        
      if(maxPacientesporMedico <= 0 || maxPacientesporMedico > 15){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
            return ret;
        }else{
            Retorno ret = new Retorno(Retorno.Resultado.OK);
            ret.valorEntero=maxPacientesporMedico;
            tope = maxPacientesporMedico;
            return ret;
        }
    }

    @Override
    public Retorno registrarMedico(String nombre, int codMedico, int tel, int especialidad) {
       
     if(lm.obtenermedico(codMedico) != null){
         Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
          return ret;
     }else if (especialidad < 1 || especialidad > 20){
         Retorno ret = new Retorno(Retorno.Resultado.ERROR_2);
         return ret;
     }else{
         lm.agregar(nombre, codMedico, tel, especialidad);
         Retorno ret = new Retorno(Retorno.Resultado.OK);
         return ret;
          } 
    }

    @Override
    public Retorno eliminarMedico(int codMedico) {
        
        nodomedico medico = lm.obtenermedico(codMedico);
        
     if(medico == null){
         Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
         return ret;
     }else if(medico.getLc().primero != null){
         Retorno ret = new Retorno(Retorno.Resultado.ERROR_2);
         return ret;
     }else{
         lm.eliminar(codMedico);
         Retorno ret = new Retorno(Retorno.Resultado.OK);
         return ret;
        }
     }

    @Override
    public Retorno agregarPaciente(String nombrep, int nrop, String direccion) {
        if(lp.obtenerpaciente(nrop) != null){
          Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
          return ret;
        }else{
            lp.agregar(nombrep, nrop, direccion);
            Retorno ret = new Retorno(Retorno.Resultado.OK);
            return ret;
        } 
     }

    @Override
    public Retorno eliminarPaciente(int CI) {
        
        nodopaciente paciente = lp.obtenerpaciente(CI);
     if(paciente == null){
         Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
         return ret;
     }else if (paciente.lh.getPrimero() != null || ObtenerConsultasXCI(CI) ){
         Retorno ret = new Retorno(Retorno.Resultado.ERROR_2);
         return ret;
     }else{
         lp.eliminar(CI);
         Retorno ret = new Retorno(Retorno.Resultado.OK);
         return ret;
        }   
     }
    
    public boolean ObtenerConsultasXCI(int ci){
        
        nodomedico medico = lm.getPrimero();
        while(medico != null){
            if(medico.lc.obtenerconsultas(ci) != null ){
                return true;
            }
            medico = medico.getSiguiente();
        }
        return false;
        
    }
    
    @Override
    public Retorno registrarDiaDeConsulta(int codMedico, Date fecha) {
    nodomedico medico = lm.obtenermedico(codMedico);

        if (medico == null) {
        Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
        return ret;
        } else if (medico.lc.ExisteConsultaEnLaFecha(fecha)) {
        Retorno ret = new Retorno(Retorno.Resultado.ERROR_2);
        return ret;
        } else {
        medico.lc.agregar(fecha);
        Retorno ret = new Retorno(Retorno.Resultado.OK);
        return ret;
        }
    }

    @Override
    public Retorno reservaConsulta(int codMedico, int ciPaciente, Date fecha) {
        
        nodomedico medico = lm.obtenermedico(codMedico);
        nodopaciente paciente = lp.obtenerpaciente(ciPaciente);

        if(lp.obtenerpaciente(ciPaciente) == null){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
            return ret;
        }else if(medico == null){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_2);
            return ret;
        }else if(medico.lc.HayConsulta(ciPaciente, fecha)){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_3);
            return ret;
        }else if(!medico.lc.ExisteConsultaEnLaFecha(fecha)){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_4);
            return ret;
        }else{
            nodoconsultas consulta = medico.lc.obtenerConsultasPorFecha(fecha);
            consulta.lpa.agregar(ciPaciente, consulta, tope);
            consulta.lpa.obtenerpaciente(ciPaciente).setEstadoConsulta("pendiente");
            paciente.lcp.agregar(fecha);
            Retorno ret = new Retorno(Retorno.Resultado.OK);
            return ret; 
        }
    }

    @Override
    public Retorno cancelarReserva(int codMedico, int ciPaciente) {
        
        nodomedico medico = lm.obtenermedico(codMedico);
        nodopaciente paciente = lp.obtenerpaciente(ciPaciente);
        
        if(paciente == null){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
            return ret;  
        }else if (medico == null){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_2);
            return ret;
        }else if(medico.lc.obtenerconsultas(ciPaciente) == null || medico.lc.obtenerconsultas(ciPaciente).lpa.obtenerpaciente(ciPaciente).getEstadoConsulta() == "No asistió"){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_3);
            return ret;
        }else if(medico.lc.obtenerconsultas(ciPaciente).lpa.obtenerpaciente(ciPaciente).getEstadoConsulta() != "pendiente"){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_4);
            return ret; 
        }else{
            medico.lc.EliminarConsulta(ciPaciente, tope);
            Retorno ret = new Retorno(Retorno.Resultado.OK);
        
            return ret; 
        }  
    }

    @Override
    public Retorno anunciaLlegada(int codMedico, int CIPaciente) {
        nodopaciente paciente = lp.obtenerpaciente(CIPaciente);
        nodomedico medico = lm.obtenermedico(codMedico);
        nodoconsultas consulta = medico.lc.obtenerconsultas(CIPaciente);
        
        
        if(paciente == null){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
            return ret;   
        }else if(consulta == null){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_2);
            return ret; 
        }else{
            Date fechaconsulta = consulta.getFecha();
            paciente.lcp.EliminarConsulta(fechaconsulta);
            consulta.lpa.obtenerpaciente(CIPaciente).setEstadoConsulta("en espera");
            Retorno ret = new Retorno(Retorno.Resultado.OK);
            ret.valorString = "";
            ret.valorString = medico.lc.MostrarConsulta(medico.getNomM(), consulta.lpa.obtenerpaciente(CIPaciente).getNroReserva());
            return ret; 
        } 
     }

    @Override
    public Retorno terminarConsultaMedicoPaciente(int CIPaciente, int codMedico, String detalleDeConsulta) {
        
        nodopaciente paciente = lp.obtenerpaciente(CIPaciente);
        nodomedico medico = lm.obtenermedico(codMedico);
        nodoconsultas consulta = medico.lc.obtenerconsultas(CIPaciente);
        
        if(paciente == null){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
            return ret;   
        }else if(medico.lc.obtenerconsultas(CIPaciente).lpa.obtenerpaciente(CIPaciente).getEstadoConsulta() != "en espera"){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_2);
            return ret; 
        }else{
            consulta.lpa.obtenerpaciente(CIPaciente).setEstadoConsulta("terminada");
            Date fechaconsulta = consulta.getFecha();
            paciente.lh.agregar(detalleDeConsulta, fechaconsulta);
            Retorno ret = new Retorno(Retorno.Resultado.OK);
            ret.valorString = "";
            ret.valorString = medico.lc.MostrarConsulta(medico.getNomM(), consulta.lpa.obtenerpaciente(CIPaciente).getNroReserva());
            return ret; 
        } 
     }

    @Override
    public Retorno cerrarConsulta(int codMedico, Date fechaConsulta) {
        
        nodomedico medico = lm.obtenermedico(codMedico);
        
        if(medico == null){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
            return ret;   
        }
        
        nodoconsultas consulta = medico.lc.obtenerConsultasPorFecha(fechaConsulta);
        
        
        if(consulta == null){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_2);
            return ret; 
        }else{
            ListapacientesAgendados lpa = consulta.getLpa();
            nodopacienteagendado paciente = lpa.getPrimero();
            while(paciente != null){
                if(paciente.getEstadoConsulta() == "pendiente"){
                    paciente.setEstadoConsulta("No asistió");
                    agregarAHistoriaClinica(paciente.getCi(), fechaConsulta);
                    eliminarDeConsultasPendientes(paciente.getCi(), fechaConsulta);
                }
                paciente = paciente.getSiguiente();
            }
            Retorno ret = new Retorno(Retorno.Resultado.OK);
            return ret; 
        }    
     }
    
    public void agregarAHistoriaClinica(int ciPaciente, Date fecha){
        String milanga = "";
        nodopaciente paciente = lp.obtenerpaciente(ciPaciente);
        
        paciente.lh.agregar("No asistió", fecha);
    }
    
    public void eliminarDeConsultasPendientes(int ciPaciente, Date fecha){
        nodopaciente paciente = lp.obtenerpaciente(ciPaciente);
        
        paciente.lcp.EliminarConsulta(fecha);
    }
    //TERMINAN LOS REGISTROS
    
    
    
    
    
    //EMPIEZAN LOS LISTADOS
    @Override
    public Retorno listarMédicos() {
        
        Retorno ret = new Retorno(Retorno.Resultado.OK);
        ret.valorString = "";
        ret.valorString = lm.listar();
        return ret;
     }

    @Override
    public Retorno listarPacientes() {
        Retorno ret = new Retorno(Retorno.Resultado.OK);
        ret.valorString = "";
        ret.valorString = lp.listar();
        return ret;   
     }

    @Override
    public Retorno listarConsultas(int codMedico) {
     nodomedico medico = lm.obtenermedico(codMedico);
     if(medico == null){
         Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
         return ret;
     }else{
         Retorno ret = new Retorno(Retorno.Resultado.OK);
        ret.valorString = "";
        ret.valorString = auxiliarListadoRecursivo(medico.lc.getPrimero());
        return ret;   
     }
    }
    
    private String auxiliarListadoRecursivo(nodoconsultas nodo){
        if(nodo == null){
            return "";
        }else{
           String consultas = "\n" + "Fecha: " + nodo.getFecha() + "\n" ;
            consultas += nodo.lpa.listar();
            String consultassiguientes = auxiliarListadoRecursivo(nodo.getSiguiente());
            return consultas + consultassiguientes;
        }
        
    }

    @Override
    public Retorno listarPacientesEnEspera(int codMedico, Date fecha) {
        
        nodomedico medico = lm.obtenermedico(codMedico);
        nodoconsultas consulta = medico.lc.obtenerConsultasPorFecha(fecha);
       
        if(consulta == null){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
            return ret; 
        }else{
            Retorno ret = new Retorno(Retorno.Resultado.OK);
            ret.valorString = "";
            ret.valorString = medico.lc.MostrarPacientesEspera(fecha);
        return ret;   
        }  
     }

    @Override
    public Retorno consultasPendientesPaciente(int CIPaciente) {
        
        nodopaciente paciente = lp.obtenerpaciente(CIPaciente);
        
        if(paciente == null){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
            return ret; 
        }else{
            Retorno ret = new Retorno(Retorno.Resultado.OK);
            ret.valorString = "";
            ret.valorString = auxiliarListadoRecursivoPacientePendiente(paciente);
            return ret;
        }  
     }
    
    private String auxiliarListadoRecursivoPacientePendiente(nodopaciente nodo){
        if(nodo == null){
            return "";
        }else{
           String consultas = "" ;
            consultas += nodo.lcp.listar();
            String consultassiguientes = auxiliarListadoRecursivoPacientePendiente(nodo.getSiguiente());
            return consultas + consultassiguientes;
        }
        
    }

    @Override
    public Retorno historiaClínicaPaciente(int ci) {
       nodopaciente paciente = lp.obtenerpaciente(ci);
        
        if(paciente == null){
            Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
            return ret; 
        }else{
            Retorno ret = new Retorno(Retorno.Resultado.OK);
            ret.valorString = "";
            ret.valorString = auxiliarListadoRecursivoHistoria(paciente);
            return ret;
        }    
    }
    
    private String auxiliarListadoRecursivoHistoria(nodopaciente nodo){
        if(nodo == null){
            return "";
        }else{
            String consultas = "";
            consultas += nodo.lh.listar();
            String consultassiguientes = auxiliarListadoRecursivoHistoria(nodo.getSiguiente());
            return consultas + consultassiguientes;
        }
    }

    @Override
    public Retorno reporteDePacientesXFechaYEspecialidad(int mes, int año) {
        
      if(mes <= 0 || mes > 12 || año < 2020 || año > 2023){
          Retorno ret = new Retorno(Retorno.Resultado.ERROR_1);
            return ret; 
      }else{
          int[][] matriz = new int[32][21];
          for(int i = 1; i < 31; i++){
              for(int j = 1; j < 20; j++){
                  int CantidadDePacientes = lm.ObtenerCantidadDePacientes(i, j, año);
                  matriz[i][j] = CantidadDePacientes;
              }
          }
          Retorno ret = new Retorno(Retorno.Resultado.OK);
          ret.valorString = "";
          ret.valorString = mostrarmatriz(matriz);
          return ret;
      } 
    }
    
    public String mostrarmatriz(int[][] m) {
        String aux = "";
        int filas = m.length;
        int columnas = m[0].length;
        aux = "\n";
        for (int i = 1; i < filas; i++) {
            for (int j = 1; j < columnas; j++) {
                aux = aux + m[i][j] + "-";

            }
            aux = aux + "\n";
        }

        return aux;
    }
}
