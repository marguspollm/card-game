import { memo } from "react";

const Card = memo(function Card({ code }: { code: string }) {
  return <img src={`/cards/${code}.svg`} alt={code} />;
});

export default Card;
