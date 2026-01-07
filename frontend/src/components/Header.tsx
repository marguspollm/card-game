import {
  AppBar,
  Avatar,
  Box,
  Button,
  Divider,
  IconButton,
  Toolbar,
  Typography,
} from "@mui/material";
import { useContext } from "react";
import { Link, useNavigate } from "react-router";
import { PlayerContext } from "../context/PlayerContext";

function Header() {
  const { player, isLoggedIn, removePlayer, removeSession } =
    useContext(PlayerContext);
  const navigate = useNavigate();

  function handleLogout() {
    removePlayer();
    removeSession();
    navigate("/");
  }

  function initials() {
    return player?.name
      .split(" ")
      .map(i => i[0])
      .join("")
      .toUpperCase();
  }

  function stringToColor(string: string = "") {
    let hash = 0;
    let i;

    for (i = 0; i < string.length; i += 1) {
      hash = string.charCodeAt(i) + ((hash << 5) - hash);
    }

    let color = "#";

    for (i = 0; i < 3; i += 1) {
      const value = (hash >> (i * 8)) & 0xff;
      color += `00${value.toString(16)}`.slice(-2);
    }

    return color;
  }

  return (
    <>
      <AppBar position="static">
        <Toolbar variant="dense">
          <IconButton key={"home"} component={Link} to={"/"}>
            🏠
          </IconButton>
          <Divider
            orientation="vertical"
            sx={{ color: "white", mt: 0.5, mb: 0.5 }}
            flexItem
          />
          {!isLoggedIn ? (
            <Button
              key={"create-player"}
              component={Link}
              to={"/create-player"}
              variant="text"
              sx={{ color: "white" }}
            >
              Create player
            </Button>
          ) : (
            <Button
              key={"game"}
              component={Link}
              to={"/game"}
              variant="text"
              sx={{ color: "white" }}
            >
              Game
            </Button>
          )}
          <Button
            key={"scores"}
            component={Link}
            to={"/scores"}
            variant="text"
            sx={{ color: "white" }}
          >
            Leaderboard
          </Button>
          <Box sx={{ flexGrow: 1 }} />
          {isLoggedIn && (
            <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
              <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
                <Avatar sx={{ bgcolor: stringToColor(player?.name) }}>
                  {initials()}
                </Avatar>
                <Typography variant="subtitle1">{player?.name}</Typography>
              </Box>

              <Button
                onClick={handleLogout}
                variant="text"
                sx={{
                  color: "white",
                  borderRadius: "12px",
                  mx: 1,
                  background:
                    "linear-gradient(to right, #e15151ff, #fb1c15ff, #e15151ff )",
                }}
              >
                Logout
              </Button>
            </Box>
          )}
        </Toolbar>
      </AppBar>
    </>
  );
}

export default Header;
