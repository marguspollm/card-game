import { Box, Typography } from "@mui/material";
import "../../css/Game.css";

function GameBar({
  lives,
  timer,
  score,
  guessResult,
}: {
  lives: number;
  timer: number;
  score: number;
  guessResult: string;
}) {
  function showLives(lives: number) {
    let livesLeft = "";
    if (lives === 0) return "💔";
    for (let i = 0; i < lives; i++) {
      livesLeft += "💚";
    }
    return livesLeft;
  }
  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-around",
        width: "100%",
        p: 2,
      }}
    >
      <Typography
        variant="h6"
        className={guessResult === "wrong" ? "flash-wrong" : ""}
      >
        Lives: {showLives(lives)}
      </Typography>
      <Typography variant="h6">Time: {timer}s</Typography>
      <Typography
        variant="h6"
        className={guessResult === "correct" ? "flash-correct" : ""}
      >
        Score: {score}
      </Typography>
    </Box>
  );
}

export default GameBar;
