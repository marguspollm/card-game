import { useContext, useEffect, useState } from "react";
import type { GameState } from "../models/GameState";
import type { GuessType } from "../models/GuessType";
import { Box, Button, Typography } from "@mui/material";
import type { GameRequest } from "../models/requests/GameRequest";
import GuessButtons from "../components/Game/GuessButtons";
import GameBar from "../components/Game/GameBar";
import CardDisplay from "../components/Game/CardDisplay";
import { PlayerContext } from "../context/PlayerContext";
import "../css/Game.css";
import { useNavigate } from "react-router";

const backendUrl = import.meta.env.VITE_API_HOST;

function Game() {
  const { isLoggedIn, player, sessionId, saveSession, removeSession } =
    useContext(PlayerContext);
  const navigate = useNavigate();
  const [gameState, setGameState] = useState<GameState>();

  const [gameStarted, setGameStarted] = useState(false);
  const [gameOver, setGameOver] = useState("");
  const [guessResult, setGuessResult] = useState<"correct" | "wrong" | "">("");

  const [score, setScore] = useState(0);
  const [lives, setLives] = useState(3);
  const [timer, setTimer] = useState(10);

  async function createSession() {
    const payload = { player };
    try {
      const res = await fetch(`${backendUrl}/session`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      const data = await res.json();
      saveSession(data.sessionId);
      return data.sessionId;
    } catch (err) {
      console.log("Error creating session:", err);
    }
  }

  async function startGame(id: string) {
    const payload: GameRequest = { sessionId: id };
    try {
      const res = await fetch(`${backendUrl}/game/start-game`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      const data = await res.json();
      setGameState(data);
    } catch (err) {
      console.log("Error starting game:", err);
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

      setGameState(data);
      setLives(data.lives);
      setScore(data.score);

      if (data.correct === true) {
        setGuessResult("correct");
      } else if (data.correct === false) {
        setGuessResult("wrong");
      }

      setTimeout(() => setGuessResult(""), 1000);

      if (data.status === "TIME_OUT") {
        setGameOver("Game over. Time ran out.");
        return;
      }
      if (data.status === "LIVES_LOST") {
        setGameOver("Game over. No more lives.");
        return;
      }

      setTimer(10);
    } catch (err) {
      console.log("Guess error:", err);
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
      removeSession();
    } catch (err) {
      console.log("End game error:", err);
    }
  }

  async function drawCard() {
    await createSession().then(id => {
      setGameStarted(true);
      setScore(0);
      setLives(3);
      setTimer(10);
      setGameOver("");
      startGame(id);
    });
  }

  useEffect(() => {
    if (!gameStarted || gameOver) return;
    const intervalId = setInterval(() => {
      setTimer(t => {
        if (t <= 1) {
          clearInterval(intervalId);
          setGameOver("Game over. Time ran out.");
          endGame();
          return 0;
        }
        return t - 1;
      });
    }, 1000);
    return () => clearInterval(intervalId);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [gameStarted, gameOver]);

  useEffect(() => {
    if (!isLoggedIn) {
      console.log(isLoggedIn);
      navigate("/login");
    }
  }, []);

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        height: "100vh",
      }}
    >
      {!gameStarted ? (
        <Box sx={{ textAlign: "center" }}>
          <Typography>
            After drawing card you have the option to decide if the next card is
            HIGHER, LOWER or EQUAL.
          </Typography>
          <Typography>
            If your choice is correct you get +1 point, if you are wrong you
            lose a life.
          </Typography>
          <Typography>You have 10 seconds to decide</Typography>
          <Button variant="contained" sx={{ mt: 2 }} onClick={drawCard}>
            Draw Card
          </Button>
        </Box>
      ) : (
        <>
          <GameBar
            lives={lives}
            timer={timer}
            score={score}
            guessResult={guessResult}
          />
          {!gameOver ? (
            <GuessButtons sendGuess={sendGuess} />
          ) : (
            <Box sx={{ textAlign: "center", mt: 3 }}>
              <Typography variant="h5">{gameOver}</Typography>
              <Button variant="contained" sx={{ mt: 2 }} onClick={drawCard}>
                Start New Game
              </Button>
            </Box>
          )}
          {gameState && (
            <CardDisplay card={gameState} guessResult={guessResult} />
          )}
        </>
      )}
    </Box>
  );
}

export default Game;
