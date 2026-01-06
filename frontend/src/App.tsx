import { Route, Routes } from "react-router";
import { Box } from "@mui/material";
import Header from "./components/Header";
import Scores from "./pages/Scores";
import PlayerScores from "./pages/PlayerScores";
import Game from "./pages/Game";
import PlayerCreation from "./pages/PlayerCreation";

function App() {
  return (
    <>
      <Header />
      <Box sx={{ margin: 5 }}>
        <Routes>
          <Route path="/" element={<PlayerCreation />} />
          <Route path="/game" element={<Game />} />
          <Route path="/scores" element={<Scores />} />
          <Route path="/scores/player/:id" element={<PlayerScores />} />
          <Route path="/create-player" element={<PlayerCreation />} />
        </Routes>
      </Box>
    </>
  );
}

export default App;
