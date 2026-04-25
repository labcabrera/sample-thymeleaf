import { Container, Typography, Box } from "@mui/material";
import AppBreadcrumbs from "../components/AppBreadcrumbs";

export default function HomePage() {
  return (
    <Container>
      <AppBreadcrumbs items={[{ label: "Home" }]} />
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" color="primary" gutterBottom>
          Sample Geo React
        </Typography>
        <Typography>
          An example of a front-end application using React and MUI for managing
          geographical entities such as countries and regions.
        </Typography>
      </Box>
    </Container>
  );
}
