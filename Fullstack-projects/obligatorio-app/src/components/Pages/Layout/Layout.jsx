import Header from "../Dashboard/Header";
import { Outlet } from "react-router-dom";

const Layout = () => {
  return (
    <div className="container-fluid dashboard">
      <Header />
      <div className="col-9 mx-auto">
        <Outlet />
      </div>
    </div>
  );
};

export default Layout;
