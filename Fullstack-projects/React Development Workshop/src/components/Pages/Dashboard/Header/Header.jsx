/* eslint-disable jsx-a11y/anchor-is-valid */
import React from "react";
import logo from "../../../logo.svg";
import "./Header.css";
import NavBar from "./NavBar";
import Profile from "./Logout";
import { useDispatch } from "react-redux";
import { onLogout } from "../../../../app/slices/userSlice";

const Header = () => {
  const dispatcher = useDispatch();

  const _onLogOut = () => {
    dispatcher(onLogout());
  };

  return (
    <header className="App-header">
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <button
          className="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarTogglerDemo01"
          aria-controls="navbarTogglerDemo01"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarTogglerDemo01">
          <a className="navbar-brand" href="#">
            <img
              src={logo}
              width="30"
              height="30"
              className="d-inline-block align-top"
              alt=""
            />
            Dashboard
          </a>
          <NavBar />
          <Profile onLogOut={_onLogOut} />
        </div>
      </nav>
    </header>
  );
};

export default Header;
