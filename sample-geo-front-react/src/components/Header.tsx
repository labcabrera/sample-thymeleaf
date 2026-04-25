import { Link as RouterLink } from "react-router-dom";
import React from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import Button from "@mui/material/Button";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import UserMenu from "./UserMenu";

const MENU_OPTIONS: Array<{ label: string; path: string }> = [
  { label: "Countries", path: "/countries" },
  { label: "Provinces", path: "/provinces" },
  { label: "Municipalities", path: "/municipalities" },
];

type Props = {
  onDrawerToggle: () => void;
};

export default function Header({ onDrawerToggle }: Props) {
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);

  const handleGeoClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  // user menu is handled by UserMenu component

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

        <Typography variant="h6" component="div" sx={{ mr: 2 }}>
          Sample Geo React
        </Typography>

        <Button
          color="inherit"
          onClick={handleGeoClick}
          sx={{ textTransform: "none" }}
        >
          <Typography variant="h6">Geo</Typography>
        </Button>

        <Menu
          anchorEl={anchorEl}
          open={Boolean(anchorEl)}
          onClose={handleClose}
        >
          {MENU_OPTIONS.map((opt) => (
            <MenuItem
              key={opt.path}
              component={RouterLink}
              to={opt.path}
              onClick={handleClose}
            >
              {opt.label}
            </MenuItem>
          ))}
        </Menu>

        <Box sx={{ flex: 1 }} />
        <UserMenu />
      </Toolbar>
    </AppBar>
  );
}
