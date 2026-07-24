using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Dominio
{
    public class Agenda : IComparable<Agenda>
    //Clase agenda que en esta etapa del obligatorio sólo funcionará como parte del diseño general, con más bien poca o nula funcionalidad para esta primera entrega.

    {

        public int _Id { get; set; }

        private static int _UltimoId = 1;
        public DateTime _FechaCreacion { get; set; }
        public string _EstadoAgenda { get; set; }
        public Huesped _Huesped { get; set; }
        public Actividad _Actividad { get; set; }

        public double _CostoFinal = 0;

        public Agenda()
        {
            _Id = _UltimoId;
            _UltimoId++;
        }

        public Agenda(string estadoAgenda, Huesped huesped, Actividad actividad, DateTime fechaCreacion)
        {
            _Id = _UltimoId;
            _UltimoId++;
            _FechaCreacion = fechaCreacion;
            _EstadoAgenda = estadoAgenda;
            _Huesped = huesped;
            _Actividad = actividad;
            if(_CostoFinal==0)
            {
                _CostoFinal=CalcularCostoFinal();
            }
        }
        public void EsValido() //Validación correspondiente con excepciones acorde al caso planteado.
        {
            if (DateTime.Now.Year - _Huesped._FechaNacimiento.Year < _Actividad._EdadMinima)
            {
                throw new Exception("El Huesped no tiene la Edad necesaria para participar en la Actividad");
            }
        }

        public double CalcularCostoFinal()  //Método que nos permitirá definir el costo final.
        {
            return _Actividad.CalcularPrecio(_CostoFinal, _Huesped);
        }

        public int CompareTo(Agenda? other)
        //Método que nos permite ordenar la lista de agendas ascendentemente por fecha.
        {
            if (_Actividad._FechaActividad.CompareTo(other._Actividad._FechaActividad) < 0)
            {
                return 1;
            }
            else if (_Actividad._FechaActividad.CompareTo(other._Actividad._FechaActividad) > 0)
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
