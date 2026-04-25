import { createTheme } from "@mui/material/styles";

const theme = createTheme({
  palette: {
    mode: "light",
    primary: {
      main: "#005b72",
    },
    secondary: {
      main: "#f9b000",
    },
    background: {
      default: "#f5f7fb",
      paper: "#ffffff",
    },
    success: {
      main: "#2e7d32",
    },
  },
  typography: {
    fontFamily: "Inter, Roboto, Arial, sans-serif",
    h1: { fontSize: "2.125rem", fontWeight: 700 },
    h2: { fontSize: "1.75rem", fontWeight: 700 },
    h3: { fontSize: "1.5rem", fontWeight: 600 },
    body1: { fontSize: "1rem" },
  },
  shape: {
    borderRadius: 8,
  },
  components: {
    MuiButton: {
      defaultProps: {
        disableElevation: false,
      },
      styleOverrides: {
        root: {
          textTransform: "none",
          borderRadius: 8,
        },
      },
    },
    MuiAppBar: {
      styleOverrides: {
        root: {
          backgroundImage: "none",
        },
      },
    },
  },
});

export default theme;
