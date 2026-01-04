package ee.margus.card_game.service;

import ee.margus.card_game.entity.Player;
import ee.margus.card_game.entity.Score;
import ee.margus.card_game.model.Deck;
import ee.margus.card_game.model.GameSession;
import ee.margus.card_game.repository.ScoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScoresServiceTest {

    @Mock
    private ScoreRepository scoreRepository;
    @InjectMocks
    private ScoresService scoresService;

    @Test
    void getAll() {
        List<Score> scores = List.of(new Score());
        when(scoreRepository.findAll()).thenReturn(scores);
        assertEquals(scores, scoresService.getAll());
        verify(scoreRepository).findAll();
    }

    @Test
    void saveResult() {
        Deck deck = mock(Deck.class);
        GameSession session = new GameSession("test-uuid", deck);
        session.setScore(50);
        Player player = new Player();
        session.setPlayer(player);
        scoresService.saveResult(session);
        ArgumentCaptor<Score> captor = ArgumentCaptor.forClass(Score.class);
        verify(scoreRepository).save(captor.capture());
        Score saved = captor.getValue();
        assertEquals(50, saved.getScore());
        assertEquals(player, saved.getPlayer());
    }

    @Test
    void getPlayerScores() {
        List<Score> scores = List.of(new Score());
        when(scoreRepository.findByPlayer_IdOrderByScoreAsc(1L)).thenReturn(scores);
        assertEquals(scores, scoresService.getPlayerScores(1L));
        verify(scoreRepository).findByPlayer_IdOrderByScoreAsc(1L);
    }
}