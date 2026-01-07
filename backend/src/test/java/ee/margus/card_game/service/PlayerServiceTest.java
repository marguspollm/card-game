package ee.margus.card_game.service;

import ee.margus.card_game.entity.Player;
import ee.margus.card_game.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks
    private PlayerService playerService;

    @Test
    void create() {
        Player request = new Player();
        request.setName("Test");
        Player player = new Player();
        player.setId(1L);
        player.setName("Test");

        when(playerRepository.save(any(Player.class))).thenReturn(player);

        assertEquals(player, playerService.create(request));
        verify(playerRepository).save(request);
    }

    @Test
    void testGetPlayer() {
        Player player = new Player();
        player.setId(1L);
        player.setName("Test");
        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));

        assertEquals(player, playerService.getPlayer(1L));
        verify(playerRepository).findById(1L);
    }

    @Test
    void testGetPlayerDoesntExist() {
        Player player = new Player();
        player.setId(1L);
        player.setName("Test");
        when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> playerService.getPlayer(1L));

        verify(playerRepository).findById(1L);
    }
}