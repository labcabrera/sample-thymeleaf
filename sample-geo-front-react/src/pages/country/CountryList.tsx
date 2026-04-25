import { useState, useEffect } from "react";
import {
  Container,
  Box,
  CircularProgress,
  TextField,
  IconButton,
  List,
  ListItemText,
  ListItemButton,
  Pagination,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useAuth } from "react-oidc-context";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import {
  fetchCountries,
  type Country,
  type Page,
} from "../../lib/countries-api";
import AppBreadcrumbs from "../../components/AppBreadcrumbs";

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
        <IconButton onClick={() => navigate("/countries/create")}>
          <AddCircleIcon />
        </IconButton>
      </AppBreadcrumbs>
      <Box sx={{ my: 2 }}>
        <Box
          component="form"
          sx={{ display: "flex", gap: 2, mb: 2 }}
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
            <List dense>
              {rows.map((r) => (
                <ListItemButton
                  key={r.id}
                  onClick={() =>
                    navigate(`/countries/view/${r.id}`, {
                      state: { country: r },
                    })
                  }
                  sx={{
                    border: 1,
                    borderColor: "primary.light",
                    borderRadius: 1,
                    mb: 1,
                  }}
                >
                  <ListItemText primary={`${r.name}`} />
                </ListItemButton>
              ))}
            </List>
            <Box>
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
