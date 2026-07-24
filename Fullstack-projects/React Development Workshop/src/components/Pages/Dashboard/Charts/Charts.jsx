import { LineChart, Line, CartesianGrid, XAxis, YAxis } from 'recharts';
//import { useSelector } from 'react-redux';

const Charts = () => {

  const data = [{name: 'Page A', uv: 400, pv: 2400, amt: 2400}, {name: 'Page B', uv: 400, pv: 2400, amt: 2400}, {name: 'Page C', uv: 400, pv: 2400, amt: 2400}];

  //const alimentos = useSelector((store) => store.todosSlice.alimentosCalorias);

  // const fechaActual = new Date(); //hoy

  // const fecha1 = new Date(fechaActual); //ayer
  // fecha1.setDate(fecha1.getDate() - 1);
  // const fecha2 = new Date(fechaActual); //hace 2 dias
  // fecha1.setDate(fecha1.getDate() - 2);
  // const fecha3 = new Date(fechaActual); //hace 3 dias
  // fecha1.setDate(fecha1.getDate() - 3);
  // const fecha4 = new Date(fechaActual); //hace 4 dias
  // fecha1.setDate(fecha1.getDate() - 4);
  // const fecha5 = new Date(fechaActual); //hace 5 dias
  // fecha1.setDate(fecha1.getDate() - 5);
  // const fecha6 = new Date(fechaActual); //hace 6 dias
  // fecha1.setDate(fecha1.getDate() - 6);

  // let caloriasFechaActual = 0;
  // let caloriasFecha1 = 0;
  // let caloriasFecha2 = 0;
  // let caloriasFecha3 = 0;
  // let caloriasFecha4 = 0;
  // let caloriasFecha5 = 0;
  // let caloriasFecha6 = 0;

  // const arr = alimentos.filter((arr) => new Date(arr.fecha).getDate()+1 === fechaActual.getDate());


  const renderLineChart = (
  <LineChart width={400} height={200} data={data}>
    <Line type="monotone" dataKey="uv" stroke="#8884d8" />
    <CartesianGrid stroke="#ccc" />
    <XAxis dataKey="name" />
    <YAxis />
  </LineChart>);


  return (
    <div className="container metrics">
      <h5>Graficas</h5>
      <div className="row">
        <div className="col-6">
          <div className="card">
            <div className="card-body">{renderLineChart}</div>
          </div>
        </div>
        <div className="col-6">
          <div className="card">
            <div className="card-body">{renderLineChart}</div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Charts;
