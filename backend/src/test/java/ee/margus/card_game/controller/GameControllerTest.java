package ee.margus.card_game.controller;

import ee.margus.card_game.dto.CardGameDTO;
import ee.margus.card_game.dto.GameRequest;
import ee.margus.card_game.model.GameState;
import ee.margus.card_game.model.Guess;
import ee.margus.card_game.service.GameService;
import ee.margus.card_game.service.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@WebMvcTest(GameController.class)
public class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private SessionService sessionService;
    @MockitoBean
    private GameService gameService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testEndGame() throws Exception {
        GameState gameState = mock(GameState.class);
        GameRequest request = new GameRequest("test-session-id", null);
        when(sessionService.getGameState("test-session-id")).thenReturn(gameState);
        mockMvc.perform(post("/game/end-game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
        verify(gameService).endGame(gameState);

    }

    @Test
    void testGuess() throws Exception {
        GameState gameState = mock(GameState.class);
        CardGameDTO dto = new CardGameDTO();
        GameRequest request = new GameRequest("test-session-id", Guess.HIGHER);

        when(sessionService.getGameState("test-session-id")).thenReturn(gameState);
        when(gameService.guess(gameState, Guess.HIGHER)).thenReturn(dto);

        mockMvc.perform(post("/game/guess")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }

    @Test
    void testStartGame() throws Exception {
        GameState gameState = mock(GameState.class);
        CardGameDTO dto = new CardGameDTO();
        GameRequest request = new GameRequest("test-session-id", null);

        when(sessionService.getGameState("test-session-id")).thenReturn(gameState);
        when(gameService.start(gameState)).thenReturn(dto);

        mockMvc.perform(post("/game/start-game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
