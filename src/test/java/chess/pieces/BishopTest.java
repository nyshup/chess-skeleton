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

public class BishopTest {

    GameState state;

    @Before
    public void before() {
        state = mock(GameState.class);
    }

    @Test
    public void movesBlocked() {
        Position from = new Position("b2");
        Piece piece = new Bishop(Black);
        TestUtils.setPiecesOnBoard(Black, state, "a1", "c3", "a3", "c1");
        assertThat(TestUtils.getToPositions(piece, from, state).size(), is(0));
    }

    @Test
    public void movesBlockedWithEnemy() {
        Position from = new Position("b2");
        Piece piece = new Bishop(Black);
        TestUtils.setPiecesOnBoard(White, state, "a1", "c3", "a3", "c1");
        List<String> toPositions = TestUtils.getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(4));
        assertThat(toPositions, hasItems("a1", "c3", "a3", "c1"));
    }

    @Test
    public void movesBlockedNoBlocked() {
        Position from = new Position("b2");
        Piece piece = new Bishop(Black);
        List<String> toPositions = TestUtils.getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(9));
        assertThat(toPositions, hasItems("a3", "c1", "a1", "h8"));
    }

}