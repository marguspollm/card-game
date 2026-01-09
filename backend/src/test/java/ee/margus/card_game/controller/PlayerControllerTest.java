package ee.margus.card_game.controller;

import ee.margus.card_game.dto.PlayerDTO;
import ee.margus.card_game.entity.Player;
import ee.margus.card_game.service.PlayerService;
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
@WebMvcTest(PlayerController.class)
class PlayerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private PlayerService playerService;

    @Test
    void loginAndCreatePlayer() throws Exception {
        PlayerDTO request = new PlayerDTO();
        request.setName("test");
        Player response = new Player();
        response.setId(1L);
        response.setName("test");

        when(playerService.login(any(PlayerDTO.class))).thenReturn(response);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void loginAndExistingPlayer() throws Exception {
        PlayerDTO request = new PlayerDTO();
        request.setName("test");
        Player response = new Player();
        response.setId(1L);
        response.setName("test");

        when(playerService.login(any(PlayerDTO.class))).thenReturn(response);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.id").value(1L));
    }
}