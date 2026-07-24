using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dominio
{
    public class ActividadHostal : Actividad
    //Clase hija "ActividadHostal", que toma herencia de Actividad y en donde comenzaremos a implementar sistemas de validación correspondientes.
    {
        public string _NombreResponsable { get; set; }
        public string _LugarEnHostal { get; set; }
        public bool _AlAireLibre { get; set; }

        public ActividadHostal(string nombreActividad, string descripcion, DateTime fechaActividad, int cantMaxPersonas, int edadMinima, double costo, string nombreResponsable, string lugarEnHostal, bool alAireLibre) : base(nombreActividad, descripcion, fechaActividad, cantMaxPersonas, edadMinima, costo)
        {
            _NombreResponsable = nombreResponsable;
            _LugarEnHostal = lugarEnHostal;
            _AlAireLibre = alAireLibre;
        }

        public override void EsValido() //Validaciones correspondientes con excepciones acordes a cada caso planteado.
        {
            if (String.IsNullOrEmpty(_NombreActividad) || _NombreActividad.Length > 25)
            {
                throw new Exception("Nombre no valido");
            }

            if (String.IsNullOrEmpty(_Descripcion))
            {
                throw new Exception("Descripcion no valida");
            }

            if (_FechaActividad < DateTime.Now)
            {
                throw new Exception("Fecha no valida");
            }

            if (String.IsNullOrEmpty(_NombreResponsable))
            {
                throw new Exception("Nombre no valido");
            }
        }

        public override string Lugar() 
        //Preparamos una clase abstracta que podremos overridear y utilizar en las clases hijas de "Actividad" con el fin de identificar el Lugar donde se desarrolla la Actividad.
        {
            return _LugarEnHostal;
        }

        public override string Proveedor()
        //Preparamos una clase abstracta que podremos overridear y utilizar en las clases hijas de "Actividad" con el fin de identificar el Proveedor de la Actividad.
        {
            return "Hostal";
        }

        public override double CalcularPrecio(double CostoATrabajar, Huesped H)
        //Preparamos una clase abstracta que podremos overridear y utilizar en las clases hijas de "Actividad" con el fin de calcular el precio de una Actividad en base a un Costo y un Huesped con un determinado nivel de fidelización/valor numérico.
        {
            if (H._ValorNumerico == 1)
            {
                CostoATrabajar = _Costo;
            }

            if (H._ValorNumerico == 2)
            {
                CostoATrabajar = _Costo * 0.90;
            }

            if (H._ValorNumerico == 3)
            {
                CostoATrabajar = _Costo * 0.85;
            }

            if (H._ValorNumerico == 4)
            {
                CostoATrabajar = _Costo * 0.8;
            }

            return CostoATrabajar;
        }

    }
}
