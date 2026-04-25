import { useEffect, useState } from "react";
import { useParams, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import {
  Container,
  Paper,
  Box,
  Typography,
  Button,
  CircularProgress,
} from "@mui/material";
import { fetchCountry, type Country } from "../../lib/countries-api";

export default function CountryView() {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const navigate = useNavigate();
  const auth = useAuth();
  const [country, setCountry] = useState<Country>();

  useEffect(() => {
    if (location.state.country) {
      setCountry(location.state.country as Country);
    } else if (id) {
      fetchCountry(id).then((response) => setCountry(response));
    }
  }, [id, auth, country, location]);

  return (
    <Container>
      <Box sx={{ my: 2 }}>
        <Button variant="text" onClick={() => navigate(-1)} sx={{ mb: 1 }}>
          Volver
        </Button>
        <Paper sx={{ p: 2 }}>
          {!country ? (
            <Box sx={{ display: "flex", justifyContent: "center", p: 2 }}>
              <CircularProgress />
            </Box>
          ) : country ? (
            <Box>
              <Typography variant="h6">{country.name}</Typography>
              <Typography variant="body2" color="text.secondary">
                Id: {country.id}
              </Typography>
            </Box>
          ) : (
            <Typography>No se encontró el country</Typography>
          )}
        </Paper>
      </Box>
    </Container>
  );
}
