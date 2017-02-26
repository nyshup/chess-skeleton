package chess.pieces;

import chess.GameState;
import chess.Position;
import chess.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static chess.Player.Black;
import static chess.Player.White;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.mockito.Mockito.mock;

/**
 * Created by ruslan on 2/25/17.
 */
public class QueenTest {
    GameState state;

    @Before
    public void before() {
        state = mock(GameState.class);
    }

    @Test
    public void movesBlocked() {
        Position from = new Position("b2");
        Piece piece = new Queen(Black);
        TestUtils.setPiecesOnBoard(Black, state, "a1", "a2", "a3" , "b1", "b3", "c1", "c2", "c3");
        List<String> toPositions = TestUtils.getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(0));
    }

    @Test
    public void movesBlockedEnemy() {
        Position from = new Position("b2");
        Piece piece = new Queen(Black);
        TestUtils.setPiecesOnBoard(White, state, "a1", "a2", "a3" , "b1", "b3", "c1", "c2", "c3");
        List<String> toPositions = TestUtils.getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(8));
        assertThat(toPositions, hasItems("a1", "a2", "a3" , "b1", "b3", "c1", "c2", "c3"));
    }

    @Test
    public void movesNotBlocked() {
        Position from = new Position("b2");
        Piece piece = new Queen(Black);
        List<String> toPositions = TestUtils.getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(23));
        assertThat(toPositions, hasItems("a1", "a3", "c1", "h8", "a2", "b1", "h2", "b8"));
    }
}