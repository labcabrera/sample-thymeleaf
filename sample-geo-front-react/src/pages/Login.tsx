import React from "react";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import { useAuth } from "react-oidc-context";

export default function Login() {
  const auth = useAuth();

  const handleLogin = () => {
    auth?.signinRedirect();
  };

  return (
    <Container>
      <Box sx={{ my: 4 }}>
        <Typography variant="h5" gutterBottom>
          Iniciar sesión
        </Typography>
        <Typography sx={{ mb: 2 }}>Iniciar sesión (OIDC)</Typography>
        <Button variant="contained" color="primary" onClick={handleLogin}>
          Login
        </Button>
      </Box>
    </Container>
  );
}
