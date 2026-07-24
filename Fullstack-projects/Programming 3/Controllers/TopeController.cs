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
    public class TopeController : Controller
    {

        private static HttpClient _clienteApi = new() { BaseAddress = new Uri(@"https://localhost:7240/api") };

        JsonSerializerOptions opciones = new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true, ///
            WriteIndented = true ///
        };

        public TopeController()
        {

            //Indica cuál es la URL con la que se debe acceder a la API por defecto.


            _clienteApi.DefaultRequestHeaders.Accept.Clear();
            _clienteApi.DefaultRequestHeaders.Accept.Add(new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue(@"application/json"));
        }

        private Uri _uriUpdateTope = new Uri(@"https://localhost:7240/api/Tope");


        // GET: TopeController/Edit/5
        public ActionResult Edit()
        {
            return View();
        }

        // POST: TopeController/Edit/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit(TopeModel topeModel)
        {
            try
            {


                // cliente.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", HttpContext.Session.GetString("token"));

                topeModel.Id = 1;
                //string json = JsonConvert.SerializeObject(topeModel);
                //HttpContent contenido = new StringContent(json, Encoding.UTF8, "application/json");
                //solicitud.Content = contenido;
                //Task<HttpResponseMessage> respuesta = cliente.SendAsync(solicitud);
                //respuesta.Wait();


                var topeSerializado = JsonSerializer.Serialize(topeModel);
                var contenidoRequest = new StringContent(topeSerializado, Encoding.UTF8, "application/json");

                var resultado = await _clienteApi.PutAsync($"{_uriUpdateTope}/{topeModel.Id}", contenidoRequest);

                if (resultado.IsSuccessStatusCode)
                {
                    ViewBag.Mensaje = "Topes modificados con exito";
                    return View(topeModel);
                }
                else
                {
                    ViewBag.Mensaje = resultado.StatusCode;
                    return View(topeModel);
                }

            }
            catch (Exception ex)
            {
                ViewBag.Mensaje = $"Error desconocido: {ex.Message}";
                return View(topeModel);
            }
        }

    }
}