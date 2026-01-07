import type { Card } from "./Card";

export type GameState = {
  card: Card;
  previousCard: Card;
  lives: number;
  score: number;
};
