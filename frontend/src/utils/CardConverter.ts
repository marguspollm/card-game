const rankMap: Record<string, string> = {
  ACE: "A",
  TWO: "2",
  THREE: "3",
  FOUR: "4",
  FIVE: "5",
  SIX: "6",
  SEVEN: "7",
  EIGHT: "8",
  NINE: "9",
  TEN: "T",
  JACK: "J",
  QUEEN: "Q",
  KING: "K",
};
const suitMap: Record<string, string> = {
  HEARTS: "H",
  DIAMONDS: "D",
  CLUBS: "C",
  SPADES: "S",
};

export function convertCard(
  rank: string | undefined,
  suit: string | undefined
): string {
  if (!rank || !suit) {
    return "1B";
  }
  return rankMap[rank.toUpperCase()] + suitMap[suit.toUpperCase()];
}
