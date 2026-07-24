using Dominio;
using Microsoft.AspNetCore.Mvc;

namespace WebApp.Controllers
{//Controlador que nos permitirá definir el funcionamiento de las distintas Vistas para los Registros; en donde podremos estructurar métodos de obtención y devolución en base a los requerimientos planteados.
    public class RegistroController : Controller
    {
        Sistema s = Sistema.GetInstancia(); //Función que nos permitirá tener a mano todo el contenido del Sistema en el dominio. Métodos, precargas, etc.
        public IActionResult Create() //Vista que devuelve un menú de creación en donde podremos crear nuestro propio usuario y registrarlo.
        {
            return View();
        }


        [HttpPost]
        public IActionResult Create(Huesped H) //Vista que define cómo crearemos ese usuario exactamente y que finalmente le da de alta. Recibe al usuario huesped en proceso de registro por parámetro.
        {
            H._ValorNumerico = 1;
            try
            {
                
                s.AltaUsuario(H);
                ViewBag.msg = "Usuario registrado correctamente";
            }
            catch
            {

                ViewBag.msg = "Datos incorrectos";
            }
            return View(H);
        }
    }
}
