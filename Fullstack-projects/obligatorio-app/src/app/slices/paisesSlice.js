import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    paises: [],
    paisesUsuario: [],
};

export const paisesSlice = createSlice({
  name: "paisesSlice",
  initialState: initialState,
  reducers: {
    onLoadPaises: (state, action) => {
        const { payload } = action;
        state.paises = payload;
    },
    onLoadPaisesUsuario: (state, action) => {
        const { payload } = action;
        state.paisesUsuario = payload;
    },
  },
});

export const { onLoadPaises, onLoadPaisesUsuario } = paisesSlice.actions;
export default paisesSlice.reducer;