using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dominio
{
    public class Proveedor : IComparable<Proveedor>
    //Definimos la clase proveedor, que posteriormente validaremos y a la que le asignaremos un método ToString(). 
    {
        private static int _UltimoId = 1;

        public int _Id { get; set; }
        public string _NombreProveedor { get; set; }
        public string _NumTelefono { get; set; }
        public string _Direccion { get; set; }
        public int _Descuento { get; set; }

        public Proveedor()
        {
            _Id = _UltimoId;
            _UltimoId++;
        }

        public Proveedor(string nombreProveedor, string numTelefono, string direccion, int descuento)
        {
            _Id = _UltimoId;
            _UltimoId++;
            _NombreProveedor = nombreProveedor;
            _NumTelefono = numTelefono;
            _Direccion = direccion;
            _Descuento = descuento;
        }


        public void EsValido() //Validaciones correspondientes con excepciones acordes a cada caso planteado.
        {
            if (String.IsNullOrEmpty(_NombreProveedor))
            {
                throw new Exception("Nombre no valido");
            }

            if (String.IsNullOrEmpty(_NumTelefono))
            {
                throw new Exception("Número de Teléfono no valido");
            }

            if (String.IsNullOrEmpty(_Direccion))
            {
                throw new Exception("Direccion no valida");
            }
        }

        public override string ToString()
        //Método ToString() para asegurarnos de que al ser llamado, los proveedores devuelvan los datos que se piden.
        {
            return $"Id: {_Id}, Nombre: {_NombreProveedor}, Número de Teléfono: {_NumTelefono}, Dirección: {_Direccion}, Descuento: {_Descuento}%";

        }

        public int CompareTo(Proveedor? other)
        //Método que nos permite ordenar la lista Proveedores en orden alfabetico posteriormente.
        {
            if (_NombreProveedor.CompareTo(other._NombreProveedor) > 0)
            {
                return 1;
            }
            else if (_NombreProveedor.CompareTo(other._NombreProveedor) < 0)
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
    }
}
