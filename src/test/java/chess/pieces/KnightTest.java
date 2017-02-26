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

public class KnightTest {

    GameState state;

    @Before
    public void before() {
        state = mock(GameState.class);
    }

    @Test
    public void movesBlocked() {
        Position from = new Position("d4");
        Piece piece = new Knight(Black);
        TestUtils.setPiecesOnBoard(Black, state, "c6", "e6" ,"f5" , "f3", "c2", "e2", "b3", "b5");
        List<String> toPositions = TestUtils.getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(0));
    }

    @Test
    public void movesBlockedEnemy() {
        Position from = new Position("d4");
        Piece piece = new Knight(Black);
        TestUtils.setPiecesOnBoard(White, state, "c6", "e6" ,"f5" , "f3", "c2", "e2", "b3", "b5");
        List<String> toPositions = TestUtils.getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(8));
        assertThat(toPositions, hasItems("c6", "e6" ,"f5" , "f3", "c2", "e2", "b3", "b5"));
    }

    @Test
    public void movesNotBlocked() {
        Position from = new Position("d4");
        Piece piece = new Knight(Black);
        List<String> toPositions = TestUtils.getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(8));
        assertThat(toPositions, hasItems("c6", "e6" ,"f5" , "f3", "c2", "e2", "b3", "b5"));
    }
}