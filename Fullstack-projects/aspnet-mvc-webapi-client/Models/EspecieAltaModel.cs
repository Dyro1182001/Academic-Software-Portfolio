using System.ComponentModel.DataAnnotations;
using System.Linq;

namespace WebApiClient.Models
{
    public class EspecieAltaModel
    {
        public int Id { get; set; }

        [Required(ErrorMessage = "El nombre no puede ser vacío.")]
        public string NombreCientifico { get; set; }
        public string NombreVulgar { get; set; }
        [Required(ErrorMessage = "La descripción no puede estar vacía.")]
        public string Descripcion { get; set; }
        [Required(ErrorMessage = "Debe ingresar un valor en el peso mínimo.")]
        public double PesoMin { get; set; }
        public double PesoMax { get; set; }
        public double largoMin { get; set; }
        public double LargoMax { get; set; }
        [Required(ErrorMessage = "Debe adjuntar una imágen.")]

        public List<AmenazaModel> Amenazas { get; set; }
        public ConservacionModel? Conservacion { get; set; }
        public List<EcosistemaIdModel> Ecosistemas { get; set; }

        public string Imagen { get; set; } = "";

        public void AgregarAmenaza(AmenazaModel amenaza)
        {
            if (amenaza == null)
                throw new Exception("La amenaza no puede ser nula");
            if (Amenazas == null)
                Amenazas = new List<AmenazaModel>();
            if (!Amenazas.Contains(amenaza))
                Amenazas.Add(amenaza);
        }

        public void AgregarEcosistema(EcosistemaIdModel ecosistema)
        {
            if (ecosistema == null)
                throw new Exception("La amenaza no puede ser nula");
            if (Ecosistemas == null)
                Ecosistemas = new List<EcosistemaIdModel>();
            if (!Ecosistemas.Contains(ecosistema))
                Ecosistemas.Add(ecosistema);
        }
    }
}
