import NavBarItem from "./NavBarItem";

const NavBar = () => {
  const sections = [
    { title: "Inicio", link: "todo-list" },
    { title: "Mapa", link: "map" },
  ];

  return (
    <ul className="navbar-nav mr-auto mt-2 mt-lg-0">
      {sections.map(({ title, link }, index) => (
        <NavBarItem title={title} link={link} key={`navmenu-item-${index}`} />
      ))}
    </ul>
  );
};

export default NavBar;
