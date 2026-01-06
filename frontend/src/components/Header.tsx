import { AppBar, Box, Button, Toolbar, Typography } from "@mui/material";
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

  return (
    <>
      <AppBar position="static">
        <Toolbar variant="dense">
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
              <Typography>{player?.name}</Typography>
              <Button
                onClick={handleLogout}
                variant="text"
                sx={{
                  color: "white",
                  borderRadius: "12px",
                  border: "1px solid black",
                  mx: 1,
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
