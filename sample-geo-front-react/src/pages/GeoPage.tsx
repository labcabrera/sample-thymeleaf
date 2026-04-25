import {
  Container,
  Box,
  List,
  ListItemButton,
  ListItemText,
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
        <List>
          {items.map((it) => (
            <ListItemButton key={it.to} component={RouterLink} to={it.to}>
              <ListItemText primary={it.label} color="primary" />
            </ListItemButton>
          ))}
        </List>
      </Box>
    </Container>
  );
}
