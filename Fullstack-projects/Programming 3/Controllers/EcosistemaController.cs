using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Hosting;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using WebApiClient.Models;

namespace WebApiClient.Controllers
{
    public class EcosistemaController : Controller
    {
        
        private HttpClient _clienteApi = new HttpClient();
        private JsonSerializerOptions _opciones = new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true
        };

        private IWebHostEnvironment _environment;

        public EcosistemaController(IWebHostEnvironment environment)
        {
            this._environment = environment;
            _clienteApi.BaseAddress = new Uri(@"https://localhost:7240/api");
            _clienteApi.DefaultRequestHeaders.Accept.Clear();
            _clienteApi.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue(@"application/json"));
        }

        private Uri _uriGetEcosistemas = new Uri(@"https://localhost:7240/api/Ecosistema");
        private Uri _uriGetEcosistemaById = new Uri(@"https://localhost:7240/api/Ecosistema/Ecosistema/");
        private Uri _uriDeleteEcosistema = new Uri(@"https://localhost:7240/api/Ecosistema");
        private Uri _uriRegistroEcosistemas = new Uri(@"https://localhost:7240/Ecosistema/Registro");


        public async Task<ActionResult> Index()
        {
            try
            {
                var respuesta = await _clienteApi.GetAsync(_uriGetEcosistemas);
                if (respuesta.IsSuccessStatusCode)
                {
                    var contenido = await respuesta.Content.ReadAsStringAsync();
                    var usuarios = JsonSerializer.Deserialize<IEnumerable<Models.EcosistemaModel>>(contenido, _opciones);
                    return View(usuarios);
                }
                else
                {
                    ViewBag.Mensaje = $"No se pueden recuperar los ecosistemas {respuesta.StatusCode}";
                    return View();
                }
            }
            catch (Exception ex)
            {
                ViewBag.Mensaje = $"Error desconocido: {ex.Message}";
                return View();
            }
        }

        // GET: EcosistemaController/Create
        public ActionResult Create()
        {
            var ecosistemaModel = new EcosistemaAltaModel();
            CargarListasDesplegables(ecosistemaModel);
            return View(ecosistemaModel);
        }

        // POST: EcosistemaController/Create
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create(EcosistemaAltaModel ecosistemaModel,int[] amenazas, IFormFile foto)
        {
            try
            {
                if (ecosistemaModel == null)
                {
                    return View();
                }
                

                CargarListasDesplegables(ecosistemaModel);
                GuardarImagen(foto, ecosistemaModel);
                AsignarAmenazas(ecosistemaModel,amenazas);

                ecosistemaModel.Pais.Codigo = "";
                ecosistemaModel.Pais.Nombre = "";
                ecosistemaModel.Conservacion.Nombre = "";
                ecosistemaModel.Conservacion.Estado = 0;
                ecosistemaModel.MisEspecies = new List<EspecieIdModel>();

                var ecoSerializado = JsonSerializer.Serialize(ecosistemaModel);
                var contenidoRequest = new StringContent(ecoSerializado, Encoding.UTF8, "application/json");
                var resultado = _clienteApi.PostAsync($"{_uriRegistroEcosistemas}", contenidoRequest).Result;

                if (resultado.IsSuccessStatusCode)
                {
                    ViewBag.Mensaje = "Ecosistema registrado con éxito";
                    return RedirectToAction("Index");
                }
                else
                {
                    ViewBag.Mensaje = resultado.StatusCode.ToString();
                    return View(ecosistemaModel);
                }
            }
            catch (Exception ex)
            {
                ViewBag.Mensaje = $"Error desconocido: {ex.Message}";
                return View(ecosistemaModel);
            }
        }

        // GET: EcosistemaController/Delete/5
        public ActionResult Delete(int id)
        {
            var ecosistema = GetEcosistema(id);

            return View(ecosistema);
        }

        // POST: UsuarioController/Delete/5
        [HttpPost]
        //[ValidateAntiForgeryToken]
        public ActionResult Delete(int id, EcosistemaModel ecosistema, bool IsChecked)
        {
            if (IsChecked)
            {
                try
                {
                    var respuesta = _clienteApi.DeleteAsync($"{_uriDeleteEcosistema}/{ecosistema.Id}");
                    if (((int)respuesta.Result.StatusCode) == StatusCodes.Status204NoContent)
                    {
                        return RedirectToAction(nameof(Index));
                    }

                    ViewBag.Error = $"No fue posible eliminar el especie {id}";

                    return View(ecosistema);
                }
                catch (Exception e)
                {
                    ViewBag.Error = $"Error desconocido: {e.Message}";
                    return View(ecosistema);
                }
            }
            else
            {
                ViewBag.Error = "Debe marcar la casilla";
                return View(ecosistema);
            }

        }

        #region Métodos auxiliares
        private void CargarListasDesplegables(EcosistemaAltaModel ecosistemaModel)
        {
            try
            {
                IEnumerable<AmenazaModel> listaAmenazas = new List<AmenazaModel>();
                IEnumerable<ConservacionModel> listaConservaciones = new List<ConservacionModel>();
                IEnumerable<PaisModel> listaPaises = new List<PaisModel>();

                var respuestaAmenazas = _clienteApi.GetAsync(new Uri(@"https://localhost:7240/api/Amenaza")).Result;
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

                var respuestaPaises = _clienteApi.GetAsync(new Uri(@"https://localhost:7240/api/Pais")).Result;
                if (respuestaPaises.IsSuccessStatusCode)
                {
                    string contenidoPaises = respuestaPaises.Content.ReadAsStringAsync().Result;
                    listaPaises = JsonSerializer.Deserialize<IEnumerable<PaisModel>>(contenidoPaises, _opciones);
                }

                // Asegúrate de que las listas no sean nulas antes de asignarlas al modelo
                ViewBag.Amenazas = listaAmenazas;
                ViewBag.Paises = listaPaises;
                ViewBag.Conservaciones = listaConservaciones;
            }
            catch (Exception ex)
            {
                ViewBag.Mensaje = $"Error al cargar listas desplegables: {ex.Message}";
                throw;
            }
        }

        private EcosistemaModel GetEcosistema(int id)
        {
            var respuesta = _clienteApi.GetAsync(_uriGetEcosistemaById + $"{id}").Result;
            if (respuesta.IsSuccessStatusCode)
            {
                var contenido = respuesta.Content.ReadAsStringAsync();
                var ecosistema = JsonSerializer.Deserialize<Models.EcosistemaModel>(contenido.Result, _opciones);
                return ecosistema;
            }
            else
            {
                ViewBag.Mensaje = $"No se pueden recuperar las especies {respuesta.ToString()}";
                return null;
            }
        }

        private bool GuardarImagen(IFormFile imagen, EcosistemaAltaModel e)
        {
            if (imagen == null || e == null)
                return false;

            string rutaFisicaWwwRoot = _environment.WebRootPath;
            string extension = Path.GetExtension(imagen.FileName);
            string nombreImagen = e.Nombre.ToString() + "_001" + extension;
            string rutaFisicaFoto = Path.Combine(rutaFisicaWwwRoot, "img", "Ecosistema", nombreImagen);

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

        private void AsignarAmenazas(EcosistemaAltaModel E, int[] Amenazas)
        {
            foreach (int idAmenaza in Amenazas)
            {
                E.AgregarAmenaza(new AmenazaModel { Id = idAmenaza, Descripcion = "", Grado = 0 });
            }

        }


        #endregion
    }
}
