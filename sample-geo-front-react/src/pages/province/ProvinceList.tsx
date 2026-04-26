/* eslint-disable react-hooks/set-state-in-effect */
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
import AppBreadcrumbs from "../../components/AppBreadcrumbs";
import AddButton from "../../components/buttons/AddButton";
import type { Page } from "../../lib/api";
import { fetchProvinces, type Province } from "../../lib/provinces-api";
import CountrySelect from "../../components/selects/CountrySelect";

export default function ProvinceList() {
  const auth = useAuth();
  const navigate = useNavigate();
  const [pageData, setPageData] = useState<Page<Province> | null>(null);
  const [page, setPage] = useState<number>(0);
  const [rsql, setRsql] = useState<string>("");

  const [nameFilter, setNameFilter] = useState<string>();
  const [countryFilter, setCountryFilter] = useState<string>();

  useEffect(() => {
    let rsql = "";
    if (nameFilter && nameFilter !== "") rsql = `name=re=${name}`;
    if (countryFilter && countryFilter !== "") {
      if (rsql !== "") rsql += ";";
      rsql += `countryId==${countryFilter}`;
    }
    setRsql(rsql);
  }, [nameFilter, countryFilter]);

  useEffect(() => {
    fetchProvinces(rsql, 10, page, "name,asc", auth).then((response) =>
      setPageData(response),
    );
  }, [auth, page, rsql]);

  return (
    <Container>
      <AppBreadcrumbs
        items={[
          { label: "Home", href: "/" },
          { label: "Geo", href: "/geo" },
          { label: "Provinces" },
        ]}
      >
        <AddButton onClick={() => navigate("/provinces/create")} />
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
            onChange={(e) => setNameFilter(e.target.value)}
          />
          <CountrySelect
            onChange={(v) => setCountryFilter(v?.id)}
            size="small"
            sx={{ minWidth: 240 }}
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
                {pageData.content.map((r) => (
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
