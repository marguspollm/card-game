import type { Card } from "./Card";

export type CardGame = {
  card: Card;
  previousCard: Card;
  lives: number;
  score: number;
};
