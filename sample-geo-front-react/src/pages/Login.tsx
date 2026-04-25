import React from "react";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import { useKeycloak } from "@react-keycloak/web";

export default function Login() {
  const { keycloak } = useKeycloak();

  const handleLogin = () => {
    keycloak?.login();
  };

  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h5" gutterBottom>
          Iniciar sesión
        </Typography>
        <Typography sx={{ mb: 2 }}>
          Para iniciar sesión se redirigirá a Keycloak.
        </Typography>
        <Button variant="contained" color="primary" onClick={handleLogin}>
          Login con Keycloak
        </Button>
      </Box>
    </Container>
  );
}
