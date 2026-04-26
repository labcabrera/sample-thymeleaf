import { useState, useEffect, type ChangeEvent } from "react";
import {
  Container,
  Box,
  CircularProgress,
  List,
  ListItemText,
  ListItemButton,
  Pagination,
  Paper,
  Typography,
  ListItemAvatar,
  Avatar,
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import { useAuth, type AuthContextProps } from "react-oidc-context";
import { fetchCountries, type Country } from "../../lib/countries-api";
import AppBreadcrumbs from "../../components/AppBreadcrumbs";
import AddButton from "../../components/buttons/AddButton";
import type { Page } from "../../lib/api";
import ClearableTextField from "../../components/inputs/ClearableTextField";

export default function CountryList() {
  const auth = useAuth();
  const navigate = useNavigate();
  const [pageData, setPageData] = useState<Page<Country> | null>(null);
  const [page, setPage] = useState<number>(0);
  const [nameFilter, setNameFilter] = useState<string>("");

  const bindCountries = (
    rsql: string,
    page: number,
    auth: AuthContextProps,
  ) => {
    fetchCountries(rsql, 10, page, "name,asc", auth).then((response) =>
      setPageData(response),
    );
  };

  useEffect(() => {
    const rsql = nameFilter ? `name=re=${nameFilter}` : "";
    bindCountries(rsql, page, auth);
  }, [auth, page, nameFilter]);

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
      <Paper elevation={5} sx={{ p: 2 }}>
        <Box
          component="form"
          sx={{ display: "flex", gap: 2, my: 2 }}
          onSubmit={(e) => e.preventDefault()}
        >
          <ClearableTextField
            label="Name"
            value={nameFilter}
            onChange={(e) => setNameFilter(e || "")}
          />
        </Box>
        {!pageData ? (
          <Box sx={{ display: "flex", justifyContent: "center", p: 2 }}>
            <CircularProgress />
          </Box>
        ) : (
          <>
            <List dense>
              {pageData.content.map((r) => (
                <ListItemButton
                  key={r.id}
                  onClick={() =>
                    navigate(`/countries/view/${r.id}`, {
                      state: { country: r },
                    })
                  }
                >
                  <ListItemAvatar>
                    <Avatar
                      src={`https://flagcdn.com/${r.id.toLowerCase()}.svg`}
                      variant="rounded"
                      alt={r.id}
                    ></Avatar>
                  </ListItemAvatar>
                  <ListItemText>
                    <Typography color="primary">{r.name}</Typography>
                  </ListItemText>
                </ListItemButton>
              ))}
            </List>
            {pageData.content.length === 0 && (
              <Typography>No results were found</Typography>
            )}
            <Box
              sx={{
                display: "flex",
                justifyContent: "center",
                mt: 2,
              }}
            >
              <Pagination
                count={pageData.pagination.totalPages}
                page={pageData.pagination.page + 1}
                onChange={(_: ChangeEvent<unknown>, value: number) =>
                  setPage(value - 1)
                }
                color="primary"
                showFirstButton
                showLastButton
              />
            </Box>
          </>
        )}
      </Paper>
    </Container>
  );
}
