const getFromLocalStorage = (key) => {
  const session = localStorage.getItem(key);
  return session ? JSON.parse(session) : null;
};

const setToLocalStorage = (key, value) => {
  localStorage.setItem(key, JSON.stringify(value));
};

const removeFromLocalStorage = (key) => {
  localStorage.removeItem(key);
};

export { removeFromLocalStorage, getFromLocalStorage, setToLocalStorage };
