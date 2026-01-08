import { Box, Typography } from "@mui/material";
import Card from "../components/Game/Card";

function Home() {
  const cards = ["2C", "AD", "KS", "8C", "JH"];
  return (
    <Box
      sx={{
        display: "flex",
        alignItems: "center",
        flexDirection: "column",
        gap: 5,
      }}
    >
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "flex-end",
          gap: 0,
        }}
      >
        {cards.map((code, i) => {
          const count = cards.length;
          const spread = 10;
          const angle = (i - (count - 1) / 2) * spread;
          return (
            <Box
              key={code}
              sx={{
                width: 120,
                height: 180,
                marginLeft: i === 0 ? 0 : "-90px",
                transform: `rotate(${angle}deg)`,
                transformOrigin: "center bottom",
                transition: "transform 0.3s ease, box-shadow 0.3s ease",
                "& img": { width: "100%", height: "100%" },
              }}
            >
              <Card code={code} />
            </Box>
          );
        })}
      </Box>
      <Typography variant="h4" sx={{ fontWeight: 700 }}>
        The Higer/Lower card game
      </Typography>
    </Box>
  );
}

export default Home;
