/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable react-hooks/set-state-in-effect */
import { useEffect, useState } from "react";
import { useParams, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import {
  Container,
  Paper,
  Box,
  CircularProgress,
  Stack,
  Grid,
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
import LabelValueInfo from "../../components/LabelValueInfo";

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

  const formatDate = (date?: string | Date | null): string | null => {
    if (!date) return null;
    const dt = typeof date === "string" ? new Date(date) : date;
    if (Number.isNaN(dt.getTime())) return null;
    return dt.toISOString().split(".")[0];
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
          ) : (
            <Grid container spacing={1}>
              <LabelValueInfo label="Id" value={country.id} />
              <LabelValueInfo label="Name" value={country.name} />
              <LabelValueInfo
                label="Created"
                value={formatDate(country.createdAt)}
              />
              <LabelValueInfo
                label="Updated"
                value={formatDate(country.updatedAt)}
              />
            </Grid>
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
