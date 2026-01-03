import type { Player } from "./Player";

export type Score = {
  id: number;
  player: Player;
  score: number;
  duration: number;
};
