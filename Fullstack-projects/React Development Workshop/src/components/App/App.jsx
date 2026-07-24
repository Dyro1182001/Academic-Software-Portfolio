import "bootstrap-css-only";
import Login from "../Pages/Login";
import Layout from "../Pages/Layout";
import SignUp from "../Pages/SignUp";
import Map from "../Pages/Dashboard/Map";
import Dashboard from "../Pages/Dashboard";
import NotFound from "../Pages/NotFound";
import { Route, Routes } from "react-router-dom";
import PrivateRoute from "../Pages/PrivateRoute";

const App = () => {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route
          path="/dashboard"
          element={
            <PrivateRoute>
              <Layout />
            </PrivateRoute>
          }
        >
          <Route path="todo-list" element={<Dashboard />} />
          <Route path="map" element={<Map />} />
        </Route>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </div>
  );
};

export default App;
