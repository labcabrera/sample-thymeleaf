import { BrowserRouter, Routes, Route } from "react-router-dom";
import { ThemeProvider, CssBaseline } from "@mui/material";
import theme from "./theme";
import AuthProvider from "./auth/AuthProvider";
import Layout from "./components/Layout";
import HomePage from "./pages/HomePage";
import Login from "./pages/Login";
import CountryView from "./pages/country/CountryView";
import CountryList from "./pages/country/CountryList";
import CountryCreate from "./pages/country/CountryCreate";
import CountryEdit from "./pages/country/CountryEdit";
import ProvinceList from "./pages/province/ProvinceList";
import ProvinceCreate from "./pages/province/ProvinceCreate";
import ProvinceEdit from "./pages/province/ProvinceEdit";
import ProvinceView from "./pages/province/ProvinceView";
import Municipalities from "./pages/municipality/Municipalities";
import GeoPage from "./pages/GeoPage";
import NotFoundPage from "./pages/NotFoundPage";

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AuthProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/" element={<Layout />}>
              <Route index element={<HomePage />} />
              <Route path="geo" element={<GeoPage />} />
              <Route path="countries" element={<CountryList />} />
              <Route path="countries/view/:id" element={<CountryView />} />
              <Route path="countries/edit/:id" element={<CountryEdit />} />
              <Route path="countries/create" element={<CountryCreate />} />
              <Route path="provinces" element={<ProvinceList />} />
              <Route path="provinces/view/:id" element={<ProvinceView />} />
              <Route path="provinces/edit/:id" element={<ProvinceEdit />} />
              <Route path="provinces/create" element={<ProvinceCreate />} />
              <Route path="municipalities" element={<Municipalities />} />
              <Route path="*" element={<NotFoundPage />} />
            </Route>
          </Routes>
        </BrowserRouter>
      </AuthProvider>
    </ThemeProvider>
  );
}

export default App;
