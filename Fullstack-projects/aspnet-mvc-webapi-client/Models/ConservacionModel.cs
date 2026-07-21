using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace WebApiClient.Models
{
    public class ConservacionModel
    {
        public int Id { get; set; }

        //[StringLength(maximumLength: 50, MinimumLength = 2, ErrorMessage = "El nombre requiere de 50 caracteres como máximo y 2 como mínimo.")]
        public string Nombre { get; set; }
        public int Estado { get; set; } 

        //public List<Especie> Especies { get; set; }
        //public List<Ecosistema> Ecosistemas { get; set; }
    }
}
