using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Security.Cryptography;
using System.Text;

namespace WebApiClient.Models
{
    public class UsuarioModel
    {
        public int Id { get; set; }
        [Required(ErrorMessage = "El alias no puede estar vacío.")]

        [MinLength(6, ErrorMessage = "El alias debe estar compuesto de un mínimo de 6 caracteres.")]
        public string Alias { get; set; }

        [Required(ErrorMessage = "La contraseña no puede estar vacía.")]

        [MinLength(8, ErrorMessage = "El largo de la contraseña debe ser de un mínimo de 8 caracteres.")]
        public string Contrasenia { get; set; }
        public string ContraEncriptada { get; set; }

        [Required(ErrorMessage = "El rol no puede estar vacío.")]
        public string Rol { get; set; }
        public DateTime FechaRegistro { get; set; }

        [NotMapped]
        public string Token { get; set; }

        //public void setFechaRegistro()
        //{
        //    FechaRegistro = DateTime.Now;
        //}

        //private static string EncriptarPass(string password)
        //{
        //    using (var sha256 = SHA256.Create())
        //    {
        //        byte[] passwordBytes = Encoding.UTF8.GetBytes(password);
        //        byte[] hashedBytes = sha256.ComputeHash(passwordBytes);

        //        string passEncriptada = BitConverter.ToString(hashedBytes).Replace("-", "").ToLower();

        //        return passEncriptada;
        //    }
        //}

        //public void setPassEncriptada()
        //{
        //    ContraEncriptada = EncriptarPass(Contrasenia);
        //}
    }
}
