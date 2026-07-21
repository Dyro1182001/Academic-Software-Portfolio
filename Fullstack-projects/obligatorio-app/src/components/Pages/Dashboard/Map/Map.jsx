import { MapContainer, Marker, Popup, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import { useSelector } from "react-redux";



import L from "leaflet";

delete L.Icon.Default.prototype._getIconUrl;

L.Icon.Default.mergeOptions({
  iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
  iconUrl: require("leaflet/dist/images/marker-icon.png"),
  shadowUrl: require("leaflet/dist/images/marker-shadow.png"),
});


const Map = () => {

  const position = [-20.7188889, -58.06041666666666];

  const paises = useSelector((store) => store.paisesSlice.paises);
  const arrayConRegistrados = paises.map((pais) => ({
    ...pais,
    registrados: 0
  }));

  const paisesUsuarios = useSelector((store) => store.paisesSlice.paisesUsuario);

  paisesUsuarios.forEach(a => {
    arrayConRegistrados.forEach(b => {
    if(a.id === b.id){
      b.registrados=a.cantidadDeUsuarios;
    }
  });
  });
  

  return (
    <MapContainer
      center={position}
      zoom={3}
      scrollWheelZoom={true}
      style={{ height: "100vh", width: "100wh" }}
    >
      <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />
      
      {arrayConRegistrados.map((pais) => (
          <Marker position={[pais.latitude, pais.longitude]}>
          <Popup>
            {pais.name} 
            <br /> 
            Personas registradas:{pais.registrados} .
          </Popup>
          </Marker>
        ))}

    </MapContainer>
  );
};

export default Map;