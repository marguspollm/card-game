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
    void createSessionAndPlayer() {
        StartGameDTO request = new StartGameDTO();
        Player requestPlayer = new Player();
        requestPlayer.setName("Test");
        request.setPlayer(requestPlayer);

        StartGameDTO response = new StartGameDTO();
        Player responsePlayer = new Player();
        responsePlayer.setName("Test");
        responsePlayer.setId(1L);
        response.setSessionId(uuid);
        response.setPlayer(responsePlayer);

        when(randomGenerator.generate()).thenReturn(uuid);
        when(playerService.create(any(Player.class))).thenReturn(responsePlayer);

        assertEquals(response, sessionService.createSession(request));
        assertNotNull(sessionService.getGameState(response.getSessionId()));
    }

    @Test
    void createNewSessions() {
        StartGameDTO request = new StartGameDTO();
        Player player = new Player();
        player.setName("Test");
        player.setId(1L);
        request.setPlayer(player);

        StartGameDTO response = new StartGameDTO();
        response.setPlayer(player);
        response.setSessionId(uuid);

        when(randomGenerator.generate()).thenReturn(uuid);

        assertEquals(response, sessionService.createSession(request));
        assertNotNull(sessionService.getGameState(response.getSessionId()));
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