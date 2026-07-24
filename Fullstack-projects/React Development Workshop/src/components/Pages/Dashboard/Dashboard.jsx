import { useEffect } from "react";
import Charts from "./Charts";
import "./Dashboard.css";
import Main from "./Main";

import Metrics from "./Metrics/Metrics";
//import { getTodos } from "../../../services/api";
import { getAlimentosUsuario, getPaises, getPaisesUsuario, getAlimentos } from "../../../services/api";
//import { registroAlimento } from "../../../services/api";
import { useSelector, useDispatch } from "react-redux";
import { onLoadToDos, onLoadAlimentos } from "../../../app/slices/todosSlice";
import { onLoadPaises, onLoadPaisesUsuario } from "../../../app/slices/paisesSlice";


const Dashboard = () => {
  const userLogged = useSelector((store) => store.userSlice.userLogged);
  const dispatcher = useDispatch();
  
  useEffect(() => {
    if (userLogged) {
      const { id } = userLogged;
      const { apiKey } = userLogged;
      getAlimentosUsuario(apiKey, id)
        .then((res) => {
          dispatcher(onLoadToDos(res.registros));
        })
        .catch((e) => {});
      getPaises()
        .then((res) => {
          dispatcher(onLoadPaises(res.paises));
        })
        .catch((e) => {});
      getPaisesUsuario(apiKey, id)
        .then((res) => {
          dispatcher(onLoadPaisesUsuario(res.paises));
        })
        .catch((e) => {});
      getAlimentos(apiKey, id)
        .then((res) => {
          dispatcher(onLoadAlimentos(res.alimentos));
        })
        .catch((e) => {});
    }
  }, [dispatcher, userLogged]);

  return (
    <>
      <Metrics />
      <Charts />
      <h5>Alimentos</h5>
      <div className="card">
        <div className="card-body">
          <Main />
        </div>
      </div>
    </>
  );
};

export default Dashboard;
