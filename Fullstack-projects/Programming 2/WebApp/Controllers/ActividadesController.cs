using Microsoft.AspNetCore.Mvc;
using Dominio;

namespace WebApp.Controllers
{   //Controlador que nos permitirá definir el funcionamiento de las distintas Vistas para las Actividades; en donde podremos estructurar métodos de obtención y devolución en base a los requerimientos planteados.
    public class ActividadesController : Controller
    {
        Sistema s = Sistema.GetInstancia(); //Función que nos permitirá tener a mano todo el contenido del Sistema en el dominio. Métodos, precargas, etc.
        public IActionResult Index() //Vista general que devuelve todas las actividades.
        {
            return View(s.GetActividades());
        }

		public IActionResult BusquedaFechaConcreta()
		{
			return View(s.GetActividades());
		}

		[HttpPost]
		public ActionResult BusquedaFechaConcreta(DateTime Fecha)
		{
			return View(s.ActividadesRangoFecha(Fecha, Fecha));
		}



	}
}
