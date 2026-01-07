package ee.margus.card_game.controller;

import ee.margus.card_game.entity.Player;
import ee.margus.card_game.entity.Score;
import ee.margus.card_game.service.ScoresService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(ScoresController.class)
class ScoresControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ScoresService scoresService;

    @Test
    void getAllScores() throws Exception {
        List<Score> scores = List.of(
                new Score(1L, new Player(), 10, 1000L),
                new Score(2L, new Player(), 20, 2000L));

        when(scoresService.getAll()).thenReturn(scores);

        mockMvc.perform(get("/scores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].score").value(10));
    }

    @Test
    void getPlayerScores() throws Exception {
        List<Score> scores = List.of(
                new Score(1L, new Player(), 10, 1000L),
                new Score(2L, new Player(), 15, 1500L));
        when(scoresService.getPlayerScores(5L)).thenReturn(scores);
        mockMvc.perform(get("/scores/player/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].score").value(10))
                .andExpect(jsonPath("$[1].score").value(15));
    }

}