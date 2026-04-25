import React from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Paper from "@mui/material/Paper";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import CircularProgress from "@mui/material/CircularProgress";
import { useKeycloak } from "@react-keycloak/web";
import callApi from "../../lib/api";

export default function Countries() {
  const { keycloak } = useKeycloak();
  const [loading, setLoading] = React.useState(false);
  const [items, setItems] = React.useState<
    Array<{ id?: string; name: string }>
  >([]);
  const [error, setError] = React.useState<string | null>(null);

  React.useEffect(() => {
    let mounted = true;
    const load = async () => {
      setLoading(true);
      setError(null);
      try {
        if (!keycloak) {
          setError("Authentication service not available");
          return;
        }

        if (!keycloak.authenticated) {
          setError("Not authenticated");
          return;
        }

        const data = await callApi(keycloak as any, "/countries");
        if (!mounted) return;
        // assume API returns array of { id, name }
        setItems(Array.isArray(data) ? data : []);
      } catch (e: any) {
        if (!mounted) return;
        setError(e.message || "Error fetching countries");
      } finally {
        if (mounted) setLoading(false);
      }
    };

    load();
    return () => {
      mounted = false;
    };
  }, [keycloak]);

  return (
    <Container>
      <Box sx={{ my: 2 }}>
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
              <Typography color="error" sx={{ mb: 2 }}>
                {error}
              </Typography>
              {(!keycloak || !keycloak.authenticated) && (
                <Button variant="contained" onClick={() => keycloak?.login()}>
                  Login
                </Button>
              )}
            </Box>
          ) : (
            <List>
              {items.map((it) => (
                <ListItem key={it.id ?? it.name}>
                  <ListItemText primary={it.name} secondary={it.id} />
                </ListItem>
              ))}
            </List>
          )}
        </Paper>
      </Box>
    </Container>
  );
}
