/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sistemas;

import clasesDeEjemplo.Anomalia;
import java.util.ArrayList;

/**
 *
 * @author Drakmalar
 */
public class SistemaAnomalia {
    
    private ArrayList<Anomalia> anomalias = new ArrayList<Anomalia>();
    
    public ArrayList<Anomalia> ObtenerAnomalias(){
        return anomalias;
    }
    
    public void cargarListaAnomalia(Anomalia anomalia){
        anomalias.add(anomalia);
    }
    
}
