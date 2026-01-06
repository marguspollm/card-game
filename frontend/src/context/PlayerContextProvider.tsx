import { type ReactNode, useState } from "react";
import { PlayerContext } from "./PlayerContext";
import type { Player } from "../models/Player";

export const PlayerContextProvider = ({
  children,
}: {
  children: ReactNode;
}) => {
  const [player, setPlayer] = useState<Player | null>(() => {
    const saved = sessionStorage.getItem("player");
    return saved ? JSON.parse(saved) : null;
  });
  const [sessionId, setSessionId] = useState(() => {
    const saved = sessionStorage.getItem("sessionId");
    return saved ? JSON.parse(saved) : null;
  });
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  function removePlayer() {
    setPlayer(null);
    setIsLoggedIn(false);
    sessionStorage.removeItem("player");
  }

  function savePlayer(player: Player) {
    setPlayer(player);
    setIsLoggedIn(true);
    sessionStorage.setItem("player", JSON.stringify(player));
  }

  function saveSession(id: string) {
    setSessionId(id);
    sessionStorage.setItem("session", JSON.stringify(id));
  }

  function removeSession() {
    sessionStorage.removeItem("session");
  }

  return (
    <PlayerContext.Provider
      value={{
        player,
        sessionId,
        isLoggedIn,
        saveSession,
        removeSession,
        savePlayer,
        removePlayer,
      }}
    >
      {children}
    </PlayerContext.Provider>
  );
};
