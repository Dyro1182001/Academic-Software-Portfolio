using Dominio;
using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;
using System.Reflection.Metadata;
using WebApp.Models;

namespace WebApp.Controllers
{//Controlador que nos permitirá definir el funcionamiento de las distintas Vistas generales; en donde podremos estructurar métodos de obtención y devolución en base a los requerimientos planteados.
    public class HomeController : Controller
    {

        Sistema s = Sistema.GetInstancia(); //Función que nos permitirá tener a mano todo el contenido del Sistema en el dominio. Métodos, precargas, etc.


        private readonly ILogger<HomeController> _logger;

        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
        }

        public IActionResult Index() //Obtenemos los datos del usuario que se encuentra navegando por nuestra web para poder saludarlo y aclarar si hay una sesión abierta o no.
        {
            string? logueadoNombre = HttpContext.Session.GetString("LogueadoEmail");
            int? logueadoId = HttpContext.Session.GetInt32("LogueadoId");
            if (logueadoNombre != null)
            {

                ViewBag.msgBienvenida = "Hola " + logueadoNombre;
            }
            else
            {

                ViewBag.msgBienvenida = "Hola, debe iniciar sesión para navegar";
            }

            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }

        public IActionResult Login()
        {
            return View();
        }

        public IActionResult LogoutFuncional() //Función que permite a los usuarios cerrar la sesión a través de una limpieza total de la misma y un redireccionamiento a la vista general.
        {
            HttpContext.Session.Clear();
            return RedirectToAction("Index", "Home");

        }




        [HttpPost]
        public IActionResult Login(string Email, string Password) //Obtenemos los datos del usuario que se encuentra navegando por nuestra web con el fin de definir qué debemos mostrarle y cuantos permisos tiene.
        {
            Usuario logueada = s.UsuarioLogin(Email, Password);
            if (logueada != null)
            {
                HttpContext.Session.SetInt32("LogueadoId", logueada._Id);
                HttpContext.Session.SetString("LogueadoEmail", logueada._EmailRegistro);
                HttpContext.Session.SetString("LogueadoRol", logueada.Rol());
                if(logueada.Rol() == ("Operador"))
                {
                    Operador aux = logueada as Operador;
                    HttpContext.Session.SetString("LogueadoNivel", "Admin");
                }
                return RedirectToAction("Index");

            }
            else
            {

                ViewBag.msg = "Error en los datos";
                return View();
            }

        }

        public IActionResult VerDatosPersonales() //Permitimos a los usuarios revisar sus propios datos personales.
        {
            int? logueadoId = HttpContext.Session.GetInt32("LogueadoId");
            Usuario h1 = s.GetUsuario((int)logueadoId);
            return View(h1);
        }

		public IActionResult VerDatosOp()
		{
			int? logueadoId = HttpContext.Session.GetInt32("LogueadoId");
			Usuario h1 = s.GetUsuario((int)logueadoId);
			return View(h1);
		}
	}
}