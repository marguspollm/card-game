package ee.margus.card_game.service;

import ee.margus.card_game.dto.StartGameDTO;
import ee.margus.card_game.entity.Player;
import ee.margus.card_game.util.RandomGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {
    @Mock
    private PlayerService playerService;
    @InjectMocks
    private SessionService sessionService;
    @Mock
    private RandomGenerator randomGenerator;
    String uuid = "test-random-uuid";


    @Test
    void givenPlayer_whenCreateSession_returnNewSession() {
        StartGameDTO request = new StartGameDTO();
        Player player = new Player();
        player.setName("Test");
        player.setId(1L);
        request.setPlayer(player);

        StartGameDTO response = new StartGameDTO();
        response.setSessionId(uuid);
        response.setPlayer(player);

        when(randomGenerator.generate()).thenReturn(uuid);
        when(playerService.getPlayer(any(Long.class))).thenReturn(player);

        assertEquals(response, sessionService.createSession(request));
        assertNotNull(sessionService.getGameState(response.getSessionId()));
    }

    @Test
    void givenNoPlayer_whenCreateNewSessions_thenThrowException() {
        StartGameDTO request = new StartGameDTO();
        assertThrows(RuntimeException.class, () -> sessionService.createSession(request));
    }

    @Test
    void givenNotExistingSession_whenGetSession_throwException() {
        assertThrows(RuntimeException.class, () -> sessionService.getGameState("1"));
    }

    @Test
    void givenExistingSession_whenGetSession_thenReturnGameSession() {
        StartGameDTO request = new StartGameDTO();
        Player player = new Player();
        player.setName("Test");
        player.setId(1L);
        request.setPlayer(player);

        when(randomGenerator.generate()).thenReturn(uuid);

        sessionService.createSession(request);
        assertNotNull(sessionService.getGameState(uuid));
    }

    @Test
    void deleteSession() {
        StartGameDTO request = new StartGameDTO();
        Player player = new Player();
        player.setName("Test");
        player.setId(1L);
        request.setPlayer(player);

        when(randomGenerator.generate()).thenReturn(uuid);

        sessionService.createSession(request);
        assertNotNull(sessionService.getGameState(uuid));
        sessionService.deleteSession(uuid);
        assertThrows(RuntimeException.class, () -> sessionService.getGameState("1"));
    }
}