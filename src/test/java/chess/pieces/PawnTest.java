package chess.pieces;

import chess.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static chess.Player.Black;
import static chess.Player.White;
import static chess.TestUtils.getToPositions;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class PawnTest {

    GameState state;

    @Before
    public void before() {
        state = mock(GameState.class);
    }

    @Test
    public void testNoMovesBlockedEnemyWhite() {
        Position from = new Position("b2");
        Piece piece = new Pawn(White);
        TestUtils.setPiecesOnBoard(Black, state, "b3");
        assertThat(getToPositions(piece, from, state).size(), is(0));
    }

    @Test
    public void testNoMovesBlockedEnemyBlack() {
        Position from = new Position("h7");
        Piece piece = new Pawn(Black);
        TestUtils.setPiecesOnBoard(White, state, "h6");
        assertThat(getToPositions(piece, from, state).size(), is(0));
    }

    @Test
    public void testNoMovesBlockedOwner() {
        Position from = new Position("b2");
        Piece piece = new Pawn(White);
        TestUtils.setPiecesOnBoard(White, state, "b3");
        assertThat(getToPositions(piece, from, state).size(), is(0));
    }

    @Test
    public void testNotBlocked() {
        Position from = new Position("b2");
        Piece piece = new Pawn(White);
        List<String> toPositions = getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(2));
        assertThat(toPositions, hasItems("b3", "b4"));
    }

    @Test
    public void testBlockedWithEnemyWhite() {
        Position from = new Position("b2");
        Piece piece = new Pawn(White);
        TestUtils.setPiecesOnBoard(Black, state, "a3", "b3", "c3");
        List<String> toPositions = getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(2));
        assertThat(toPositions, hasItems("a3", "c3"));
    }

    @Test
    public void testBlockedWithEnemyBlack() {
        Position from = new Position("b7");
        Piece piece = new Pawn(Black);
        TestUtils.setPiecesOnBoard(White, state, "a6", "b6", "c6");
        List<String> toPositions = getToPositions(piece, from, state);
        assertThat(toPositions.size(), is(2));
        assertThat(toPositions, hasItems("a6", "c6"));
    }



}