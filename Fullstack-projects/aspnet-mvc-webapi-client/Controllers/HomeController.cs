using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;
using System.Net.Http.Headers;
using System.Text;
using System.Text.Json;
using WebApiClient.Models;

namespace WebApiClient.Controllers
{
    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;

        /// <summary>
        ///       El cliente Http no se debe instanciar en cada solicitud, porque se puede saturar la conexión.
        ///     Por esa razón lo instanciamos como static.  Seteamos la Uri básica de la Api
        /// </summary>
        private static HttpClient _cli = new() { BaseAddress = new Uri("https://localhost:7240/api") };
        /// <summary>
        /// Uri para las operaciones con usuarios.
        /// </summary>
        private Uri _uriUsuario = new Uri($"{_cli.BaseAddress}/Usuario");

        /// <summary>
        /// Configurar las opciones para deserializar los objetos
        /// </summary>
        /// <remarks>
        /// PropertyNameCaseInsensitive = true: No tomar en cuenta las mayúsculas y minúsculas al desearializar
        /// WriteIndented= true: Hace que el json sea más legible
        /// </remarks>

        JsonSerializerOptions opciones = new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true, ///
            WriteIndented = true ///
        };
        /// <summary>
        /// Constructor. Observar que es independiente de la aplicación que procesa los usuarios.
        /// Configura para utilizar exclusivamente application/json (opcional, actualmente es el tipo MIME por defecto).
        /// </summary>
        /// <param name="logger">Por el momento se ignora</param>
        public HomeController(ILogger<HomeController> logger)
        {
            _logger = logger;
            _cli.DefaultRequestHeaders.Accept.Clear();
            _cli.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
        }
        /// <summary>
        /// Despliega la lista de usuarios obtenida al consumir la API utilizando HttpClient
        /// </summary>
        /// <returns>Retorna una vista con la lista de autores, si existe alguno.</returns>

        /// <summary>
        /// Método auxiliar para retornar el json obtenido a partir de la api.
        /// </summary>
        /// <param name="uri">Url donde está ubicado el recurso de la API a consumir</param>
        /// <returns>Un string con el contenido del body obtenido en la respuesta; habitualmente contendrá la info de un objeto o de una lista de objetos y se formateará como Json</returns>
        private string? GetRespuesta(Uri uri)
        {

            var response = _cli.GetAsync(uri).Result;
            response.EnsureSuccessStatusCode(); //Espera hasta obtener la respuesta; si no lo logra lanza una excepción

            //Leer el json que viene incluido en el contenido (body) 
            var json = response.Content.ReadAsStringAsync().Result;
            return json;
        }

       

        /// //////////////////////////////////////////////////////////////////

        public IActionResult Index()
        {
            string? logueadoNombre = HttpContext.Session.GetString("Alias");
            if (logueadoNombre != null)
            {

                ViewBag.msgBienvenida = "Bienvenido " + logueadoNombre;
            }
            else
            {

                ViewBag.msgBienvenida = "Bienvenido, debe iniciar sesión para navegar";
            }

            return View();
        }

        public IActionResult Privacy()
        {
            return View();
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}