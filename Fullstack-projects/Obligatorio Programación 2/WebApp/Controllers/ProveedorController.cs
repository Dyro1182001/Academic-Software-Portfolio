using Dominio;
using Microsoft.AspNetCore.Mvc;

namespace WebApp.Controllers
{//Controlador que nos permitirá definir el funcionamiento de las distintas Vistas para los Proveedores; en donde podremos estructurar métodos de obtención y devolución en base a los requerimientos planteados.
    public class ProveedorController : Controller
    {
		Sistema s = Sistema.GetInstancia(); //Función que nos permitirá tener a mano todo el contenido del Sistema en el dominio. Métodos, precargas, etc.
        public IActionResult Index() //Vista general que devuelve todos los Proveedores.
        {
            return View(s.GetProveedores());
        }

		public IActionResult Edit(int Id) //Vista que permite seleccionar a un Proveedor determinado para posteriormente editarlo.
        {
			Proveedor P = s.GetProveedor(Id);
			return View(P);
		}

		[HttpPost]

		public IActionResult Edit(int Id, int descuento) //Vista que define exactamente qué seremos capaces de editar dentro de Proveedor y qué necesitaremos para hacerlo, en este caso necesitaremos el id del mismo y un valor de descuento asignado por el usuario que reemplazará el anterior.
        {
			Proveedor P = s.GetProveedor(Id);
			try
			{
				P._Descuento = descuento;
				ViewBag.msg = "Editado con exito";
			}
			catch (Exception e)
			{
				ViewBag.msg = e.Message;
			}
			return View(P);
		}

		
	}
}
