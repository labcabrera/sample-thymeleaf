import { useNavigate } from "react-router-dom";
import {
  AppBar,
  Toolbar,
  IconButton,
  Button,
  Box,
  Typography,
} from "@mui/material";
import UserMenu from "./UserMenu";
import MenuIcon from "@mui/icons-material/Menu";

type Props = {
  onDrawerToggle: () => void;
};

export default function Header({ onDrawerToggle }: Props) {
  const navigate = useNavigate();

  return (
    <AppBar position="fixed">
      <Toolbar>
        <IconButton
          color="inherit"
          edge="start"
          onClick={onDrawerToggle}
          sx={{ mr: 2 }}
        >
          <MenuIcon />
        </IconButton>

        <Button
          color="inherit"
          onClick={() => navigate("/")}
          sx={{ textTransform: "none" }}
        >
          <Typography variant="h6" component="div" sx={{ mr: 2 }}>
            Sample Geo React
          </Typography>
        </Button>

        <Button
          color="inherit"
          onClick={() => navigate("/geo")}
          sx={{ textTransform: "none" }}
        >
          <Typography variant="h6">Geo</Typography>
        </Button>

        <Box sx={{ flex: 1 }} />
        <UserMenu />
      </Toolbar>
    </AppBar>
  );
}
