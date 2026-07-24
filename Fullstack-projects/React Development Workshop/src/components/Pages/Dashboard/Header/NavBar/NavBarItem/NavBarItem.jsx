import "./NavBarItem.css";
import { Link } from "react-router-dom";

const NavBarItem = ({ title, link }) => {
  return (
    <li className="nav-item">
      <Link className="nav-link" to={link}>
        {title}
      </Link>
    </li>
  );
};

export default NavBarItem;
