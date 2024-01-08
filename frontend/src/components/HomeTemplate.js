import * as React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import AppBar from '@mui/material/AppBar';
import CssBaseline from '@mui/material/CssBaseline';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import DrawerProfile from './DrawerProfile';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import { ChatBubble, Class, Logout } from '@mui/icons-material';
import SchoolIcon from '@mui/icons-material/School';
import { useNavigate } from "react-router-dom";
import GradingIcon from '@mui/icons-material/Grading';
import { Outlet } from 'react-router-dom';


const drawerWidth = 200;

const HomeTemplate = () => {
  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <AppBar position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
        <Toolbar>
          <Typography variant="h6" noWrap component="div">
            Interaction Portal
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        variant="permanent"
        sx={{
          width: drawerWidth,
          flexShrink: 0,
        //   [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box' },
        }}
      >
        <Toolbar />
        <Box sx={{ overflow: 'auto' }}>
            <DrawerProfile/>
            <Divider/>
            <ListItemButton>
                <ListItemIcon><ChatBubble/></ListItemIcon>
                <ListItemText>Conversations</ListItemText>
            </ListItemButton>
            <ListItemButton>
                <ListItemIcon><SchoolIcon/></ListItemIcon>
                <ListItemText>Classes</ListItemText>
            </ListItemButton>
              {/* <ListItemButton>
                  <ListItemIcon><GradingIcon/></ListItemIcon>
                  <ListItemText>Courses</ListItemText>
              </ListItemButton> */}
            <ListItemButton>
                <ListItemIcon><Logout/></ListItemIcon>
                <ListItemText>Logout</ListItemText>
            </ListItemButton>

        </Box>
      </Drawer>
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <Toolbar />
      </Box>
    </Box>
  );
}

export default HomeTemplate