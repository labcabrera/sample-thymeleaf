import { useState, useEffect } from "react";
import {
  Container,
  Typography,
  Paper,
  Box,
  Button,
  Table,
  TableHead,
  TableBody,
  TableRow,
  TableCell,
  TableContainer,
  TablePagination,
  CircularProgress,
  TextField,
} from "@mui/material";
import Breadcrumbs from "@mui/material/Breadcrumbs";
import Link from "@mui/material/Link";
import { Link as RouterLink } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import {
  fetchCountries,
  type Country,
  type Page,
  type Pagination,
} from "../../lib/countries-api";

export default function CountryList() {
  const auth = useAuth();
  const [pageData, setPageData] = useState<Page<Country> | null>(null);
  const [page, setPage] = useState<number>(0);
  const [size, setSize] = useState<number>(10);
  const [rsql, setRsql] = useState<string>("");
  const [nameFilter, setNameFilter] = useState<string>("");

  useEffect(() => {
    fetchCountries(rsql, size, page, "name,asc", auth).then((response) =>
      setPageData(response),
    );
  }, [auth, page, size, rsql]);

  const handleChangePage = (_: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSize(parseInt(e.target.value, 10));
    setPage(0);
  };

  const rows: Country[] = pageData?.content ?? [];
  const pagination: Pagination | null =
    (pageData as Page<Country>)?.pagination ?? null;

  return (
    <Container>
      <Box sx={{ my: 2 }}>
        <Box sx={{ mb: 1 }}>
          <Breadcrumbs aria-label="breadcrumb">
            <Link
              component={RouterLink}
              underline="hover"
              color="inherit"
              to="/"
            >
              Home
            </Link>
            <Link
              component={RouterLink}
              underline="hover"
              color="inherit"
              to="/geo"
            >
              Geo
            </Link>
            <Typography color="text.primary">Countries</Typography>
          </Breadcrumbs>
        </Box>
        <Box sx={{ display: "flex", justifyContent: "flex-end", mb: 2 }}>
          <Button variant="contained">Nuevo country</Button>
        </Box>
        <Box
          component="form"
          sx={{ display: "flex", gap: 2, mb: 2 }}
          onSubmit={(e) => e.preventDefault()}
        >
          <TextField
            label="Nombre"
            variant="outlined"
            size="small"
            value={nameFilter}
            onChange={(e) => {
              const v = e.target.value;
              setNameFilter(v);
              setRsql(v ? `name=re=${v}` : "");
              setPage(0);
            }}
          />
        </Box>

        {!pageData ? (
          <Box sx={{ display: "flex", justifyContent: "center", p: 2 }}>
            <CircularProgress />
          </Box>
        ) : (
          <TableContainer component={Paper}>
            <Table size="small">
              <TableHead>
                <TableRow>
                  <TableCell>Id</TableCell>
                  <TableCell>Nombre</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {rows.map((r) => (
                  <TableRow key={r.id} hover>
                    <TableCell>{r.id}</TableCell>
                    <TableCell>{r.name}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
            <TablePagination
              component="div"
              count={pagination?.totalElements ?? 0}
              page={pagination ? pagination.page : page}
              onPageChange={handleChangePage}
              rowsPerPage={pagination ? pagination.size : size}
              onRowsPerPageChange={handleChangeRowsPerPage}
              rowsPerPageOptions={[5, 10, 25, 50]}
            />
          </TableContainer>
        )}
      </Box>
    </Container>
  );
}
