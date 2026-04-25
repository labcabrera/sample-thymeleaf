import React from "react";
import { Outlet, Link as RouterLink } from "react-router-dom";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import Typography from "@mui/material/Typography";
import Drawer from "@mui/material/Drawer";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemText from "@mui/material/ListItemText";
import Box from "@mui/material/Box";

const drawerWidth = 240;

export default function Layout() {
  const [open, setOpen] = React.useState(false);

  return (
    <Box sx={{ display: "flex" }}>
      <AppBar position="fixed">
        <Toolbar>
          <IconButton
            color="inherit"
            edge="start"
            onClick={() => setOpen(true)}
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap component="div">
            Sample Geo
          </Typography>
        </Toolbar>
      </AppBar>

      <Drawer open={open} onClose={() => setOpen(false)}>
        <Box
          sx={{ width: drawerWidth }}
          role="presentation"
          onClick={() => setOpen(false)}
        >
          <List>
            <ListItem component={RouterLink} to="/">
              <ListItemText primary="Home" />
            </ListItem>
            <ListItem component={RouterLink} to="/countries">
              <ListItemText primary="Countries" />
            </ListItem>
            <ListItem component={RouterLink} to="/provinces">
              <ListItemText primary="Provinces" />
            </ListItem>
            <ListItem component={RouterLink} to="/municipalities">
              <ListItemText primary="Municipalities" />
            </ListItem>
            <ListItem component={RouterLink} to="/login">
              <ListItemText primary="Login" />
            </ListItem>
          </List>
        </Box>
      </Drawer>

      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <Toolbar />
        <Outlet />
      </Box>
    </Box>
  );
}
