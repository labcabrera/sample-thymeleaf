import { createTheme, lighten, darken } from "@mui/material/styles";

const primaryMain = "#005b72";
const theme = createTheme({
  palette: {
    mode: "light",
    primary: {
      main: primaryMain,
      light: lighten(primaryMain, 0.15),
      dark: darken(primaryMain, 0.15),
      contrastText: "#ffffff",
    },
    // Additional palette configuration
    secondary: {
      main: "#f9b000",
    },
    divider: "#545f61",
    error: {
      main: "#d32f2f",
    },
    // improve contrast handling
    tonalOffset: 0.2,
    contrastThreshold: 3,
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
