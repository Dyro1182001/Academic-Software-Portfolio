using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace Dominio
{
	public class Sistema
	{

		#region Singleton

		//Singleton que nos permitirá asegurarnos de que todos los datos se carguen correctamente y estén obtenidos cuando necesitemos utilizarlos, además de asegurarse de que el Sistema siempre esté funcionando y que se eviten errores en caso de null.

		private static Sistema instancia = null;
		private Sistema()
		{
			Precarga();
		}
		public static Sistema GetInstancia()
		{

			if (instancia == null)
			{
				instancia = new Sistema();
			}
			return instancia;
		}

		#endregion

		#region Listas 

		//Creamos y preparamos tanto las Listas correspondientes para el obligatorio como sus métodos de obtención y devolución de las variables dentro de las mismas.

		private List<Actividad> _actividades = new List<Actividad>();
		private List<Proveedor> _proveedores = new List<Proveedor>();
		private List<Huesped> _huespedes = new List<Huesped>();
		private List<Agenda> _agendas = new List<Agenda>();
		private List<Usuario> _usuarios = new List<Usuario>();

		public List<Actividad> GetActividades() 
		{
			return _actividades;
		}

		public Actividad GetActividad(int id)
		{
			foreach (Actividad a in _actividades)
			{
				if (id == a._Id)
				{
					return a;
				}

			}
			return null;
		}

		public List<Agenda> GetAgendas()
		{
			_agendas.Sort();
			return _agendas;
		}

		public Agenda GetAgenda(int id)
		{
			foreach (Agenda A in _agendas)
			{
				if (id == A._Id)
				{
					return A;
				}

			}
			return null;
		}

		public List<Agenda> GetMisAgendas(int id)
		{
			List<Agenda> _misAgendas = new List<Agenda>();
			foreach (Agenda a in _agendas)
			{
				if (id == a._Huesped._Id)
				{
					_misAgendas.Add(a);
				}

			}
			_misAgendas.Sort();
			return _misAgendas;
		}

		public List<Agenda> BuscarAgendasHuesped(string tipoDoc, string numDoc)
        //Nos permite obtener todas las agendas de un huesped determinado en base a su tipo de documento y número del mismo como parámetros.
        {
            List<Agenda> ret = new List<Agenda>();
			foreach (Agenda agenda in _agendas)
			{
				if (agenda._Huesped._TipoDocumento == tipoDoc && agenda._Huesped._NumDocumento == numDoc)
				{
					ret.Add(agenda);
				}
			}
			ret.Sort();
			return ret;
		}

		public bool BuscarAgendaIdH(int id, Actividad A)
		{
			foreach (Agenda agenda in _agendas)
			{
				if (agenda._Huesped == GetUsuario(id) && agenda._Actividad == A)
				{
					return true;
				}
			}

			return false;
		}

		public List<Proveedor> GetProveedores()
		{
			_proveedores.Sort();
			return _proveedores;
		}

		public Proveedor GetProveedor(int id)
		{
			foreach (Proveedor P in _proveedores)
			{
				if (id == P._Id)
				{
					return P;
				}

			}
			return null;
		}

		public List<Huesped> GetHuespedes()
		{
			return _huespedes;
		}

		public Usuario GetUsuario(int id)
		{
			foreach (Usuario usuario in _usuarios)
			{
				if (id == usuario._Id)
				{
					return usuario;
				}

			}
			return null;
		}

        public List<Usuario> GetUsuarios()
		{
			return _usuarios;
		}

		public List<Actividad> GetActividadesRangoFechasCosto(DateTime fecha1, DateTime fecha2, double costo)
        //Nos permite obtener todas actividades entre un rango determinado de fechas y costo.
        {
            List<Actividad> ret = new List<Actividad>();
			foreach (Actividad actividad in _actividades)
			{
				if (actividad._FechaActividad > fecha1 && actividad._FechaActividad < fecha2 && actividad._Costo > costo)
				{
					ret.Add(actividad);
				}
			}
			ret.Sort();
			return ret;
		}

		public List<Actividad> ActividadesRangoFecha(DateTime fecha1, DateTime fecha2)
        //Nos permite obtener todas actividades entre un rango determinado de fechas.
        {
            List<Actividad> ret = new List<Actividad>();
			foreach (Actividad actividad in _actividades)
			{
				if (actividad._FechaActividad >= fecha1 && actividad._FechaActividad <= fecha2)
				{
					ret.Add(actividad);
				}
			}
			ret.Sort();
			return ret;
		}

		#endregion

		#region Altas

		//Diseño de métodos de Alta para las distintas clases, de forma que podamos asegurarnos que los ingresos en cada lista son apropiados y funcionales.
		public void AltaHuesped(Huesped huesped)
		{

			try
			{
				huesped.EsValido();
				_huespedes.Add(huesped);
			}
			catch (Exception e)
			{

				throw e;

			}
		}
		public void AltaUsuario(Usuario u)
		{

			try
			{
				u.EsValido();
				_usuarios.Add(u);
			}
			catch (Exception e)
			{

				throw e;

			}
		}

		public void AltaProveedor(Proveedor proveedor)
		{
			try
			{
				proveedor.EsValido();
				_proveedores.Add(proveedor);
			}
			catch (Exception e)
			{

				throw e;
			}
		}

		public void AltaActividad(Actividad actividad)
		{
			try
			{
				actividad.EsValido();
				_actividades.Add(actividad);
			}
			catch (Exception e)
			{

				throw e;
			}
		}

		public void AltaAgenda(Agenda a)
		{
			a.EsValido();
			_agendas.Add(a);
		}


		#endregion

		#region Métodos
		public bool CambiarValorPromoProveedor(int id, int nuevoValorPromo)
		//Método que nos permite buscar a un Proveedor por id para posteriormente modificar su valor de Descuento por uno nuevo, ingresado por el Usuario.
		{
			foreach (Proveedor p in _proveedores)
			{
				if (p._Id == id)
				{
					p._Descuento = nuevoValorPromo;
					return true;
				}
			}

			return false;
		}

		public bool MatchNomProveedor(string Nombre)
		//Método que se encarga de comprobar si existen otros Proveedores con el nombre indicado por el usuario. 
		{
			foreach (Proveedor p in _proveedores)
			{
				if (Nombre.Equals(p._NombreProveedor))
				{
					return true;
				}
			}
			return false;
		}

		public bool MatchTipoNumDocumento(string TipoDocumento, string NumDocumento)
		//Método que se encarga de corroborar si existen otros huespedes que compartan el mismo tipo y número de documento que los indicados por el Usuario. 
		{
			foreach (Huesped h in _huespedes)
			{
				if (TipoDocumento.Equals(h._TipoDocumento) && NumDocumento.Equals(h._NumDocumento))
				{
					return true;
				}
			}
			return false;
		}

		public Usuario UsuarioLogin(string Email, string Password)
        //Método que permite el ingreso de un Usuario cualquiera, tras validar sus datos Email y Contraseña que son recibidos por parámetro..
        {

            foreach (Usuario usuarioExistente in _usuarios)
			{
				if (usuarioExistente._EmailRegistro.Equals(Email) && usuarioExistente._Contrasenia.Equals(Password))
				{
					return usuarioExistente;
				}
			}
			return null;
		}

		public Agenda CrearAgenda(int IdH, int IdA)
        //Método que permite la creación de una Agenda tras recibir el Id del Huesped interesado en agendarse y el Id de la Actividad a la que está tratando de agendarse.
        {
            Usuario H = GetUsuario(IdH);
			Actividad A = GetActividad(IdA);
			Agenda ag1 = new Agenda("PENDIENTE_PAGO", (Huesped)H, A, DateTime.Now);
			//A._CantMaxPersonas = A._CantMaxPersonas - 1;

			return ag1;
		}

		public void ConfirmarAgenda(int id)
        //Método que permite al usuario Operador confirmar Agendas.
        {
            foreach (Agenda A in _agendas)
			{
				if (id == A._Id)
				{
					A._EstadoAgenda = "CONFIRMADA";
				}

			}
		}


		#endregion

		#region Precarga

		private void Precarga()
		{
			//Método que nos permite ingresar los datos que precargaremos para realizar distintas pruebas y acatar lo establecido en la letra del obligatorio.

			Proveedor proveedor1 = new Proveedor("DreamWorks S.R.L.", "23048549", "Suarez 3380 Apto 304", 10);
			Proveedor proveedor2 = new Proveedor("Estela Umpierrez S.A.", "33459678", "Lima 2456", 7);
			Proveedor proveedor3 = new Proveedor("TravelFun", "29152020", "Misiones 1140", 9);
			Proveedor proveedor4 = new Proveedor("Rekreation S.A.", "29162019", "Bacacay 1211", 11);
			Proveedor proveedor5 = new Proveedor("Alonso & Umpierrez", "24051920", "18 de Julio 1956 Apto 4", 10);
			Proveedor proveedor6 = new Proveedor("Electric Blue", "26018945", "Cooper 678", 5);
			Proveedor proveedor7 = new Proveedor("Lúdica S.A.", "26142967", "Dublin 560", 4);
			Proveedor proveedor8 = new Proveedor("Gimenez S.R.L.", "29001010", "Andes 1190", 7);
			Proveedor proveedor9 = new Proveedor("Ricardo Piñeiro", "22041120", "Agraciada 2512 Apto. 1", 8);
			Proveedor proveedor10 = new Proveedor("Norberto Molina", "22001189", "Paraguay 2100", 9);

			AltaProveedor(proveedor1);
			AltaProveedor(proveedor2);
			AltaProveedor(proveedor3);
			AltaProveedor(proveedor4);
			AltaProveedor(proveedor5);
			AltaProveedor(proveedor6);
			AltaProveedor(proveedor7);
			AltaProveedor(proveedor8);
			AltaProveedor(proveedor9);
			AltaProveedor(proveedor10);

			//Las fechas fueron creadas aparte para poder asignar a la clase una variable de fecha en lugar de escribir la fecha
			//Ayuda tambien a ahorrar código ya que multiples fechas fueron reutilizadas

			DateTime fecha1 = new DateTime(2552, 5, 11);
			DateTime fecha2 = new DateTime(2552, 5, 16);
			DateTime fecha3 = new DateTime(2552, 5, 20);
			DateTime fecha4 = new DateTime(2552, 5, 27);
			DateTime fecha5 = new DateTime(2552, 6, 2);
			DateTime fecha6 = new DateTime(2552, 6, 7);
			DateTime fecha7 = new DateTime(2552, 6, 22);
			DateTime fecha8 = new DateTime(2552, 8, 8);
			DateTime fecha9 = new DateTime(2552, 8, 14);
			DateTime fecha10 = new DateTime(2552, 8, 17);
			DateTime fecha11 = new DateTime(2552, 8, 28);
			DateTime fecha12 = new DateTime(2552, 9, 5);
			DateTime fecha13 = new DateTime(2552, 9, 11);
			DateTime fecha14 = new DateTime(2552, 9, 24);
			DateTime fecha15 = new DateTime(2552, 10, 4);


			//Los costos estan en dolares, es por eso que los valores son tan bajos

			ActividadHostal actividadH1 = new ActividadHostal("Partido de Futbol", "Partido de Futbol 5 organizado por el mismo hotel", fecha1, 12, 18, 5, "Juan Alberto", "La segunda cancha del gimnasio en el primer piso", false);
			ActividadHostal actividadH2 = new ActividadHostal("Partido de Basketball", "Partido de Basketball organizado por el mismo hotel", fecha2, 10, 20, 6, "Gregorio Lopez", "La primer cancha del gimnasio en el segundo piso", false);
			ActividadHostal actividadH3 = new ActividadHostal("Partido de Volleyball", "Partido de Volleyball organizado por el mismo hotel", fecha3, 14, 16, 4, "Ana Rodriguez", "La primera cancha del gimnasio en el primer piso", false);
			ActividadHostal actividadH4 = new ActividadHostal("Partido de Baseball", "Partido de Baseball organizado por el mismo hotel", fecha4, 18, 16, 4, "Catalina Ramirez", "La cuarta cancha del gimnasio en el segundo piso", false);
			ActividadHostal actividadH5 = new ActividadHostal("Torneo de Ping Pong", "Torneo de Ping Pong organizado por el mismo hotel", fecha5, 20, 14, 3, "José Antonio", "La tercera cancha del gimnasio en el primer piso", false);
			ActividadHostal actividadH6 = new ActividadHostal("Torneo de Futbol", "Torneo de Futbol organizado por el mismo hotel", fecha6, 40, 22, 7, "Juan Alberto", "La cuarta cancha del gimnasio en el primer piso", false);
			ActividadHostal actividadH7 = new ActividadHostal("Torneo de Basketball", "Torneo de Basketball organizado por el mismo hotel", fecha7, 50, 25, 8, "Gregorio Lopez", "La tercera cancha del gimnasio en el segundo piso", false);
			ActividadHostal actividadH8 = new ActividadHostal("Torneo de Volleyball", "Torneo de Volleyball organizado por el mismo hotel", fecha8, 50, 19, 5, "Ana Rodriguez", "La segunda cancha del gimnasio en el segundo piso", false);
			ActividadHostal actividadH9 = new ActividadHostal("Torneo de Baseball", "Torneo de Baseball organizado por el mismo hotel", fecha9, 55, 16, 6, "Catalina Ramirez", "La quinta cancha del gimnasio en el segundo piso", false);
			ActividadHostal actividadH10 = new ActividadHostal("Torneo de Natación", "Torneo de Natación organizado por el mismo hotel", fecha10, 30, 20, 5, "José Antonio", "Piscinas inferiores dentro del primer edificio", false);

			AltaActividad(actividadH1);
			AltaActividad(actividadH2);
			AltaActividad(actividadH3);
			AltaActividad(actividadH4);
			AltaActividad(actividadH5);
			AltaActividad(actividadH6);
			AltaActividad(actividadH7);
			AltaActividad(actividadH8);
			AltaActividad(actividadH9);
			AltaActividad(actividadH10);



			ActividadTercerizada actividadT1 = new ActividadTercerizada("Expedición", "Expedición por las cercanías del hotel hacia la selva", fecha2, 20, 21, 10, proveedor1, true, DateTime.Now);
			ActividadTercerizada actividadT2 = new ActividadTercerizada("Expedición", "Expedición por las cercanías del hotel hacia la playa", fecha4, 15, 10, 8, proveedor2, true, DateTime.Now);
			ActividadTercerizada actividadT3 = new ActividadTercerizada("Buceo", "Buceo en las aguas en la cercanía del hotel", fecha6, 30, 25, 15, proveedor3, true, DateTime.Now);
			ActividadTercerizada actividadT4 = new ActividadTercerizada("Buceo", "Buceo en las aguas en la cercanía de una ciudad a pocos kilómetros del hotel", fecha8, 10, 25, 20, proveedor4, true, DateTime.Now);
			ActividadTercerizada actividadT5 = new ActividadTercerizada("Reserva Natural", "Visita a Reserva Natural ubicada en las cercanías del hotel", fecha10, 40, 8, 15, proveedor5, true, DateTime.Now);
			ActividadTercerizada actividadT6 = new ActividadTercerizada("Maratón en Bicicleta", "Maratón sobre la ciudad en Bicicleta", fecha1, 50, 20, 10, proveedor1, true, DateTime.Now);
			ActividadTercerizada actividadT7 = new ActividadTercerizada("Cine", "Cine ubicado a pocas cuadras de nuestro Hostal organizado por un tercero", fecha3, 25, 20, 12, proveedor2, true, DateTime.Now);
			ActividadTercerizada actividadT8 = new ActividadTercerizada("Caminata", "Caminata de 5km hacia una playa cercana y de vuelta", fecha5, 30, 20, 10, proveedor3, true, DateTime.Now);
			ActividadTercerizada actividadT9 = new ActividadTercerizada("Reserva Natural", "Visita a Reserva Natural ubicada en ciudad cercana", fecha7, 15, 12, 15, proveedor4, true, DateTime.Now);
			ActividadTercerizada actividadT10 = new ActividadTercerizada("Reserva Natural", "Visita a Reserva Natural ubicada en ciudad cercana", fecha9, 15, 12, 15, proveedor5, true, DateTime.Now);
			ActividadTercerizada actividadT11 = new ActividadTercerizada("Expedición", "Expedición por las cercanías del hotel hacia el bosque", fecha11, 20, 21, 10, proveedor1, true, DateTime.Now);
			ActividadTercerizada actividadT12 = new ActividadTercerizada("Buceo", "Buceo en las aguas en la cercanía de una ciudad a pocos kilómetros del hotel", fecha12, 25, 21, 15, proveedor2, true, DateTime.Now);
			ActividadTercerizada actividadT13 = new ActividadTercerizada("Caminata", "Caminata de 10km hacia una playa cercana y de vuelta", fecha13, 10, 18, 8, proveedor3, true, DateTime.Now);
			ActividadTercerizada actividadT14 = new ActividadTercerizada("Cine", "Cine ubicado a pocas cuadras de nuestro Hostal organizado por un tercero", fecha14, 30, 18, 10, proveedor4, true, DateTime.Now);
			ActividadTercerizada actividadT15 = new ActividadTercerizada("Maratón en Bicicleta", "Maratón sobre la ciudad en Bicicleta", fecha15, 45, 15, 12, proveedor5, true, DateTime.Now);

			AltaActividad(actividadT1);
			AltaActividad(actividadT2);
			AltaActividad(actividadT3);
			AltaActividad(actividadT4);
			AltaActividad(actividadT5);
			AltaActividad(actividadT6);
			AltaActividad(actividadT7);
			AltaActividad(actividadT8);
			AltaActividad(actividadT9);
			AltaActividad(actividadT10);
			AltaActividad(actividadT11);
			AltaActividad(actividadT12);
			AltaActividad(actividadT13);
			AltaActividad(actividadT14);
			AltaActividad(actividadT15);

			DateTime fechaNace1 = new DateTime(1973, 10, 16);
			DateTime fechaNace2 = new DateTime(1984, 2, 26);
			DateTime fechaNace3 = new DateTime(1979, 3, 7);

			Huesped huesped1 = new Huesped("reospeedwagon@gmail.com", "Elrodo1798", "CI", "51630466", "Roberto", "Speedwagon", "D6", fechaNace1, 4);
			Huesped huesped2 = new Huesped("jojo@gmail.com", "Zawarudo1989", "Pasaporte", "C800155", "Jorge", "Joviales", "C2", fechaNace2, 2);
			Huesped huesped3 = new Huesped("cegonzalez@gmail.com", "Mondongo17", "CI", "44955322", "Cesar", "Gonzalez", "B5", fechaNace3, 1);

			Operador operador1 = new Operador("frantom422@gmail.com", "Francisc2002");
			Operador operador2 = new Operador("rodelgado01@gmail.com", "Rodel2001");

			AltaUsuario(huesped1);
			AltaUsuario(huesped2);
			AltaUsuario(huesped3);
			AltaUsuario(operador1);
			AltaUsuario(operador2);

			Agenda agenda1 = new Agenda("PENDIENTE_PAGO", huesped1, actividadH1, DateTime.Now);
			Agenda agenda2 = new Agenda("CONFIRMADA", huesped2, actividadH2, DateTime.Now);
			Agenda agenda3 = new Agenda("PENDIENTE_PAGO", huesped3, actividadH3, DateTime.Now);
			Agenda agenda4 = new Agenda("PENDIENTE_PAGO", huesped2, actividadH4, DateTime.Now);
			Agenda agenda5 = new Agenda("CONFIRMADA", huesped1, actividadH5, DateTime.Now);
			Agenda agenda6 = new Agenda("PENDIENTE_PAGO", huesped3, actividadH6, DateTime.Now);
			Agenda agenda7 = new Agenda("PENDIENTE_PAGO", huesped1, actividadH7, DateTime.Now);
			Agenda agenda8 = new Agenda("CONFIRMADA", huesped2, actividadH8, DateTime.Now);
			Agenda agenda9 = new Agenda("PENDIENTE_PAGO", huesped3, actividadH9, DateTime.Now);
			Agenda agenda10 = new Agenda("PENDIENTE_PAGO", huesped1, actividadH10, DateTime.Now);
			Agenda agenda11 = new Agenda("PENDIENTE_PAGO", huesped2, actividadT1, DateTime.Now);
			Agenda agenda12 = new Agenda("PENDIENTE_PAGO", huesped3, actividadT2, DateTime.Now);
			Agenda agenda13 = new Agenda("PENDIENTE_PAGO", huesped1, actividadT3, DateTime.Now);
			Agenda agenda14 = new Agenda("PENDIENTE_PAGO", huesped2, actividadT4, DateTime.Now);
			Agenda agenda15 = new Agenda("PENDIENTE_PAGO", huesped3, actividadT5, DateTime.Now);

			AltaAgenda(agenda1);
			AltaAgenda(agenda2);
			AltaAgenda(agenda3);
			AltaAgenda(agenda4);
			AltaAgenda(agenda5);
			AltaAgenda(agenda6);
			AltaAgenda(agenda7);
			AltaAgenda(agenda8);
			AltaAgenda(agenda9);
			AltaAgenda(agenda10);
			AltaAgenda(agenda11);
			AltaAgenda(agenda12);
			AltaAgenda(agenda13);
			AltaAgenda(agenda14);
			AltaAgenda(agenda15);

		}
		#endregion

	}
}
