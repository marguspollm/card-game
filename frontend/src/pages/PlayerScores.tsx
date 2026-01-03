import { useEffect, useState } from "react";
import { useParams } from "react-router";
import type { Score } from "../models/Score";
import { formatDuration } from "../utils/TimeFormat";
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";

const backendUrl = import.meta.env.VITE_API_HOST;

function PlayerScores() {
  const { id } = useParams();
  const [scores, setScores] = useState<Score[]>([]);

  useEffect(() => {
    fetch(`${backendUrl}/player/${id}/scores`)
      .then((res) => res.json())
      .then((r) => setScores(r));
  }, [id]);

  return (
    <div>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="right">Score</TableCell>
              <TableCell align="right">Time</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {scores?.map((row) => (
              <TableRow
                key={row.id}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell
                  component="th"
                  scope="row"
                  key={row.player.id}
                ></TableCell>
                <TableCell align="right">{row.score}</TableCell>
                <TableCell align="right">
                  {formatDuration(row.duration)}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {
        <div>
          {scores.map((score, index) => {
            return (
              <div key={score.id}>
                Game: #{index + 1}
                <br />
                Score: {score.score} - Duration:{" "}
                {formatDuration(score.duration)}
              </div>
            );
          })}
        </div>
      }
    </div>
  );
}

export default PlayerScores;
