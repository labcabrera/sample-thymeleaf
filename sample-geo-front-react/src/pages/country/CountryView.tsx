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
  Avatar,
  Table,
  TableBody,
  TableHead,
  TableRow,
  TableCell,
  TableContainer,
  TablePagination,
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
import { fetchProvinces, type Province } from "../../lib/provinces-api";
import type { Page } from "../../lib/api";

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
              <Grid size={12}>
                <Avatar
                  src={`https://flagcdn.com/${country.id.toLowerCase()}.svg`}
                  variant="rounded"
                  alt={country.id}
                ></Avatar>
              </Grid>
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
      {country?.id && (
        <Box sx={{ my: 2 }}>
          <Paper sx={{ p: 2 }}>
            <CountryViewProvinces countryId={country.id} />
          </Paper>
        </Box>
      )}
    </Container>
  );
}
function CountryViewProvinces({ countryId }: { countryId?: string }) {
  const navigate = useNavigate();
  const [pageData, setPageData] = useState<Page<Province>>();
  const [page, setPage] = useState<number>(0);
  const [size, setSize] = useState<number>(20);
  const auth = useAuth();

  useEffect(() => {
    if (!countryId || !auth) return;
    fetchProvinces(
      `country.id==${countryId}`,
      size,
      page,
      "name,asc",
      auth,
    ).then((response) => setPageData(response));
  }, [countryId, page, size, auth]);

  if (!pageData || pageData.content.length === 0) {
    return <Box sx={{ p: 2 }}>No provinces</Box>;
  }
  return (
    <>
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Id</TableCell>
              <TableCell>Name</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {pageData.content.map((p) => (
              <TableRow
                key={p.id}
                hover
                sx={{ cursor: "pointer" }}
                onClick={() =>
                  navigate(`/provinces/view/${p.id}`, {
                    state: { province: p },
                  })
                }
              >
                <TableCell>{p.id}</TableCell>
                <TableCell>{p.name}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        component="div"
        count={pageData.pagination.totalElements}
        page={page}
        onPageChange={(_e, newPage) => setPage(newPage)}
        rowsPerPage={size}
        onRowsPerPageChange={(e) => {
          const newSize = parseInt(e.target.value as string, 10);
          setSize(newSize);
          setPage(0);
        }}
        rowsPerPageOptions={[10, 20, 50, 100]}
      />
    </>
  );
}
