using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace WebApiClient.Models
{
    public class PaisModel
    {
        public int Id { get; set; } 
        public string Nombre { get; set; } 

        //public Nombre Nombre { get; set; }
        //originalmente era asi por tener Value Object, lo puse como string para testear si funca o no
        public string Codigo { get; set; } 

        //public List<Ecosistema> Ecosistema { get; set; }
    }
}
