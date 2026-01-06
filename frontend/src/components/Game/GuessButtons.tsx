import { Box, Button } from "@mui/material";
import type { GuessType } from "../../models/GuessType";

function GuessButtons({
  sendGuess,
}: {
  sendGuess: (guess: GuessType) => void;
}) {
  return (
    <Box sx={{ display: "flex", justifyContent: "center", gap: 3, mt: 3 }}>
      <Button
        variant="contained"
        sx={{ width: 120, fontWeight: "bold" }}
        onClick={() => sendGuess("HIGHER")}
      >
        Higher
      </Button>
      <Button
        variant="contained"
        sx={{ width: 120, fontWeight: "bold" }}
        onClick={() => sendGuess("EQUAL")}
      >
        Equal
      </Button>
      <Button
        variant="contained"
        sx={{ width: 120, fontWeight: "bold" }}
        onClick={() => sendGuess("LOWER")}
      >
        Lower
      </Button>
    </Box>
  );
}

export default GuessButtons;
