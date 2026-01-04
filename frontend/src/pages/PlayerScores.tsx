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
  TableSortLabel,
} from "@mui/material";
import type { Order } from "../models/Order";

const backendUrl = import.meta.env.VITE_API_HOST;

function PlayerScores() {
  const { id } = useParams();
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
    fetch(`${backendUrl}/player/${id}/scores`)
      .then((res) => res.json())
      .then((r) => setScores(r));
  }, [id]);

  return (
    <>
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell align="left">
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
                  Duration
                </TableSortLabel>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {sortedScores?.map((row) => (
              <TableRow key={row.id}>
                <TableCell align="left">{row.score}</TableCell>
                <TableCell align="right">
                  {formatDuration(row.duration)}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
}

export default PlayerScores;
