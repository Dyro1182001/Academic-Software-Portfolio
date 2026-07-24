using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace WebApiClient.Models
{
    public class EcosistemaModel
    {
        public int Id { get; set; }

        [Required(ErrorMessage = "El nombre no puede ser vacío.")]

        //[StringLength(maximumLength: 50, MinimumLength = 2, ErrorMessage = "El nombre requiere de 50 caracteres como máximo y 2 como mínimo.")]
        public string Nombre { get; set; } 
        public double UbicacionLat { get; set; } 
        public double UbicacionLon { get; set; } 
        public double Area { get; set; } 

        [Required(ErrorMessage = "La descripción no puede estar vacía.")]
        //[StringLength(maximumLength: 500, MinimumLength = 50, ErrorMessage = "La descripcion requiere de 500 caracteres como máximo y 50 como mínimo.")]

        public string Descripcion { get; set; } 


        //[InverseProperty("Ecosistemas")]
        //public List<Especie> Especies { get; set; }


        //[InverseProperty("MisHogares")]
        //public List<Especie> MisEspecies { get; set; }

        public List<AmenazaModel> Amenazas { get; set; } 

        public ConservacionModel? Conservacion { get; set; }
        public PaisModel Pais { get; set; }

        [Required(ErrorMessage = "Debe adjuntar una imágen.")]

        public string Imagen { get; set; } 

    }
}
