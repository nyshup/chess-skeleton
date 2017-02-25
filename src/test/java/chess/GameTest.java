package chess;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    Game game;
    GameState state;

    @Before
    public void before() {
        state = mock(GameState.class);
        game = new Game(state);
    }

    @Test
    public void testGetMoves() {
        when(state.getMoves()).thenReturn(Stream.of(
                new Move(new Position("a1"), new Position("a2"), null),
                new Move(new Position("b1"), new Position("b2"), null))
        );
        assertThat(game.getMoves().collect(toList()), hasItems("a1 a2", "b1 b2"));
    }

    @Test
    public void testGetCurrentPlayer() {
        when(state.getCurrentPlayer()).thenReturn(Player.White);
        assertThat(game.getCurrentPlayer(), equalTo(Player.White));
    }

    @Test
    public void testDoMoveSucced() {
        Move m1 = new Move(new Position("a1"), new Position("a2"), null);
        Move m2 = new Move(new Position("b1"), new Position("b2"), null);
        when(state.getMoves()).thenReturn(Stream.of(m1, m2));
        assertTrue(game.doMove("move a1 a2"));
        verify(state).move(m1);
    }

    public void testGetStatus() {
        when(state.getStatus()).thenReturn(GameState.Status.BLACK_WINNER);
        assertThat(game.getStatus(), equalTo(GameState.Status.BLACK_WINNER));

    }



}