import { createContext } from "react";
import type { Player } from "../models/Player";
import type { PlayerContextType } from "./PlayerContextType";

export const PlayerContext = createContext<PlayerContextType>({
  player: null,
  sessionId: "",
  isLoggedIn: false,
  saveSession: (id: string) => {
    console.log(id);
  },
  removeSession: () => {},
  removePlayer: () => {},
  savePlayer: (player: Player) => {
    console.log(player);
  },
});
