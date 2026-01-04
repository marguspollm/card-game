package ee.margus.card_game.service;

import ee.margus.card_game.entity.Player;
import ee.margus.card_game.repository.PlayerRepository;
import org.hibernate.annotations.processing.Exclude;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks
    private PlayerService playerService;

    @Test
    void create() {
        Player player = new Player();
        player.setId(1L);
        player.setName("Test");

        when(playerRepository.save(any(Player.class))).thenReturn(player);

        assertEquals(player, playerService.create("Test"));
    }
}