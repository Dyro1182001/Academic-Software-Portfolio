using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Identity.Client;
using System;
using System.Text;
using System.Text.Json;
using WebApiClient.Models;

namespace WebApiClient.Controllers
{
    public class EspecieController : Controller
    {
        // GET: EspecieController

        private HttpClient _clienteApi = new HttpClient();

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

        private IWebHostEnvironment _environment;

        public EspecieController(IWebHostEnvironment environment)
        {

            //Indica cuál es la URL con la que se debe acceder a la API por defecto.
            this._environment = environment;
            _clienteApi.BaseAddress = new Uri(@"https://localhost:7240/api");
            _clienteApi.DefaultRequestHeaders.Accept.Clear();
            _clienteApi.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue(@"application/json"));
        }

        private Uri _uriGetEspecies = new Uri(@"https://localhost:7240/api/Especie");
        private Uri _uriEspecieDelete = new Uri(@"https://localhost:7240/api/Especie");
        private Uri _uriGetEspecieById = new Uri(@"https://localhost:7240/api/Especie/Especie/");
        private Uri _uriRegistroEspecie = new Uri(@"https://localhost:7240/Especie/Registro");


        public ActionResult Index()
        {
            //Obtener todas las especies a través de la Web API.

            var respuesta = _clienteApi.GetAsync(_uriGetEspecies).Result;
            if (respuesta.IsSuccessStatusCode)
            {
                var contenido = respuesta.Content.ReadAsStringAsync();
                var especies = JsonSerializer.Deserialize<IEnumerable<Models.EspecieGetModel>>(contenido.Result, _opciones);
                return View(especies);
            }
            else
            {
                ViewBag.Mensaje = $"No se pueden recuperar los usuarios {respuesta.ToString()}";
                return View();
            }
        }

        // GET: EspecieController/Create
        public ActionResult Create()
        {
            CargarListasDesplegables();
            return View();
        }

        private void CargarListasDesplegables()
        {
            try
            {
                IEnumerable<AmenazaModel> listaAmenazas = new List<AmenazaModel>();
                IEnumerable<ConservacionModel> listaConservaciones = new List<ConservacionModel>();
                IEnumerable<EcosistemaModel> listaEcosistemas = new List<EcosistemaModel>();

                var respuestaAmenazas = _clienteApi.GetAsync(new Uri(@"https://localhost:7240/api/Amenaza")).Result;
                Console.WriteLine($"Respuesta Amenazas: {respuestaAmenazas}");
                ViewBag.RespuestaAmenazas = respuestaAmenazas.Content.ReadAsStringAsync().Result;
                if (respuestaAmenazas.IsSuccessStatusCode)
                {
                    string contenidoAmenazas = respuestaAmenazas.Content.ReadAsStringAsync().Result;
                    listaAmenazas = JsonSerializer.Deserialize<IEnumerable<AmenazaModel>>(contenidoAmenazas, _opciones);
                }

                var respuestaConservaciones = _clienteApi.GetAsync(new Uri(@"https://localhost:7240/api/Conservacion")).Result;
                if (respuestaConservaciones.IsSuccessStatusCode)
                {
                    string contenidoConservaciones = respuestaConservaciones.Content.ReadAsStringAsync().Result;
                    listaConservaciones = JsonSerializer.Deserialize<IEnumerable<ConservacionModel>>(contenidoConservaciones, _opciones);
                }

                var respuestaEcosistemas = _clienteApi.GetAsync(new Uri(@"https://localhost:7240/api/Ecosistema")).Result;
                if (respuestaEcosistemas.IsSuccessStatusCode)
                {
                    string contenidoEcosistemas = respuestaEcosistemas.Content.ReadAsStringAsync().Result;
                    listaEcosistemas = JsonSerializer.Deserialize<IEnumerable<EcosistemaModel>>(contenidoEcosistemas, _opciones);
                }

                // Asegúrate de que las listas no sean nulas antes de asignarlas al modelo
                ViewBag.Amenazas = listaAmenazas;
                ViewBag.Ecosistemas = listaEcosistemas;
                ViewBag.Conservaciones = listaConservaciones;
            }
            catch (Exception ex)
            {
                ViewBag.Mensaje = $"Error al cargar listas desplegables: {ex.Message}";
                throw;
            }
        }

        // POST: EcosistemaController/Create
        [HttpPost]
        public ActionResult Create(EspecieAltaModel especieModel, IFormFile foto, int[] amenazas, int[] ecosistemas)
        {
            try
            {
                if (especieModel == null)
                {
                    return View();
                }

                CargarListasDesplegables();
                GuardarImagen(foto, especieModel);
                AsignarAmenazas(especieModel, amenazas);
                AsignarEcosistemas(especieModel, ecosistemas);
                especieModel.Conservacion.Estado = 0;
                especieModel.Conservacion.Nombre = "";


                var espSerializado = JsonSerializer.Serialize(especieModel);
                var contenidoRequest = new StringContent(espSerializado, Encoding.UTF8, "application/json");
                var resultado = _clienteApi.PostAsync($"{_uriRegistroEspecie}", contenidoRequest).Result;

                Console.WriteLine($"Respuesta Amenazas: {resultado}");
                ViewBag.RespuestaEspecies = resultado.Content.ReadAsStringAsync().Result;

                if (resultado.IsSuccessStatusCode)
                {
                    ViewBag.Mensaje = "Especie registrado con éxito";
                    CargarListasDesplegables();
                    return RedirectToAction("Index");
                }
                else
                {
                    ViewBag.Mensaje = resultado.StatusCode.ToString();
                    CargarListasDesplegables();
                    return View(especieModel);
                }
            }
            catch (Exception ex)
            {
                ViewBag.Mensaje = $"Error desconocido: {ex.Message}";
                CargarListasDesplegables();
                return View(especieModel);
            }


        }

        // GET: EspecieController/Delete/5
        public ActionResult Delete(int id)
        {
            var especie = GetEspecie(id);

            return View(especie);
        }

        // POST: UsuarioController/Delete/5
        [HttpPost]
        //[ValidateAntiForgeryToken]
        public ActionResult Delete(int id, EspecieGetModel especie, bool IsChecked)
        {
            if (IsChecked)
            {
                try
                {
                    var respuesta = _clienteApi.DeleteAsync($"{_uriEspecieDelete}/{especie.Id}");
                    if (((int)respuesta.Result.StatusCode) == StatusCodes.Status204NoContent)
                    {
                        return RedirectToAction(nameof(Index));
                    }

                    ViewBag.Error = $"No fue posible eliminar el especie {id}";

                    return View(especie);
                }
                catch (Exception e)
                {
                    ViewBag.Error = $"Error desconocido: {e.Message}";
                    return View(especie);
                }
            }
            else
            {
                ViewBag.Error = "Debe marcar la casilla";
                return View(especie);
            }

        }


        #region Metodos auxiliares
        private EspecieGetModel GetEspecie(int id)
        {
            var respuesta = _clienteApi.GetAsync(_uriGetEspecieById + $"{id}").Result;
            if (respuesta.IsSuccessStatusCode)
            {
                var contenido = respuesta.Content.ReadAsStringAsync();
                var especie = JsonSerializer.Deserialize<Models.EspecieGetModel>(contenido.Result, _opciones);
                return especie;
            }
            else 
            {
                ViewBag.Mensaje = $"No se pueden recuperar las especies {respuesta.ToString()}";
                return null;
            }
        }

        private bool GuardarImagen(IFormFile imagen, EspecieAltaModel e)
        {
            if (imagen == null || e == null)
                return false;

            string rutaFisicaWwwRoot = _environment.WebRootPath;
            string extension = Path.GetExtension(imagen.FileName);
            string nombreImagen = e.NombreVulgar.ToString() + "_001" + extension;
            string rutaFisicaFoto = Path.Combine(rutaFisicaWwwRoot, "img", "Especie", nombreImagen);

            try
            {
                using (FileStream f = new FileStream(rutaFisicaFoto, FileMode.Create))
                {
                    imagen.CopyTo(f);

                }
                e.Imagen = nombreImagen;
                return true;
            }
            catch (Exception ex)
            {
                return ViewBag.msg = ex.Message; ;
            }

        }

        private void AsignarAmenazas(EspecieAltaModel E, int[] Amenazas)
        {
            foreach (int idAmenaza in Amenazas)
            {
                E.AgregarAmenaza(new AmenazaModel { Id = idAmenaza, Descripcion = "", Grado = 0 });
            }

        }


        private void AsignarEcosistemas(EspecieAltaModel E, int[] Especies)
        {
            foreach (int idEspecie in Especies)
            {
                E.AgregarEcosistema(new EcosistemaIdModel { Id = idEspecie });

            }

        }
        #endregion







        public ActionResult Asignar()
        {
            CargarListasAsignacion();
            return View();
        }


        [HttpPost]
        public ActionResult Asignar(int EcosistemasSelect, int EspeciesSelect)
        {
            try
            {
                // Realizar la llamada a la API para asignar la especie al ecosistema
                var client = new HttpClient();
                var response = client.PostAsync($"https://localhost:7240/api/Especie/Asignacion{EcosistemasSelect}&EspeciesSelect={EspeciesSelect}", null).Result;

                if (response.IsSuccessStatusCode)
                {
                    ViewBag.msg = "Asociación exitosa.";
                }
                else
                {
                    ViewBag.msg = $"Error al realizar la asignación: {response.StatusCode}";
                }

                // Recargar las listas y mostrar la vista
                CargarListasAsignacion();
                return View();
            }
            catch (Exception ex)
            {
                ViewBag.msg = $"Error al realizar la asignación: {ex.Message}";
                CargarListasAsignacion();
                return View();
            }
        }







        private void CargarListasAsignacion()
        {
            try
            {
                IEnumerable<EspecieGetModel> listaEspecies = new List<EspecieGetModel>();
                IEnumerable<EcosistemaModel> listaEcosistemas = new List<EcosistemaModel>();

                var respuestaEspecies = _clienteApi.GetAsync(new Uri(@"https://localhost:7240/api/Especie")).Result;
                if (respuestaEspecies.IsSuccessStatusCode)
                {
                    string contenidoEspecies = respuestaEspecies.Content.ReadAsStringAsync().Result;
                    listaEspecies = JsonSerializer.Deserialize<IEnumerable<EspecieGetModel>>(contenidoEspecies, _opciones);
                }

                var respuestaEcosistemas = _clienteApi.GetAsync(new Uri(@"https://localhost:7240/api/Ecosistema")).Result;
                if (respuestaEcosistemas.IsSuccessStatusCode)
                {
                    string contenidoEcosistemas = respuestaEcosistemas.Content.ReadAsStringAsync().Result;
                    listaEcosistemas = JsonSerializer.Deserialize<IEnumerable<EcosistemaModel>>(contenidoEcosistemas, _opciones);
                }

                // Asegúrate de que las listas no sean nulas antes de asignarlas al modelo
                ViewBag.Ecosistemas = listaEcosistemas;
                ViewBag.Especies = listaEspecies;
            }
            catch (Exception ex)
            {
                ViewBag.Mensaje = $"Error al cargar listas desplegables: {ex.Message}";
                throw;
            }
        }
    }
}
