/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable react-hooks/set-state-in-effect */
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
  fetchProvince,
  deleteProvince,
  type Province,
} from "../../lib/provinces-api";
import AppBreadcrumbs from "../../components/AppBreadcrumbs";
import DeleteButon from "../../components/buttons/DeleteButton";
import EditButton from "../../components/buttons/EditButton";
import RefreshButton from "../../components/buttons/RefreshButton";

export default function ProvinceView() {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const navigate = useNavigate();
  const auth = useAuth();
  const [province, setProvince] = useState<Province>();
  const [deleteDialogOpen, setDeleteDialogOpen] = useState<boolean>(false);

  const onDelete = async () => {
    if (!province?.id) return;
    deleteProvince(province.id, auth)
      .then(() => navigate("/provinces"))
      .catch((err) => console.log("error deleting province", err));
  };

  const bindProvince = (id: string) => {
    fetchProvince(id!, auth).then((response) => setProvince(response));
  };

  useEffect(() => {
    if (location.state?.province) {
      setProvince(location.state.province);
    } else if (id) {
      bindProvince(id);
    }
  }, [id, auth, province, location]);

  return (
    <Container>
      <AppBreadcrumbs
        items={[
          { label: "Home", href: "/" },
          { label: "Geo", href: "/geo" },
          { label: "Provinces", href: "/provinces" },
          { label: "View" },
        ]}
      >
        <Stack direction="row">
          <RefreshButton onClick={() => bindProvince(id!)} />
          <EditButton
            onClick={() =>
              navigate(`/provinces/edit/${id}`, { state: province })
            }
          />
          <DeleteButon onClick={() => setDeleteDialogOpen(true)} />
        </Stack>
      </AppBreadcrumbs>
      <Box sx={{ my: 2 }}>
        <Paper sx={{ p: 2 }}>
          {!province ? (
            <Box sx={{ display: "flex", justifyContent: "center", p: 2 }}>
              <CircularProgress />
            </Box>
          ) : province ? (
            <Box>
              <Typography variant="h6">{province.name}</Typography>
              <Typography variant="body2" color="text.secondary">
                Id: {province.id}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Country Id: {province.countryId}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Created At: {province.createdAt}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Updated At: {province.updatedAt}
              </Typography>
            </Box>
          ) : (
            <Typography>No se encontró la province</Typography>
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
