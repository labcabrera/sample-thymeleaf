import React from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Paper from "@mui/material/Paper";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";

export default function Provinces() {
  return (
    <Container>
      <Box sx={{ my: 2 }}>
        <Typography variant="h5" gutterBottom>
          Provinces
        </Typography>
        <Paper sx={{ p: 2 }}>
          <Box sx={{ display: "flex", justifyContent: "flex-end", mb: 2 }}>
            <Button variant="contained">Nuevo province</Button>
          </Box>
          <Typography>Listado de provinces (placeholder)</Typography>
        </Paper>
      </Box>
    </Container>
  );
}
