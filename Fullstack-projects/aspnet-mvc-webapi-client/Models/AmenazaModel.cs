using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace WebApiClient.Models
{
    public class AmenazaModel
    {
        public int Id { get; set; } 

        public string Descripcion { get; set; } 

        public int Grado { get; set; } 

        //public List<Especie> Especies { get; set; }

        //public List<Ecosistema> Ecosistemas { get; set; }

    }
}
