import { cardMap } from "../utils/CardConverter";

function Card({ code }: { code: string }) {
  const CardSvg = cardMap[code];
  return <CardSvg width={"100"} height={"140px"} />;
}

export default Card;
