using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Text.Json;
using WebApiClient.Models;
using static System.Net.WebRequestMethods;

namespace WebApiClient.Controllers
{
    public class UsuarioController : Controller
    {
        // GET: UsuarioController

        private static HttpClient _clienteApi = new() { BaseAddress = new Uri(@"https://localhost:7240/api") };
        
        private Uri _uriUsuario = new Uri($"{_clienteApi.BaseAddress}/Usuario/");
        private Uri _uriUsuarioLogin = new Uri(@"https://localhost:7240/Usuario/Login");
        private Uri _uriUsuarioRegistro = new Uri(@"https://localhost:7240/Usuario/Registro");
        private Uri _uriUsuarioDelete = new Uri(@"https://localhost:7240/api/Usuario");
        private Uri _uriGetUsuarioById = new Uri(@"https://localhost:7240/api/Usuario/Usuario/");



        private JsonSerializerOptions _opciones = new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        };

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

        public UsuarioController()
        {

            //Indica cuál es la URL con la que se debe acceder a la API por defecto.


            _clienteApi.DefaultRequestHeaders.Accept.Clear();
            _clienteApi.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue(@"application/json"));
        }

        

        public ActionResult Index()
        {
            //Obtener todos los autores a través de la Web API.

            var respuesta = _clienteApi.GetAsync(_uriUsuario).Result;
            if (respuesta.IsSuccessStatusCode)
            {
                var contenido = respuesta.Content.ReadAsStringAsync();
                var usuarios = JsonSerializer.Deserialize<IEnumerable<Models.UsuarioModel>>(contenido.Result, _opciones);
                return View(usuarios);
            }
            else
            {
                ViewBag.Mensaje = $"No se pueden recuperar los usuarios {respuesta.ToString()}";
                return View();
            }
        }

        // GET: UsuarioController/Details/5
        public ActionResult Details(int id)
        {
            return View();
        }

        // GET: UsuarioController/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: UsuarioController/Create
        [HttpPost]
    
        public async Task<ActionResult> Create(UsuarioModel usuarioModel)
        {
            
            try
            {
                if (usuarioModel == null)
                {
                    return View(usuarioModel);
                }

                usuarioModel.Token = "";
                usuarioModel.ContraEncriptada = "";

                var usuSerializado = JsonSerializer.Serialize(usuarioModel);
                var contenidoRequest = new StringContent(usuSerializado, Encoding.UTF8, "application/json");

                var resultado = await _clienteApi.PostAsync($"{_uriUsuarioRegistro}", contenidoRequest);

                if (resultado.IsSuccessStatusCode)
                {
                    ViewBag.Mensaje = "Usuario registrado con éxito";
                    return View(usuarioModel);
                }
                else
                {
                    ViewBag.Mensaje = resultado.StatusCode;
                    return View(usuarioModel);
                }
            }
            catch (HttpRequestException ex)
            {
                ViewBag.Mensaje = $"Error al realizar la solicitud HTTP: {ex.Message}";
                return View(usuarioModel);
            }
            catch (Exception ex)
            {
                ViewBag.Mensaje = $"Error desconocido: {ex.Message}";
                return View(usuarioModel);
            }
        }


        public IActionResult Login()
        {
            return View();
        }

        [HttpPost]
        public IActionResult Login(UsuarioModel usuario)
        {
            usuario.Token = "Sin token";
            usuario.Rol = "Sin rol";
            usuario.ContraEncriptada = "Sin pass encriptada";

            var usuarioSerializado = JsonSerializer.Serialize(usuario, opciones);
            var json = new StringContent(usuarioSerializado, Encoding.UTF8, "application/json");

            var response = _clienteApi.PostAsync($"{_uriUsuarioLogin}", json).Result;
            if (response.IsSuccessStatusCode)
            { //Espera hasta obtener la respuesta; si no lo logra lanza una excepción

                //Leer el json que viene incluido en el contenido (body) 
                var jsonRespuesta = response.Content.ReadAsStringAsync().Result;
                var usrEncontrado = JsonSerializer.Deserialize<UsuarioModel>(jsonRespuesta, opciones);
                if (usrEncontrado == null || string.IsNullOrEmpty(usrEncontrado.Token))
                {
                    ViewBag.Mensaje = "Datos erroneos";
                    return View(usuario);
                }
                else
                {
                    _clienteApi.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", usrEncontrado.Token);
                  
                    HttpContext.Session.SetString("usuario", usrEncontrado.Token);
                    HttpContext.Session.SetString("Alias", usrEncontrado.Alias);

                    if (usrEncontrado.Alias == "Admin1")
                    {
                        HttpContext.Session.SetString("Rol", "Admin");
                    }
                    else
                    {
                        HttpContext.Session.SetString("Rol", "Verificado");
                    }

                    ViewBag.Mensaje = "Logueado correcto";
                    return RedirectToAction("Index", "Home");
                }
            }
            return View(usuario);
        }

        public ActionResult Logout()
        {
            HttpContext.Session.Clear();
            return RedirectToAction("Index", "Home");

        }

        // GET: UsuarioController/Delete/5
        public ActionResult Delete(int id)
        {
            var usuario = GetUsuario(id);
          
            return View(usuario);
        }

        // POST: UsuarioController/Delete/5
        [HttpPost]
        //[ValidateAntiForgeryToken]
        public ActionResult Delete(int id, UsuarioModel usuario, bool IsChecked)
        {
            if (IsChecked)
            {
                try
                {
                    var respuesta = _clienteApi.DeleteAsync($"{_uriUsuarioDelete}/{usuario.Id}");
                    if (((int)respuesta.Result.StatusCode) == StatusCodes.Status204NoContent)
                    {
                        return RedirectToAction(nameof(Index));
                    }
                        
                    ViewBag.Error = $"No fue posible eliminar el usuario {id}";

                    return View(usuario);
                }
                catch (Exception e)
                {
                    ViewBag.Error = $"Error desconocido: {e.Message}";
                    return View(usuario);
                }
            }
            else
            {
                ViewBag.Error = "Debe marcar la casilla";
                return View(usuario);
            }

        }

        #region Metodos auxiliares
        private UsuarioModel GetUsuario(int id)
        {
            var respuesta = _clienteApi.GetAsync(_uriGetUsuarioById+$"{id}").Result;
            if (respuesta.IsSuccessStatusCode)
            {
                var contenido = respuesta.Content.ReadAsStringAsync();
                var usuario = JsonSerializer.Deserialize<Models.UsuarioModel>(contenido.Result, _opciones);
                return usuario;
            }
            else
            {
                ViewBag.Mensaje = $"No se pueden recuperar los usuarios {respuesta.ToString()}";
                return null;
            }
        }
        #endregion
    }
}
