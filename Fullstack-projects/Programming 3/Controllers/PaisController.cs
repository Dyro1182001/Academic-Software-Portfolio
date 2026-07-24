using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Text.Json;

namespace WebApiClient.Controllers
{
    public class PaisController : Controller
    {
        // GET: ConservacionController

        private HttpClient _clienteApi = new HttpClient();

        private JsonSerializerOptions _opciones = new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        };

        public PaisController()
        {

            //Indica cuál es la URL con la que se debe acceder a la API por defecto.

            _clienteApi.BaseAddress = new Uri(@"https://localhost:7240/api/Pais");
            _clienteApi.DefaultRequestHeaders.Accept.Clear();
            _clienteApi.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue(@"application/json"));
        }

        public ActionResult Index()
        {
            //Obtener todos los autores a través de la Web API.

            var respuesta = _clienteApi.GetAsync(_clienteApi.BaseAddress).Result;
            if (respuesta.IsSuccessStatusCode)
            {
                var contenido = respuesta.Content.ReadAsStringAsync();
                var usuarios = JsonSerializer.Deserialize<IEnumerable<Models.PaisModel>>(contenido.Result, _opciones);
                return View(usuarios);
            }
            else
            {
                ViewBag.Mensaje = $"No se pueden recuperar los estados de conservacion {respuesta.ToString()}";
                return View();
            }
        }
    }
}
