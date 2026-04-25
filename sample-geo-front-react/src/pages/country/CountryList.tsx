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

export default function Countries() {
  const auth = useAuth();
  const [pageData, setPageData] = useState<Page<Country> | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [page, setPage] = useState<number>(0);
  const [size, setSize] = useState<number>(10);
  const [sort, setSort] = useState<string>("name,asc");
  const [rsql, setRsql] = useState<string>("");

  useEffect(() => {
    let mounted = true;
    const load = async () => {
      setLoading(true);
      setError(null);
      try {
        const data = await fetchCountries(rsql, size, page, sort, auth as any);
        if (!mounted) return;
        setPageData(data);
      } catch (e: any) {
        if (!mounted) return;
        setError(e?.message ?? String(e));
      } finally {
        if (mounted) setLoading(false);
      }
    };
    load();
    return () => {
      mounted = false;
    };
  }, [auth, page, size, sort, rsql]);

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
        <Typography variant="h5" gutterBottom>
          Countries
        </Typography>
        <Paper sx={{ p: 2 }}>
          <Box sx={{ display: "flex", justifyContent: "flex-end", mb: 2 }}>
            <Button variant="contained">Nuevo country</Button>
          </Box>

          {loading ? (
            <Box sx={{ display: "flex", justifyContent: "center", p: 2 }}>
              <CircularProgress />
            </Box>
          ) : error ? (
            <Box sx={{ p: 2 }}>
              <Typography color="error">{error}</Typography>
            </Box>
          ) : (
            <TableContainer component={Paper}>
              <Table>
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
        </Paper>
      </Box>
    </Container>
  );
}
