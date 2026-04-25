import {
  Container,
  Paper,
  Box,
  Typography,
  List,
  ListItemButton,
  ListItemText,
  Breadcrumbs,
  Link,
} from "@mui/material";
import { Link as RouterLink } from "react-router-dom";

export default function GeoPage() {
  const items = [
    { label: "Countries", to: "/countries" },
    { label: "Provinces", to: "/provinces" },
    { label: "Municipalities", to: "/municipalities" },
  ];

  return (
    <Container>
      <Box sx={{ my: 2 }}>
        <Breadcrumbs aria-label="breadcrumb">
          <Link component={RouterLink} underline="hover" color="inherit" to="/">
            Home
          </Link>
          <Typography color="text.primary">Geo</Typography>
        </Breadcrumbs>
        <Paper sx={{ p: 2 }}>
          <List>
            {items.map((it) => (
              <ListItemButton key={it.to} component={RouterLink} to={it.to}>
                <ListItemText primary={it.label} />
              </ListItemButton>
            ))}
          </List>
        </Paper>
      </Box>
    </Container>
  );
}
