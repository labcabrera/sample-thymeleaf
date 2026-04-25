import { useState, useEffect } from "react";
import {
  Container,
  Box,
  CircularProgress,
  TextField,
  List,
  ListItemText,
  ListItemButton,
  Pagination,
  Paper,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import {
  fetchCountries,
  type Country,
  type Page,
} from "../../lib/countries-api";
import AppBreadcrumbs from "../../components/AppBreadcrumbs";
import AddButton from "../../components/buttons/AddButton";

export default function CountryList() {
  const auth = useAuth();
  const navigate = useNavigate();
  const [pageData, setPageData] = useState<Page<Country> | null>(null);
  const [page, setPage] = useState<number>(0);
  const [rsql, setRsql] = useState<string>("");
  const [nameFilter, setNameFilter] = useState<string>("");

  useEffect(() => {
    fetchCountries(rsql, 10, page, "name,asc", auth).then((response) =>
      setPageData(response),
    );
  }, [auth, page, rsql]);

  const rows: Country[] = pageData?.content ?? [];

  return (
    <Container>
      <AppBreadcrumbs
        items={[
          { label: "Home", href: "/" },
          { label: "Geo", href: "/geo" },
          { label: "Countries" },
        ]}
      >
        <AddButton onClick={() => navigate("/countries/create")} />
      </AppBreadcrumbs>
      <Box sx={{ my: 2 }}>
        <Box
          component="form"
          sx={{ display: "flex", gap: 2, my: 2 }}
          onSubmit={(e) => e.preventDefault()}
        >
          <TextField
            label="Name"
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
            <Paper>
              <List dense>
                {rows.map((r) => (
                  <ListItemButton
                    key={r.id}
                    onClick={() =>
                      navigate(`/countries/view/${r.id}`, {
                        state: { country: r },
                      })
                    }
                  >
                    <ListItemText primary={`${r.name}`} />
                  </ListItemButton>
                ))}
              </List>
            </Paper>
            <Box
              sx={{
                display: "flex",
                justifyContent: "center",
              }}
            >
              <Pagination
                count={pageData.pagination.totalPages}
                page={pageData.pagination.page + 1}
                onChange={(_: React.ChangeEvent<unknown>, value: number) =>
                  setPage(value - 1)
                }
                color="primary"
                showFirstButton
                showLastButton
              />
            </Box>
          </>
        )}
      </Box>
    </Container>
  );
}
