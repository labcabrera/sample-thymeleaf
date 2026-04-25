import { useState, useEffect } from "react";
import {
  Container,
  Box,
  TablePagination,
  CircularProgress,
  TextField,
  IconButton,
  List,
  ListItemText,
  ListItemButton,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import {
  fetchCountries,
  type Country,
  type Page,
  type Pagination,
} from "../../lib/countries-api";
import AppBreadcrumbs from "../../components/AppBreadcrumbs";

export default function CountryList() {
  const auth = useAuth();
  const navigate = useNavigate();
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
          <AppBreadcrumbs
            items={[
              { label: "Home", href: "/" },
              { label: "Geo", href: "/geo" },
              { label: "Countries" },
            ]}
          >
            <IconButton onClick={() => navigate("/countries/create")}>
              <AddCircleIcon />
            </IconButton>
          </AppBreadcrumbs>
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
          <>
            <List>
              {rows.map((r) => (
                <ListItemButton
                  key={r.id}
                  onClick={() =>
                    navigate(`/countries/view/${r.id}`, {
                      state: { country: r },
                    })
                  }
                >
                  <ListItemText primary={r.name} secondary={r.id} />
                </ListItemButton>
              ))}
            </List>
            <TablePagination
              component="div"
              count={pagination?.totalElements ?? 0}
              page={pagination ? pagination.page : page}
              onPageChange={handleChangePage}
              rowsPerPage={pagination ? pagination.size : size}
              onRowsPerPageChange={handleChangeRowsPerPage}
              rowsPerPageOptions={[5, 10, 25, 50]}
            />
          </>
        )}
      </Box>
    </Container>
  );
}
