using Dominio;
using Microsoft.AspNetCore.Mvc;

namespace WebApp.Controllers
{//Controlador que nos permitirá definir el funcionamiento de las distintas Vistas para las Agendas; en donde podremos estructurar métodos de obtención y devolución en base a los requerimientos planteados.
    public class AgendaController : Controller
	{
		Sistema s = Sistema.GetInstancia(); //Función que nos permitirá tener a mano todo el contenido del Sistema en el dominio. Métodos, precargas, etc.
        public IActionResult Agendas() //Vista general que devuelve todas las agendas.
        {
			return View(s.GetAgendas());
		}

		public IActionResult MisAgendas(int id) //Vista específica que devuelve las agendas del usuario navegando actualmente por nuestra página en base a su id como parámetro.
        {
			int? logueadoId = HttpContext.Session.GetInt32("LogueadoId");
			if (logueadoId == null)
			{
				return RedirectToAction("Login", "Home");
			}
			else
			{
				return View(s.GetMisAgendas((int)logueadoId));
			}


		}

		public IActionResult Create(int Id) //Vista específica que devuelve la actividad pedida por el usuario, en base al id de la misma como parámetro.
        {
			return View(s.GetAgenda(Id));
		} 


        [HttpPost]
        public IActionResult Create(int id, bool IsChecked) //Vista específica que genera la agenda solicitada por el usuario, recibe id como parámetro de la creación de la misma y además le pide al usuario que confirme su solicitud por medio de un check box.
        {
            int? logueadoId = HttpContext.Session.GetInt32("LogueadoId");
            Usuario U = s.GetUsuario((int)logueadoId);
			Actividad Ac = s.GetActividad(id);
            Agenda A = new Agenda("PENDIENTE_PAGO", (Huesped)U, Ac, DateTime.Now);

			
			if (IsChecked)
            {
				if(U.GetEdad() > Ac._EdadMinima)
				{
                    try
                    {

                        s.AltaAgenda(A);
                        ViewBag.msg = "Agenda registrada correctamente";

                    }
                    catch (Exception e)
                    {

                        ViewBag.msg = e.Message;
                    }
				}
				else
				{
					ViewBag.msg = "Usted no tiene la edad mínima para participar de la actividad (" + Ac._EdadMinima + " años)";
				}
                
            }

            else
            {
                ViewBag.msg = "Debe seleccionar el checkbox";
            }

			return View() ;
        }

        public IActionResult Confirmacion(int id) //Vista específica que permite al usuario confirmar su solicitud.
        {
			return View(s.GetAgenda(id));
		}
		[HttpPost]
		public IActionResult Confirmacion(int id, bool IsChecked)
		{
			if (IsChecked)
			{
				s.ConfirmarAgenda(id);
				s.GetAgenda(id)._Actividad._CantMaxPersonas--;
				ViewBag.msg = "Cambio realizado";
			}
			else
			{
				ViewBag.msg = "Debe seleccionar el checkbox";
			}
			return View(s.GetAgenda(id));
		}

		[HttpPost]
		public ActionResult Agendas(string IdSelect, string numDocumento) //Vista específica que devuelve al usuario huesped todas sus agendas en base a su tipo y número de documento.
        {
			return View(s.BuscarAgendasHuesped(IdSelect, numDocumento));
		}
	}


}
