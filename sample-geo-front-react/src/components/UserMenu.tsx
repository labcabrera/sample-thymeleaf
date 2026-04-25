import React from "react";
import Avatar from "@mui/material/Avatar";
import Tooltip from "@mui/material/Tooltip";
import IconButton from "@mui/material/IconButton";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import { useKeycloak } from "@react-keycloak/web";

export default function UserMenu() {
  const { keycloak } = useKeycloak();
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);

  const handleOpen = (e: React.MouseEvent<HTMLElement>) =>
    setAnchorEl(e.currentTarget);
  const handleClose = () => setAnchorEl(null);

  if (keycloak?.authenticated) {
    const username =
      (keycloak.tokenParsed as any)?.preferred_username || "User";
    const initial = (username as string).charAt(0).toUpperCase();
    return (
      <>
        <Tooltip title={username}>
          <IconButton onClick={handleOpen} color="inherit" sx={{ p: 0 }}>
            <Avatar sx={{ bgcolor: "secondary.main" }}>{initial}</Avatar>
          </IconButton>
        </Tooltip>
        <Menu
          anchorEl={anchorEl}
          open={Boolean(anchorEl)}
          onClose={handleClose}
        >
          <MenuItem
            onClick={() => {
              handleClose();
              keycloak.logout({ redirectUri: window.location.origin });
            }}
          >
            Logout
          </MenuItem>
        </Menu>
      </>
    );
  }

  return (
    <Button color="inherit" onClick={() => keycloak?.login()}>
      Login
    </Button>
  );
}
