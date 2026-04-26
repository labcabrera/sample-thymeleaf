/* eslint-disable react-hooks/set-state-in-effect */
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import { Container, IconButton, Paper } from "@mui/material";
import { createCountry, type Country } from "../../lib/countries-api";
import SaveIcon from "@mui/icons-material/Save";
import AppBreadcrumbs from "../../components/AppBreadcrumbs";
import CountryForm from "./CountryForm";

export default function CountryCreate() {
  const navigate = useNavigate();
  const auth = useAuth();
  const [formData, setFormData] = useState<Country>({} as Country);
  const [isValidForm, setIsValidForm] = useState<boolean>(false);

  const onCreate = () => {
    createCountry(formData, auth).then((response) =>
      navigate(`/countries/view/${response.id}`, {
        state: { country: response },
      }),
    );
  };

  const validate = (formData: Country) => {
    if (!formData) return false;
    if (!formData.id) return false;
    if (!formData.name) return false;
    return true;
  };

  useEffect(() => {
    setIsValidForm(validate(formData));
  }, [formData, setIsValidForm]);

  return (
    <Container>
      <AppBreadcrumbs
        items={[
          { label: "Home", href: "/" },
          { label: "Geo", href: "/geo" },
          { label: "Countries", href: "/countries" },
          { label: "Creation" },
        ]}
      >
        <IconButton onClick={onCreate} color="primary" disabled={!isValidForm}>
          <SaveIcon />
        </IconButton>
      </AppBreadcrumbs>
      <Paper elevation={5} sx={{ p: 2, mt: 2 }}>
        <CountryForm formData={formData} setFormData={setFormData} create />
      </Paper>
    </Container>
  );
}
