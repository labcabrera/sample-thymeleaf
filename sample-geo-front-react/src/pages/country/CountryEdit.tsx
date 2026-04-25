import { useEffect, useState } from "react";
import { useParams, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import {
  Container,
  Paper,
  Box,
  Typography,
  CircularProgress,
  IconButton,
  Stack,
  Tooltip,
} from "@mui/material";
import { fetchCountry, type Country } from "../../lib/countries-api";
import AppBreadcrumbs from "../../components/AppBreadcrumbs";
import SaveIcon from "@mui/icons-material/Save";
import CancelIcon from "@mui/icons-material/Cancel";

export default function CountryEdit() {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const navigate = useNavigate();
  const auth = useAuth();
  const [country, setCountry] = useState<Country>();

  const bindCountry = (id: string) => {
    fetchCountry(id!, auth).then((response) => setCountry(response));
  };

  const onUpdate = () => {};

  useEffect(() => {
    if (location.state.country) {
      setCountry(location.state.country);
    } else if (id) {
      bindCountry(id);
    }
  }, [id, auth, country, location]);

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
          <Tooltip title="Refresh">
            <IconButton onClick={onUpdate} color="primary">
              <SaveIcon />
            </IconButton>
          </Tooltip>
          <Tooltip title="Edit">
            <IconButton
              onClick={() =>
                navigate(`/countries/view/${id}`, { state: country })
              }
              color="primary"
            >
              <CancelIcon />
            </IconButton>
          </Tooltip>
        </Stack>
      </AppBreadcrumbs>
      <Box sx={{ my: 2 }}>TODO</Box>
    </Container>
  );
}
