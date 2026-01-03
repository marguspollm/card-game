import type { GuessType } from "../GuessType";

export type GameRequest = {
  sessionId: string;
  guess?: GuessType;
};
