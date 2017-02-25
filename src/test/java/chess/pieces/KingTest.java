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
public class KingTest {

    GameState state;

    @Before
    public void before() {
        state = mock(GameState.class);
    }

    @Test
    public void movesBlocked() {
        Position from = new Position("d4");
        Piece piece = new King(Black);
        TestUtils.setPiecesOnBoard(Black, state, "c3", "c4" ,"c5" , "d3", "d5", "e3", "e4", "e5");
        List<String> toPositions = TestUtils.getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(0));
    }

    @Test
    public void movesBlockedEnemy() {
        Position from = new Position("d4");
        Piece piece = new King(Black);
        TestUtils.setPiecesOnBoard(White, state, "c3", "c4" ,"c5" , "d3", "d5", "e3", "e4", "e5");
        List<String> toPositions = TestUtils.getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(8));
        assertThat(toPositions, hasItems("c3", "c4" ,"c5" , "d3", "d5", "e3", "e4", "e5"));
    }

    @Test
    public void movesNotBlocked() {
        Position from = new Position("d4");
        Piece piece = new King(Black);
        List<String> toPositions = TestUtils.getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(8));
        assertThat(toPositions, hasItems("c3", "c4" ,"c5" , "d3", "d5", "e3", "e4", "e5"));
    }

}