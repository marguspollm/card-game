import { AppBar, Button, Toolbar } from "@mui/material";
import { Link } from "react-router";

function Header() {
  return (
    <>
      <AppBar position="static">
        <Toolbar variant="dense">
          <Button
            key={"game"}
            component={Link}
            to={"/game"}
            variant="text"
            color="secondary"
          >
            Game
          </Button>
          <Button
            key={"scores"}
            component={Link}
            to={"/scores"}
            variant="text"
            color="secondary"
          >
            Leaderboard
          </Button>
        </Toolbar>
      </AppBar>
    </>
  );
}

export default Header;
