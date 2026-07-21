import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";

const PrivateRoute = ({ children }) => {
  const userLogged = useSelector((store) => store.userSlice.userLogged);
  if (!userLogged) {
    return <Navigate to={"/login"} replace={true} />;
  }

  return children;
};

export default PrivateRoute;
