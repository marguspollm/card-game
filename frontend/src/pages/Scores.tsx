import { useEffect, useState } from "react";
import type { Score } from "../models/Score";
import {
  TableContainer,
  Paper,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  TableSortLabel,
  Typography,
} from "@mui/material";
import { Link } from "react-router";
import { formatDuration } from "../utils/TimeFormat";
import type { Order } from "../models/Order";

const backendUrl = import.meta.env.VITE_API_HOST;

function Scores() {
  const [scores, setScores] = useState<Score[]>([]);
  const [order, setOrder] = useState<Order>("desc");
  const [orderBy, setOrderBy] = useState<keyof Score>("score");

  const handleRequestSort = (property: keyof Score) => {
    const isAsc = orderBy === property && order === "asc";
    setOrder(isAsc ? "desc" : "asc");
    setOrderBy(property);
  };

  const sortedScores = [...scores].sort((a, b) => {
    const aValue = a[orderBy];
    const bValue = b[orderBy];
    if (aValue < bValue) return order === "asc" ? -1 : 1;
    if (aValue > bValue) return order === "asc" ? 1 : -1;
    return 0;
  });

  useEffect(() => {
    const getData = async () => {
      await fetch(`${backendUrl}/scores`)
        .then(res => res.json())
        .then(r => setScores(r));
    };

    getData();
  }, []);

  return (
    <div>
      <Typography variant="h5" sx={{ mb: 3 }}>
        LeaderBoard
      </Typography>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>Name</TableCell>
              <TableCell align="right">
                <TableSortLabel
                  active={orderBy === "score"}
                  direction={orderBy === "score" ? order : "asc"}
                  onClick={() => handleRequestSort("score")}
                >
                  Score
                </TableSortLabel>
              </TableCell>
              <TableCell align="right">
                <TableSortLabel
                  active={orderBy === "duration"}
                  direction={orderBy === "duration" ? order : "asc"}
                  onClick={() => handleRequestSort("duration")}
                >
                  Time
                </TableSortLabel>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {sortedScores?.map(row => (
              <TableRow
                key={row.id}
                sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              >
                <TableCell component="th" scope="row" key={row.player.id}>
                  <Link to={`/scores/player/${row.player.id}`}>
                    {row.player.name}
                  </Link>
                </TableCell>
                <TableCell align="right">{row.score}</TableCell>
                <TableCell align="right">
                  {formatDuration(row.duration)}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
}

export default Scores;
