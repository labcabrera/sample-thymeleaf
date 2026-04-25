import { useEffect, useState } from "react";
import { useParams, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import {
  Container,
  Paper,
  Box,
  Typography,
  CircularProgress,
  Stack,
} from "@mui/material";
import ConfirmDeleteDialog from "../../components/ConfirmDeleteDialog";
import {
  fetchCountry,
  deleteCountry,
  type Country,
} from "../../lib/countries-api";
import AppBreadcrumbs from "../../components/AppBreadcrumbs";
import DeleteButon from "../../components/buttons/DeleteButton";
import EditButton from "../../components/buttons/EditButton";
import RefreshButton from "../../components/buttons/RefreshButton";

export default function CountryView() {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const navigate = useNavigate();
  const auth = useAuth();
  const [country, setCountry] = useState<Country>();
  const [deleteDialogOpen, setDeleteDialogOpen] = useState<boolean>(false);

  const onDelete = async () => {
    if (!country?.id) return;
    deleteCountry(country.id, auth)
      .then(() => navigate("/countries"))
      .catch((err) => console.log("error deleting country", err));
  };

  const bindCountry = (id: string) => {
    fetchCountry(id!, auth).then((response) => setCountry(response));
  };

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
          { label: "View" },
        ]}
      >
        <Stack direction="row">
          <RefreshButton onClick={() => bindCountry(id!)} />
          <EditButton
            onClick={() =>
              navigate(`/countries/edit/${id}`, { state: country })
            }
          />
          <DeleteButon onClick={() => setDeleteDialogOpen(true)} />
        </Stack>
      </AppBreadcrumbs>
      <Box sx={{ my: 2 }}>
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
      <ConfirmDeleteDialog
        open={deleteDialogOpen}
        onClose={() => setDeleteDialogOpen(false)}
        onConfirm={onDelete}
      />
    </Container>
  );
}
