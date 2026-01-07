import { Box, Typography } from "@mui/material";
import { convertCard } from "../../utils/CardConverter";
import type { GameState } from "../../models/GameState";
import Card from "./Card";

function CardDisplay({
  card,
  guessResult,
}: {
  card: GameState;
  guessResult: string;
}) {
  return (
    <Box
      className={
        guessResult === "correct"
          ? "flash-correct"
          : guessResult === "wrong"
          ? "flash-wrong"
          : ""
      }
      sx={{
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center",
        fontSize: "1.8rem",
        textAlign: "center",
        padding: 2,
        borderRadius: 2,
      }}
    >
      <Typography
        className="result-banner"
        sx={{
          color: guessResult === "correct" ? "green" : "red",
          visibility: guessResult ? "visible" : "hidden",
        }}
      >
        {guessResult === "correct" ? "Correct!" : "Wrong!"}
      </Typography>

      <Typography variant="h4" sx={{ mt: 2 }}>
        {card.card.rank} of {card.card.suit}
      </Typography>
      <Box className="card-container">
        <Card code={convertCard(card.card.rank, card.card.suit)} />
      </Box>
    </Box>
  );
}

export default CardDisplay;
