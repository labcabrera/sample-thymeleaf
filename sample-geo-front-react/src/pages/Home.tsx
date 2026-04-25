import { Container, Typography, Box, Breadcrumbs } from "@mui/material";

export default function Home() {
  return (
    <Container>
      <Breadcrumbs aria-label="breadcrumb">
        <Typography color="text.primary">Home</Typography>
      </Breadcrumbs>
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
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
