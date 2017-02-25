package chess.pieces;

import chess.GameState;
import chess.Position;
import chess.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static chess.Player.Black;
import static chess.Player.White;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class RookTest {

    GameState state;

    @Before
    public void before() {
        state = mock(GameState.class);
    }

    @Test
    public void movesBlocked() {
        Position from = new Position("b2");
        Piece piece = new Rook(Black);
        TestUtils.setPiecesOnBoard(Black, state, "b1", "b3", "a2", "c2");
        assertThat(TestUtils.getToPositions(piece, from, state).size(), is(0));
    }

    @Test
    public void movesBlockedEnemy() {
        Position from = new Position("a1");
        Piece piece = new Rook(Black);
        TestUtils.setPiecesOnBoard(White, state, "b1", "a2");
        List<String> toPositions = TestUtils.getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(2));
        assertThat(toPositions, hasItems("b1", "a2"));
    }

    @Test
    public void movesNotBlocked() {
        Position from = new Position("h7");
        Piece piece = new Rook(Black);
        List<String> toPositions = TestUtils.getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(14));
        assertThat(toPositions, hasItems(
                "h1", "h2", "h3", "h4", "h5", "h6", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7"
        ));
    }

}