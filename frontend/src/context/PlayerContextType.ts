import type { Player } from "../models/Player";

export type PlayerContextType = {
  player: Player | null;
  sessionId: string;
  isLoggedIn: boolean;
  saveSession: (id: string) => void;
  removeSession: () => void;
  removePlayer: () => void;
  savePlayer: (player: Player) => void;
};
