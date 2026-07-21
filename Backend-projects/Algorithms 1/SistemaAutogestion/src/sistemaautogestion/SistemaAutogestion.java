
package sistemaautogestion;

import java.util.Date;


public class SistemaAutogestion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Prueba p = new Prueba();
        Obligatorio o = new Obligatorio();
        juegodeprueba(o,p);
    }
     public static void juegodeprueba(Obligatorio o, Prueba p) {

          // CREAR SISTEMA
        p.ver(o.crearSistemaDeAutogestion(19).resultado, Retorno.Resultado.ERROR_1, "El sistema que pretende crear no cumple con el márgen máximo establecido, revisar.");
        p.ver(o.crearSistemaDeAutogestion(-1).resultado, Retorno.Resultado.ERROR_1, "El sistema que pretende crear no cumple con el márgen mínimo establecido, revisar.");
        p.ver(o.crearSistemaDeAutogestion(5).resultado, Retorno.Resultado.OK, "Sistema creado exitosamente con márgen de 5 pacientes por médico.");
    
  
            // String nombre, int CI, String direccion
            //2.2 - Registro de Pacientes.
        p.ver(o.agregarPaciente("Alvaro", 48392685,  "Costanera 2682" ).resultado, Retorno.Resultado.OK, "Paciente registrado con éxito.");
        p.ver(o.agregarPaciente("Bob", 51322132, "Democracia 4832" ).resultado, Retorno.Resultado.OK, "Paciente registrado con éxito.");
        p.ver(o.agregarPaciente("Xavier", 11526122, "Sarandí 8412" ).resultado, Retorno.Resultado.OK, "Paciente registrado con éxito.");
        p.ver(o.agregarPaciente("Ronaldo", 32516924, "Mirasoles 3128" ).resultado, Retorno.Resultado.OK, "Paciente registrado con éxito.");
        p.ver(o.agregarPaciente("Zelda", 57963975, "Neruda 7754" ).resultado, Retorno.Resultado.OK, "Paciente registrado con éxito.");
        p.ver(o.agregarPaciente("Edward", 28226806, "Paysandu 9460" ).resultado, Retorno.Resultado.OK, "Paciente registrado con éxito.");
        p.ver(o.agregarPaciente("Alphonse", 50041075, "Brisas 6935" ).resultado, Retorno.Resultado.OK, "Paciente registrado con éxito.");
        p.ver(o.agregarPaciente("Lucy", 52723306, "Juncal 7158" ).resultado, Retorno.Resultado.OK, "Paciente registrado con éxito.");
        p.ver(o.agregarPaciente("Rebecca", 55208532, "Paysandu 5950" ).resultado, Retorno.Resultado.OK, "Paciente registrado con éxito.");
        p.ver(o.agregarPaciente("Johnny", 42004416, "Rincon 5098" ).resultado, Retorno.Resultado.OK, "Paciente registrado con éxito.");
        p.ver(o.agregarPaciente("Michael", 42068320, "Rincon 5098" ).resultado, Retorno.Resultado.OK, "Paciente registrado con éxito.");
       
        p.ver(o.listarPacientes().resultado, Retorno.Resultado.OK, "Lista de Pacientes:" + o.listarPacientes().valorString);   
        
        //mostramos los resultados 
        
        
        // Casos de demostración de error en Pacientes:

         p.ver(o.agregarPaciente("Lucy", 52723306, "Juncal 7158" ).resultado, Retorno.Resultado.ERROR_1, "El paciente que está tratando de ingresar ya existe.");
         p.ver(o.agregarPaciente("Zelda", 57963975, "Neruda 7754" ).resultado, Retorno.Resultado.ERROR_1, "El paciente que está tratando de ingresar ya existe.");
        
         p.ver(o.listarPacientes().resultado, Retorno.Resultado.OK, "Listado de Pacientes luego de intentar agregar pacientes existentes:" + o.listarPacientes().valorString);
    

        //2.3 - Eliminar Paciente.

        p.ver(o.eliminarPaciente(51322132).resultado, Retorno.Resultado.OK, "Paciente eliminado con éxito.");
        p.ver(o.eliminarPaciente(32516924).resultado, Retorno.Resultado.OK, "Paciente eliminado con éxito.");
        
        //Caso de demostración de error 1 al eliminar Paciente (el segundo se encuentra más abajo, luego del registro de consultas):
        
        p.ver(o.eliminarPaciente(00002132).resultado, Retorno.Resultado.ERROR_1, "El paciente no existe.");
       

        p.ver(o.listarPacientes().resultado, Retorno.Resultado.OK, "Listado de Pacientes, posterior a la eliminación de 2 de ellos:" + o.listarPacientes().valorString);

        
        //2.4 - Registrar Medico

   
        p.ver(o.registrarMedico("Iglesias", 16, 4212410, 2).resultado,Retorno.Resultado.OK, "Se registró correctamente el Dr Iglesias.");
        p.ver(o.registrarMedico("Alcarraz", 13, 4859757, 2).resultado,Retorno.Resultado.OK, "Se registró correctamente el Dr Alcarraz.");
        p.ver(o.registrarMedico("Delgado", 14, 4952731, 2).resultado,Retorno.Resultado.OK, "Se registró correctamente el Dr Delgado.");
        p.ver(o.registrarMedico("González", 20, 4573362, 3).resultado, Retorno.Resultado.OK, "Se registró correctamente el Dr González.");
        p.ver(o.registrarMedico("Martínez", 21, 4295369, 3).resultado, Retorno.Resultado.OK, "Se registró correctamente el Dr Martínez.");
        p.ver(o.registrarMedico("López", 22, 4710661, 3).resultado, Retorno.Resultado.OK, "Se registró correctamente el Dr López.");
        p.ver(o.registrarMedico("Fernández", 23, 4141952, 3).resultado, Retorno.Resultado.OK, "Se registró correctamente el Dr Fernández.");
        p.ver(o.registrarMedico("Jack", 24, 4947775, 4).resultado, Retorno.Resultado.OK, "Se registró correctamente el Dr Jack.");
        p.ver(o.registrarMedico("Johnson", 25, 4655770, 4).resultado, Retorno.Resultado.OK, "Se registró correctamente el Dr Johnson.");
        p.ver(o.registrarMedico("Mario", 26, 4794598, 4).resultado, Retorno.Resultado.OK, "Se registró correctamente el Dr Mario.");
        p.ver(o.registrarMedico("Luigi", 27, 4577445, 4).resultado, Retorno.Resultado.OK, "Se registró correctamente el Dr Luigi.");
        p.ver(o.registrarMedico("Bowser", 28, 4114414, 5).resultado, Retorno.Resultado.OK, "Se registró correctamente el Dr Bowser.");
        p.ver(o.registrarMedico("Hades", 29, 4124822, 5).resultado, Retorno.Resultado.OK, "Se registró correctamente el Dr Hades.");

        p.ver(o.listarMédicos().resultado, Retorno.Resultado.OK,"Lista de Médicos: " + o.listarMédicos().valorString);
  
 
        
        //Casos de demostración de error en Médico:
      
        p.ver(o.registrarMedico("Bowser", 28, 4114414, 5).resultado, Retorno.Resultado.ERROR_1, "El médico que está tratando de ingresar ya existe.");
        p.ver(o.registrarMedico("Luigi", 27, 4577445, 4).resultado, Retorno.Resultado.ERROR_1, "El médico que está tratando de ingresar ya existe.");
        p.ver(o.registrarMedico("Daisy", 30, 4587115, 22).resultado, Retorno.Resultado.ERROR_2, "El médico que está tratando de ingresar tiene un valor inválido de especialidad.");
        p.ver(o.registrarMedico("Peach", 31, 4031100, -1).resultado, Retorno.Resultado.ERROR_2, "El médico que está tratando de ingresar tiene un valor inválido de especialidad.");
      
        p.ver(o.listarMédicos().resultado, Retorno.Resultado.OK, "Listado de Médicos luego de intentar registrar Médicos ya existentes. " + o.listarMédicos().valorString);

        
        //2.5 - Eliminar Médico a través de su código.
        
        p.ver(o.eliminarMedico(25).resultado, Retorno.Resultado.OK, "Eliminamos al Dr Johnsonn");  
        p.ver(o.eliminarMedico(22).resultado, Retorno.Resultado.OK, "Eliminamos al Dr López");  
        
        //Casos de demostración de error al eliminar Médico:
        
        p.ver(o.eliminarMedico(22).resultado, Retorno.Resultado.ERROR_1, "Error, este Médico ya no se encuentra en el sistema.");  

        
        p.ver(o.listarMédicos().resultado, Retorno.Resultado.OK, "Listado de Médicos posterior a eliminar: "+ o.listarMédicos().valorString);

       //Registrar fechas.
        
        Date Fecha1 = new Date(123, 4, 12);
        Date Fecha2 = new Date(123, 4, 24); 
        Date Fecha3 = new Date(123, 4, 8);
        Date Fecha4 = new Date(123, 4, 21);
        Date Fecha5 = new Date(123, 4, 23);
        
        //Registrar días de consulta con Médico.
        
        p.ver(o.registrarDiaDeConsulta(20, Fecha1).resultado, Retorno.Resultado.OK, "Registro de día de consulta exitoso, Fecha registrada: 2023, 5, 12"); 
        
        p.ver(o.registrarDiaDeConsulta(21, Fecha2).resultado, Retorno.Resultado.OK, "Registro de día de consulta exitoso, Fecha registrada: 2023, 5, 12"); 
        
        p.ver(o.registrarDiaDeConsulta(23, Fecha3).resultado, Retorno.Resultado.OK, "Registro de día de consulta exitoso, Fecha registrada: 2023, 5, 12"); 
        
        p.ver(o.registrarDiaDeConsulta(24, Fecha4).resultado, Retorno.Resultado.OK, "Registro de día de consulta exitoso, Fecha registrada: 2023, 5, 12"); 
        
        p.ver(o.registrarDiaDeConsulta(26, Fecha4).resultado, Retorno.Resultado.OK, "Registro de día de consulta exitoso, Fecha registrada: 2023, 5, 12"); 
        
        p.ver(o.registrarDiaDeConsulta(29, Fecha2).resultado, Retorno.Resultado.OK, "Registro de día de consulta exitoso, Fecha registrada: 2023, 5, 12"); 
        
        //Casos de demostración de error al registrar días de consulta:

        p.ver(o.registrarDiaDeConsulta(99, Fecha2).resultado, Retorno.Resultado.ERROR_1, "No se puede realizar el registro dado que el médico ingresado no existe."); 

        p.ver(o.registrarDiaDeConsulta(23, Fecha3).resultado, Retorno.Resultado.ERROR_2, "No se puede realizar el registro dado que el médico ingresado ya tiene esa fecha registrada."); 
        
        
        //2.6 - Registrar reserva de consulta.
        
        p.ver(o.reservaConsulta(21, 42004416, Fecha2).resultado, Retorno.Resultado.OK, "Registro exitoso de una reserva de consulta en la fecha: 2023, 5, 24");
        
        p.ver(o.reservaConsulta(23, 55208532, Fecha3).resultado, Retorno.Resultado.OK, "Registro exitoso de una reserva de consulta en la fecha: 2022, 5, 8");
        
        p.ver(o.reservaConsulta(23, 48392685, Fecha3).resultado, Retorno.Resultado.OK, "Registro exitoso de una reserva de consulta en la fecha: 2022, 5, 8");
        
        p.ver(o.reservaConsulta(23, 11526122, Fecha3).resultado, Retorno.Resultado.OK, "Registro exitoso de una reserva de consulta en la fecha: 2022, 5, 8");
        
        p.ver(o.reservaConsulta(24, 52723306, Fecha4).resultado, Retorno.Resultado.OK, "Registro exitoso de una reserva de consulta en la fecha: 2020, 5, 21");
        
        p.ver(o.reservaConsulta(24, 57963975, Fecha4).resultado, Retorno.Resultado.OK, "Registro exitoso de una reserva de consulta en la fecha: 2020, 5, 21");
        
        p.ver(o.reservaConsulta(26, 50041075, Fecha4).resultado, Retorno.Resultado.OK, "Registro exitoso de una reserva de consulta en la fecha: 2020, 5, 21");
        
        p.ver(o.reservaConsulta(20, 28226806, Fecha1).resultado, Retorno.Resultado.OK, "Registro exitoso de una reserva de consulta en la fecha: 2023, 5, 12");
        
        p.ver(o.reservaConsulta(29, 42068320, Fecha2).resultado, Retorno.Resultado.OK, "Registro exitoso de una reserva de consulta en la fecha: 2023, 5, 24");
        
        //Casos de demostración de error al registrar reserva de consulta:
        
        p.ver(o.reservaConsulta(21, 0, Fecha2).resultado, Retorno.Resultado.ERROR_1, "No se puede realizar el registro dado que el paciente ingresado no existe.");
        
        p.ver(o.reservaConsulta(99, 42004416, Fecha2).resultado, Retorno.Resultado.ERROR_2, "No se puede realizar el registro dado que el médico ingresado no existe.");
        
        p.ver(o.reservaConsulta(21, 42004416, Fecha2).resultado, Retorno.Resultado.ERROR_3, "No se puede realizar el registro dado que el médico ingresado no existe.");
        
        p.ver(o.reservaConsulta(21, 57963975 , Fecha5).resultado, Retorno.Resultado.ERROR_4, "No se puede realizar el registro dado que el médico no está disponible en esa fecha.");
        
        
        //Caso de demostración de error al eliminar un paciente o médico con consultas:
        
        p.ver(o.eliminarPaciente(57963975).resultado, Retorno.Resultado.ERROR_2, "Un paciente agendado no puede ser eliminado.");
        
        p.ver(o.eliminarPaciente(50041075).resultado, Retorno.Resultado.ERROR_2, "Un paciente agendado no puede ser eliminado.");
        
        p.ver(o.eliminarPaciente(28226806).resultado, Retorno.Resultado.ERROR_2, "Un paciente agendado no puede ser eliminado.");
        
        p.ver(o.eliminarMedico(23).resultado, Retorno.Resultado.ERROR_2, "Un médico con consultas no puede ser eliminado.");  
        
        p.ver(o.eliminarMedico(24).resultado, Retorno.Resultado.ERROR_2, "Un médico con consultas no puede ser eliminado."); 
        
        p.ver(o.eliminarMedico(26).resultado, Retorno.Resultado.ERROR_2, "Un médico con consultas no puede ser eliminado."); 
        
        
        //Listar consultas:
        
        p.ver(o.listarConsultas(21).resultado, Retorno.Resultado.OK, o.listarConsultas(21).valorString + "Listado exitoso.");
        
        p.ver(o.listarConsultas(23).resultado, Retorno.Resultado.OK, o.listarConsultas(23).valorString + "Listado exitoso.");
        
        p.ver(o.listarConsultas(24).resultado, Retorno.Resultado.OK, o.listarConsultas(24).valorString + "Listado exitoso.");
        
        p.ver(o.listarConsultas(26).resultado, Retorno.Resultado.OK, o.listarConsultas(26).valorString + "Listado exitoso.");
        
        p.ver(o.listarConsultas(20).resultado, Retorno.Resultado.OK, o.listarConsultas(20).valorString + "Listado exitoso.");
        
        //Caso de error:
        
        p.ver(o.listarConsultas(99).resultado, Retorno.Resultado.ERROR_1, "No se pueden listar las consultas de un médico que no existe.");
        
        
        //2.7 - Cancelar reserva.
        
        p.ver(o.cancelarReserva(20, 28226806).resultado, Retorno.Resultado.OK, "Reserva cancelada con exito.");  
        
        //Casos de error:
         
        p.ver(o.cancelarReserva(21, 0).resultado, Retorno.Resultado.ERROR_1, "No se puede cancelar una reserva con un paciente inexistente.");
         
        p.ver(o.cancelarReserva(99, 28226806).resultado, Retorno.Resultado.ERROR_2, "No se puede cancelar una reserva con un médico inexistente.");
        
        p.ver(o.cancelarReserva(21, 50041075).resultado, Retorno.Resultado.ERROR_3, "El paciente no tuvo consultas con el médico ingresado.");
         
        //p.ver(o.cancelarReserva(28, 42068320).resultado, Retorno.Resultado.ERROR_4, "El paciente no tiene consultas en estado pendiente con el médico.");
         
        
        //2.8 - Anuncia llegada en el tótem.
        
        p.ver(o.anunciaLlegada(23, 48392685).resultado, Retorno.Resultado.OK, "El sistema ha sido correctamente notificado de su llegada, proceda a esperar a ser llamado."); 
        
        p.ver(o.anunciaLlegada(23, 11526122).resultado, Retorno.Resultado.OK, "El sistema ha sido correctamente notificado de su llegada, proceda a esperar a ser llamado."); 
        
        p.ver(o.anunciaLlegada(24, 52723306).resultado, Retorno.Resultado.OK, "El sistema ha sido correctamente notificado de su llegada, proceda a esperar a ser llamado."); 
        
        p.ver(o.anunciaLlegada(24, 57963975).resultado, Retorno.Resultado.OK, "El sistema ha sido correctamente notificado de su llegada, proceda a esperar a ser llamado."); 
        
        p.ver(o.anunciaLlegada(26, 50041075).resultado, Retorno.Resultado.OK, "El sistema ha sido correctamente notificado de su llegada, proceda a esperar a ser llamado."); 
        
        p.ver(o.anunciaLlegada(29, 42068320).resultado, Retorno.Resultado.OK, "El sistema ha sido correctamente notificado de su llegada, proceda a esperar a ser llamado."); 
        
        //Casos de error:
        
        p.ver(o.anunciaLlegada(26, 0).resultado, Retorno.Resultado.ERROR_1, "El paciente ingresado no existe en el sistema."); 
        
        p.ver(o.anunciaLlegada(23, 50041075).resultado, Retorno.Resultado.ERROR_2, "El paciente no tiene consultas registradas con este médico."); 
       
        
        //2.9 - Terminar consulta médico paciente.
        
        p.ver(o.terminarConsultaMedicoPaciente(52723306, 24, "Traumatismo craneal.").resultado, Retorno.Resultado.OK, "La consulta se cerró con éxito."); 
        
        p.ver(o.terminarConsultaMedicoPaciente(42068320, 29, "Traumatismo craneal.").resultado, Retorno.Resultado.OK, "La consulta se cerró con éxito.");
        
        //Casos de error:
        
        p.ver(o.terminarConsultaMedicoPaciente(0, 24, "Epilepsia diagnosticada.").resultado, Retorno.Resultado.ERROR_1, "No se puede terminar la consulta dado que el paciente ingresado no existe en el sistema.");
        
        p.ver(o.terminarConsultaMedicoPaciente(55208532, 23, "Rotura de hueso.").resultado, Retorno.Resultado.ERROR_2, "No se puede terminar la consulta dado el paciente no tiene consultas en estado 'en espera'.");
        
        
        //2.10 - Cerrar consulta.
        
        p.ver(o.cerrarConsulta(21, Fecha2).resultado, Retorno.Resultado.OK, "Consulta cerrada con éxito."); 
        
        //Casos de error:
        
        p.ver(o.cerrarConsulta(99, Fecha2).resultado, Retorno.Resultado.ERROR_1, "No se puede cerrar una consulta con un médico inexistente."); 
        
        p.ver(o.cerrarConsulta(21, Fecha5).resultado, Retorno.Resultado.ERROR_2, "No se encuentra una consulta con ese médico en la fecha ingresada."); 
        
        
        //3.1 - Listado de médicos:
        
        p.ver(o.listarMédicos().resultado, Retorno.Resultado.OK, "Listado de Médicos luego de intentar registrar Médicos ya existentes. " + o.listarMédicos().valorString);
        
        
        //3.2 - Listado de pacientes:
        
        p.ver(o.listarPacientes().resultado, Retorno.Resultado.OK, "Lista de Pacientes:" + o.listarPacientes().valorString);
        
        
        //3.3 - Listado de consultas:
        
        p.ver(o.listarConsultas(21).resultado, Retorno.Resultado.OK, o.listarConsultas(21).valorString + "Listado exitoso.");
        
        p.ver(o.listarConsultas(23).resultado, Retorno.Resultado.OK, o.listarConsultas(23).valorString + "Listado exitoso.");
        
        p.ver(o.listarConsultas(24).resultado, Retorno.Resultado.OK, o.listarConsultas(24).valorString + "Listado exitoso.");
        
        p.ver(o.listarConsultas(26).resultado, Retorno.Resultado.OK, o.listarConsultas(26).valorString + "Listado exitoso.");
        
        p.ver(o.listarConsultas(20).resultado, Retorno.Resultado.OK, o.listarConsultas(20).valorString + "Listado exitoso.");
        
        
        //3.4 - Listar pacientes en espera:
        
        p.ver(o.listarPacientesEnEspera(23, Fecha3).resultado, Retorno.Resultado.OK, "Listado de todos los pacientes en espera para el médico ingresado: " + o.listarPacientesEnEspera(23, Fecha3).valorString);
        
        p.ver(o.listarPacientesEnEspera(24, Fecha4).resultado, Retorno.Resultado.OK, "Listado de todos los pacientes en espera para el médico ingresado: " + o.listarPacientesEnEspera(24, Fecha4).valorString);
        
        
        //Caso de error:
        
         p.ver(o.listarPacientesEnEspera(23, Fecha5).resultado, Retorno.Resultado.ERROR_1, "El médico no tiene consultas en la fecha ingresada: " + o.listarPacientesEnEspera(23, Fecha5).valorString);
        
         
        //3.5 - Listar consultas pendientes de un paciente:
        
        p.ver(o.consultasPendientesPaciente(55208532).resultado, Retorno.Resultado.OK, "Listado de todas las consultas pendientes que tiene el paciente ingresado: " + o.consultasPendientesPaciente(55208532).valorString);
        
         //Caso de error:
         
         p.ver(o.consultasPendientesPaciente(0).resultado, Retorno.Resultado.ERROR_1, "No existe paciente con la cédula ingresada. " + o.consultasPendientesPaciente(0).valorString);
        
         
        //3.6 - Listar historia clínica del paciente:
        
        p.ver(o.historiaClínicaPaciente(52723306).resultado, Retorno.Resultado.OK, "Listado de la historia clínica del paciente ingresado: " + o.historiaClínicaPaciente(52723306).valorString);
        
         
        //Caso de error:
        
        p.ver(o.historiaClínicaPaciente(0).resultado, Retorno.Resultado.ERROR_1, "No existe paciente con la cédula ingresada. " + o.historiaClínicaPaciente(0).valorString);
        
        //3.7 - Total de reservas.
        
        p.ver(o.reporteDePacientesXFechaYEspecialidad(5, 2023).resultado, Retorno.Resultado.OK,"Reporte de todos los pacientes que han sido atendidos por cada especialidad en el año y mes ingresados: "+o.reporteDePacientesXFechaYEspecialidad(5, 2023).valorString);
        
        p.ver(o.reporteDePacientesXFechaYEspecialidad(3, 2002).resultado, Retorno.Resultado.ERROR_1,"El año ingresado se encuentra fuera del rango aceptado: "+o.reporteDePacientesXFechaYEspecialidad(3, 2002).valorString);
        
        
        p.imprimirResultadosPrueba();
        
    }
}

