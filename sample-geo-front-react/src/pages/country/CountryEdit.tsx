import { useEffect, useState } from "react";
import { useParams, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import { Container, Box, CircularProgress, Stack } from "@mui/material";
import { fetchCountry, type Country } from "../../lib/countries-api";
import AppBreadcrumbs from "../../components/AppBreadcrumbs";
import CountryForm from "./CountryForm";
import SaveButton from "../../components/buttons/SaveButton";
import CancelButton from "../../components/buttons/CancelButton";

export default function CountryEdit() {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const navigate = useNavigate();
  const auth = useAuth();
  const [country, setCountry] = useState<Country>();
  const [formData, setFormData] = useState<Country>({} as Country);

  const bindCountry = (id: string) => {
    fetchCountry(id!, auth).then((response) => setCountry(response));
  };

  const onUpdate = () => {};

  useEffect(() => {
    console.log("use e 1");
    setFormData(country);
  }, [country]);

  useEffect(() => {
    console.log("use e 0");
    if (location.state.country) {
      setCountry(location.state.country);
    } else if (id) {
      bindCountry(id);
    }
  }, [id, auth, location]);

  return (
    <Container>
      <AppBreadcrumbs
        items={[
          { label: "Home", href: "/" },
          { label: "Geo", href: "/geo" },
          { label: "Countries", href: "/countries" },
          { label: "Edit" },
        ]}
      >
        <Stack direction="row">
          <SaveButton onClick={onUpdate} />
          <CancelButton
            onClick={() =>
              navigate(`/countries/view/${id}`, { state: country })
            }
          />
        </Stack>
      </AppBreadcrumbs>
      {!formData ? (
        <CircularProgress />
      ) : (
        <Box sx={{ my: 2 }}>
          <CountryForm
            formData={formData}
            setFormData={setFormData}
            create={false}
          />
        </Box>
      )}
    </Container>
  );
}
