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
  fetchMunicipality,
  deleteMunicipality,
  type Municipality,
} from "../../lib/municipalities-api";
import AppBreadcrumbs from "../../components/AppBreadcrumbs";
import DeleteButon from "../../components/buttons/DeleteButton";
import EditButton from "../../components/buttons/EditButton";
import RefreshButton from "../../components/buttons/RefreshButton";
import LabelValueInfo from "../../components/LabelValueInfo";

export default function MunicipalityView() {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const navigate = useNavigate();
  const auth = useAuth();
  const [municipality, setMunicipality] = useState<Municipality>();
  const [deleteDialogOpen, setDeleteDialogOpen] = useState<boolean>(false);

  const onDelete = async () => {
    if (!municipality?.id) return;
    deleteMunicipality(municipality.id, auth)
      .then(() => navigate("/municipalities"))
      .catch((err) => console.log("error deleting municipality", err));
  };

  const bindMunicipality = (id: string) => {
    fetchMunicipality(id!, auth).then((response) => setMunicipality(response));
  };

  const formatDate = (date?: string | Date | null): string | null => {
    if (!date) return null;
    const dt = typeof date === "string" ? new Date(date) : date;
    if (Number.isNaN(dt.getTime())) return null;
    return dt.toISOString().split(".")[0];
  };

  useEffect(() => {
    if (location.state?.municipality) {
      setMunicipality(location.state.municipality);
    } else if (id) {
      bindMunicipality(id);
    }
  }, [id, auth, municipality, location]);

  return (
    <Container>
      <AppBreadcrumbs
        items={[
          { label: "Home", href: "/" },
          { label: "Geo", href: "/geo" },
          { label: "Municipalities", href: "/municipalities" },
          { label: "View" },
        ]}
      >
        <Stack direction="row">
          <RefreshButton onClick={() => bindMunicipality(id!)} />
          <EditButton
            onClick={() =>
              navigate(`/municipalities/edit/${id}`, { state: municipality })
            }
          />
          <DeleteButon onClick={() => setDeleteDialogOpen(true)} />
        </Stack>
      </AppBreadcrumbs>
      <Box sx={{ my: 2 }}>
        <Paper sx={{ p: 2 }}>
          {!municipality ? (
            <Box sx={{ display: "flex", justifyContent: "center", p: 2 }}>
              <CircularProgress />
            </Box>
          ) : (
            <Grid container spacing={1}>
              <LabelValueInfo label="Id" value={municipality.id} />
              <LabelValueInfo label="Name" value={municipality.name} />
              <LabelValueInfo
                label="Province"
                value={municipality.provinceId}
              />
              <Grid size={6}></Grid>
              <LabelValueInfo
                label="Created"
                value={formatDate(municipality.createdAt)}
              />
              <LabelValueInfo
                label="Updated"
                value={formatDate(municipality.updatedAt)}
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
