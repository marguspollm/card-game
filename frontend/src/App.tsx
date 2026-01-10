import { Route, Routes } from "react-router";
import { Box } from "@mui/material";
import Header from "./components/Header";
import Scores from "./pages/Scores";
import PlayerScores from "./pages/PlayerScores";
import Game from "./pages/Game";
import Home from "./pages/Home";
import Login from "./pages/Login";

function App() {
  return (
    <>
      <Header />
      <Box sx={{ margin: 5 }}>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/game" element={<Game />} />
          <Route path="/scores" element={<Scores />} />
          <Route path="/player/:id/scores" element={<PlayerScores />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </Box>
    </>
  );
}

export default App;
