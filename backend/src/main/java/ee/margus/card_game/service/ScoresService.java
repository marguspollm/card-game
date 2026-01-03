package ee.margus.card_game.service;

import ee.margus.card_game.entity.Score;
import ee.margus.card_game.model.GameSession;
import ee.margus.card_game.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoresService {

    @Autowired
    private ScoreRepository scoreRepository;

    public List<Score> getAll() {
        return scoreRepository.findAll();
    }

    public void saveResult(GameSession gameSession) {
        Score entry = new Score();
        entry.setDuration(gameSession.getDuration());
        entry.setScore(gameSession.getScore());
        entry.setPlayer(gameSession.getPlayer());
        scoreRepository.save(entry);
    }

    public List<Score> getPlayerScores(Long id) {
        return scoreRepository.findByPlayer_IdOrderByScoreAsc(id);
    }
}
