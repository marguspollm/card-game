import { TextField, Button, Box } from "@mui/material";
import { useContext, useState, type ChangeEvent } from "react";
import type { Player } from "../models/Player";
import { PlayerContext } from "../context/PlayerContext";
import { useNavigate } from "react-router";

const backendUrl = import.meta.env.VITE_API_HOST;

function PlayerCreation() {
  const [playerName, setPlayerName] = useState("");
  const [player, setPlayer] = useState<Player>();
  const { savePlayer } = useContext(PlayerContext);
  const navigate = useNavigate();

  async function createPlayer() {
    try {
      const res = await fetch(`${backendUrl}/player`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(player),
      });
      const data = await res.json();
      savePlayer(data);
      navigate("/game");
    } catch (err) {
      console.log("Error creating player:", err);
    }
  }

  function handleNameChange(
    e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) {
    const updatedName = e.target.value;
    setPlayerName(updatedName);
    const sanitizedName = updatedName.trim();
    if (sanitizedName === "") return;
    const updatedPlayer: Player = { name: sanitizedName };
    setPlayer(updatedPlayer);
  }

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        height: "100vh",
      }}
    >
      <TextField
        label="Enter player name"
        value={playerName}
        onChange={e => handleNameChange(e)}
        sx={{ width: 250, mb: 2 }}
      />
      <Button
        variant="contained"
        disabled={!playerName.trim()}
        onClick={createPlayer}
      >
        Create player
      </Button>
    </Box>
  );
}

export default PlayerCreation;
