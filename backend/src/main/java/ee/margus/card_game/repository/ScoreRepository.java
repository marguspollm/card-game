package ee.margus.card_game.repository;

import ee.margus.card_game.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByPlayer_IdOrderByScoreAsc(Long id);
}
