import React from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";

export default function Home() {
  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          Bienvenido a Sample Geo
        </Typography>
        <Typography>
          Este es el portal de gestión de entidades: Country, Province y
          Municipality.
        </Typography>
      </Box>
    </Container>
  );
}
