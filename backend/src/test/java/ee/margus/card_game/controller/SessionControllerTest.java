package ee.margus.card_game.controller;

import ee.margus.card_game.dto.StartGameDTO;
import ee.margus.card_game.entity.Player;
import ee.margus.card_game.service.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(SessionController.class)
class SessionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private SessionService sessionService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createSession() throws Exception {
        Player player = new Player();
        player.setId(1L);
        player.setName("test");
        StartGameDTO request = new StartGameDTO();
        request.setPlayer(player);
        StartGameDTO response = new StartGameDTO();
        response.setSessionId("test-session-id");
        response.setPlayer(player);

        when(sessionService.createSession(any(StartGameDTO.class))).thenReturn(response);

        mockMvc.perform(post("/session")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").value("test-session-id"));


    }
}