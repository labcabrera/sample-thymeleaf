import {
  Container,
  Box,
  List,
  ListItemButton,
  ListItemText,
  Paper,
  Typography,
} from "@mui/material";
import { Link as RouterLink } from "react-router-dom";
import AppBreadcrumbs from "../components/AppBreadcrumbs";

export default function GeoPage() {
  const items = [
    { label: "Countries", to: "/countries" },
    { label: "Provinces", to: "/provinces" },
    { label: "Municipalities", to: "/municipalities" },
  ];

  return (
    <Container>
      <AppBreadcrumbs
        items={[{ label: "Home", href: "/" }, { label: "Geo" }]}
      />
      <Box sx={{ my: 2 }}>
        <Paper>
          <List dense>
            {items.map((it) => (
              <ListItemButton key={it.to} component={RouterLink} to={it.to}>
                <ListItemText>
                  <Typography color="primary">{it.label}</Typography>
                </ListItemText>
              </ListItemButton>
            ))}
          </List>
        </Paper>
      </Box>
    </Container>
  );
}
