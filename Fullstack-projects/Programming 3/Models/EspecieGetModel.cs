using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;
namespace WebApiClient.Models
{
    public class EspecieGetModel
    {
        public int Id { get; set; }

        public string NombreCientifico { get; set; }
        public string NombreVulgar { get; set; }
        public string Descripcion { get; set; }
        public double PesoMin { get; set; }
        public double PesoMax { get; set; }
        public double largoMin { get; set; }
        public double LargoMax { get; set; }
        public string Imagen { get; set; }
    }
}
