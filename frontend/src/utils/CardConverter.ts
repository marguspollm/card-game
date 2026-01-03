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

export const cardMap: Record<
  string,
  React.FunctionComponent<React.SVGProps<SVGSVGElement>>
> = {
  AS,
  KS,
  QS,
  JS,
  TS,
  "9S": _9S,
  "8S": _8S,
  "7S": _7S,
  "6S": _6S,
  "5S": _5S,
  "4S": _4S,
  "3S": _3S,
  "2S": _2S,

  AH,
  KH,
  QH,
  JH,
  TH,
  "9H": _9H,
  "8H": _8H,
  "7H": _7H,
  "6H": _6H,
  "5H": _5H,
  "4H": _4H,
  "3H": _3H,
  "2H": _2H,

  AD,
  KD,
  QD,
  JD,
  TD,
  "9D": _9D,
  "8D": _8D,
  "7D": _7D,
  "6D": _6D,
  "5D": _5D,
  "4D": _4D,
  "3D": _3D,
  "2D": _2D,

  AC,
  KC,
  QC,
  JC,
  TC,
  "9C": _9C,
  "8C": _8C,
  "7C": _7C,
  "6C": _6C,
  "5C": _5C,
  "4C": _4C,
  "3C": _3C,
  "2C": _2C,

  "1B": _1B,
};

import _1B from "../cards/1B.svg?react";
import AS from "../cards/AS.svg?react";
import KS from "../cards/KS.svg?react";
import QS from "../cards/QS.svg?react";
import JS from "../cards/JS.svg?react";
import TS from "../cards/TS.svg?react";
import _9S from "../cards/9S.svg?react";
import _8S from "../cards/8S.svg?react";
import _7S from "../cards/7S.svg?react";
import _6S from "../cards/6S.svg?react";
import _5S from "../cards/5S.svg?react";
import _4S from "../cards/4S.svg?react";
import _3S from "../cards/3S.svg?react";
import _2S from "../cards/2S.svg?react";

import AH from "../cards/AH.svg?react";
import KH from "../cards/KH.svg?react";
import QH from "../cards/QH.svg?react";
import JH from "../cards/JH.svg?react";
import TH from "../cards/TH.svg?react";
import _9H from "../cards/9H.svg?react";
import _8H from "../cards/8H.svg?react";
import _7H from "../cards/7H.svg?react";
import _6H from "../cards/6H.svg?react";
import _5H from "../cards/5H.svg?react";
import _4H from "../cards/4H.svg?react";
import _3H from "../cards/3H.svg?react";
import _2H from "../cards/2H.svg?react";

import AD from "../cards/AD.svg?react";
import KD from "../cards/KD.svg?react";
import QD from "../cards/QD.svg?react";
import JD from "../cards/JD.svg?react";
import TD from "../cards/TD.svg?react";
import _9D from "../cards/9D.svg?react";
import _8D from "../cards/8D.svg?react";
import _7D from "../cards/7D.svg?react";
import _6D from "../cards/6D.svg?react";
import _5D from "../cards/5D.svg?react";
import _4D from "../cards/4D.svg?react";
import _3D from "../cards/3D.svg?react";
import _2D from "../cards/2D.svg?react";

import AC from "../cards/AC.svg?react";
import KC from "../cards/KC.svg?react";
import QC from "../cards/QC.svg?react";
import JC from "../cards/JC.svg?react";
import TC from "../cards/TC.svg?react";
import _9C from "../cards/9C.svg?react";
import _8C from "../cards/8C.svg?react";
import _7C from "../cards/7C.svg?react";
import _6C from "../cards/6C.svg?react";
import _5C from "../cards/5C.svg?react";
import _4C from "../cards/4C.svg?react";
import _3C from "../cards/3C.svg?react";
import _2C from "../cards/2C.svg?react";
