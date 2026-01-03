import { useEffect, useState, type ChangeEvent } from "react";
import type { CardGame } from "../models/CardGame";
import type { GuessType } from "../models/GuessType";
import { Box, Button, TextField, Typography } from "@mui/material";
import type { GameRequest } from "../models/requests/GameRequest";
import type { Player } from "../models/Player";
import Card from "../components/Card";
import { convertCard } from "../utils/CardConverter";

const backendUrl = import.meta.env.VITE_API_HOST;

function Game() {
  const [sessionId, setSessionId] = useState<string>("");
  const [card, setCard] = useState<CardGame>();
  const [player, setPlayer] = useState<Player>();
  const [playerNameEntered, setPlayerNameEntered] = useState(false);
  const [userCreated, setUserCreated] = useState(false);
  const [gameStarted, setGameStarted] = useState(false);
  const [score, setScore] = useState(0);
  const [lives, setLives] = useState(3);
  const [timer, setTimer] = useState(10);
  const [buttonsVisible, setButtonsVisible] = useState(true);

  async function createSession() {
    const payload = { player };
    try {
      const res = await fetch(`${backendUrl}/game`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      const data = await res.json();
      setSessionId(data.sessionId);
      setPlayer(data.player);
    } catch (err) {
      console.error("Error creating session:", err);
    }
  }

  async function startGame() {
    const payload: GameRequest = { sessionId };
    try {
      const res = await fetch(`${backendUrl}/game/start-game`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      const data = await res.json();
      setCard(data);
    } catch (err) {
      console.error("Error starting game:", err);
    }
  }

  async function sendGuess(guess: GuessType) {
    const payload: GameRequest = { sessionId, guess };
    try {
      const res = await fetch(`${backendUrl}/game/guess`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      const data = await res.json();
      setCard(data);
      setLives(data.lives);
      setScore(data.score);
      if (data.status === "TIME_OUT") return;
      if (data.status === "LIVES_LOST") {
        setButtonsVisible(false);
        return;
      }
      setTimer(10);
    } catch (err) {
      console.error("Guess error:", err);
    }
  }

  async function endGame() {
    const payload: GameRequest = { sessionId };
    try {
      await fetch(`${backendUrl}/game/end-game`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
    } catch (err) {
      console.error("End game error:", err);
    }
  }

  function handleNameChange(
    e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) {
    const name = e.target.value;
    setPlayer((prev) => ({ ...prev, name }));
    setPlayerNameEntered(name.trim().length > 0);
  }

  function createUserAndSession() {
    createSession();
    setUserCreated(true);
  }

  function startNewGame() {
    createSession();
    setUserCreated(true);
    setGameStarted(false);
  }

  async function drawCard() {
    setGameStarted(true);
    setScore(0);
    setLives(3);
    setTimer(10);
    setButtonsVisible(true);
    await startGame();
  }

  useEffect(() => {
    if (!userCreated || !buttonsVisible) return;
    const intervalId = setInterval(() => {
      setTimer((t) => {
        if (t <= 1) clearInterval(intervalId);
        return t - 1;
      });
    }, 1000);
    return () => clearInterval(intervalId);
  }, [userCreated, buttonsVisible]);

  useEffect(() => {
    if (!userCreated || !buttonsVisible) return;
    if (timer > 0) return;
    endGame();
    // eslint-disable-next-line react-hooks/set-state-in-effect
    setButtonsVisible(false);
  }, [timer, userCreated, buttonsVisible]);

  return (
    <Box
      sx={{
        display: "flex",
        alignItems: "center",
        height: "100vh",
        flexDirection: "column",
      }}
    >
      {!userCreated ? (
        <>
          <Box>
            <TextField
              type="text"
              label="Enter player name:"
              onChange={(e) => {
                handleNameChange(e);
              }}
              sx={{
                width: 250,
                "& input": { textAlign: "center", fontWeight: 600 },
              }}
            />
          </Box>
          <Button
            onClick={createUserAndSession}
            variant="contained"
            disabled={!playerNameEntered}
          >
            Start Game
          </Button>
        </>
      ) : (
        <>
          {!gameStarted ? (
            <Box
              sx={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                alignItems: "center",
                textAlign: "center",
              }}
            >
              <Typography>
                After drawing card you have the option to decide if the next
                card is HIGHER, LOWER or EQUAL.
              </Typography>
              <Typography>
                If your choice is correct you get +1 point, if you are wrong you
                lose a life.
              </Typography>
              <Typography>You have 10 seconds to decide</Typography>
              <Button onClick={drawCard} variant="contained">
                Draw Card
              </Button>
            </Box>
          ) : (
            <>
              <Box
                sx={{
                  display: "flex",
                  flexDirection: "row",
                  width: "100%",
                  justifyContent: "space-evenly",
                  alignItems: "center",
                  p: 3,
                }}
              >
                <Typography variant="h6">Lives: {lives}</Typography>
                <Typography variant="h6" fontWeight={"bold"}>
                  Time: {timer}s
                </Typography>
                <Typography variant="h6">Score: {score}</Typography>
              </Box>
              <Box
                sx={{
                  display: "flex",
                  flexDirection: "column",
                  justifyContent: "center",
                  alignItems: "center",
                  fontSize: "1.8rem",
                  textAlign: "center",
                }}
              >
                {card?.previousCard.rank && (
                  <>
                    <Typography variant="h6">
                      Previous Card: {card?.previousCard.rank}
                      {" of "}
                      {card?.previousCard.suit}
                    </Typography>
                  </>
                )}
                <Card code={convertCard(card?.card.rank, card?.card.suit)} />
                <Typography variant="h4">
                  Current Card: {card?.card.rank} of {card?.card.suit}
                </Typography>
              </Box>
              {buttonsVisible ? (
                <Box
                  sx={{
                    display: "flex",
                    justifyContent: "center",
                    gap: 2,
                    width: "100%",
                  }}
                >
                  <Button onClick={() => sendGuess("HIGHER")}>Higher</Button>
                  <Button onClick={() => sendGuess("EQUAL")}>Equal</Button>
                  <Button onClick={() => sendGuess("LOWER")}>Lower</Button>
                </Box>
              ) : (
                <Box sx={{ textAlign: "center", mt: 2 }}>
                  <Typography>Time's up or Game Over!</Typography>
                  <Button onClick={startNewGame} variant="contained">
                    Start New Game
                  </Button>
                </Box>
              )}
            </>
          )}
        </>
      )}
    </Box>
  );
}

export default Game;
