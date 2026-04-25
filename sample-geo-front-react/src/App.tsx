import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ThemeProvider, CssBaseline, createTheme } from "@mui/material";
import AuthProvider from "./auth/AuthProvider";
import Layout from "./components/Layout";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Countries from "./pages/country/Countries";
import Provinces from "./pages/province/Provinces";
import Municipalities from "./pages/municipality/Municipalities";

const theme = createTheme();

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AuthProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/" element={<Layout />}>
              <Route index element={<Home />} />
              <Route path="countries" element={<Countries />} />
              <Route path="provinces" element={<Provinces />} />
              <Route path="municipalities" element={<Municipalities />} />
            </Route>
          </Routes>
        </BrowserRouter>
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App;
