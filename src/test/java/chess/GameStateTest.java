package chess;

import chess.pieces.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static junit.framework.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Basic unit tests for the GameState class
 */
public class GameStateTest {

    private GameState state;

    @Before
    public void setUp() {
        state = new GameState();
    }

    @Test
    public void testStartsEmpty() {
        // Make sure all the positions are empty
        for (char col = Position.MIN_COLUMN; col <= Position.MAX_COLUMN; col++) {
            for (int row = Position.MIN_ROW; row <= Position.MAX_ROW; row++) {
                assertNull("All pieces should be empty", state.getPieceAt(String.valueOf(col) + row));
            }
        }
    }

    @Test
    public void testInitialGame() {
        // Start the game
        state.reset();

        // White should be the first player
        Player current = state.getCurrentPlayer();
        assertEquals("The initial player should be White", Player.White, current);

        // Spot check a few pieces
        Piece whiteRook = state.getPieceAt("a1");
        assertTrue("A rook should be at a1", whiteRook instanceof Rook);
        assertEquals("The rook at a1 should be owned by White", Player.White, whiteRook.getOwner());


        Piece blackQueen = state.getPieceAt("d8");
        assertTrue("A queen should be at d8", blackQueen instanceof Queen);
        assertEquals("The queen at d8 should be owned by Black", Player.Black, blackQueen.getOwner());
    }

    @Test
    public void testMove() {
        state.reset();
        Position from = new Position("h2");
        Position to = new Position("h4");
        Player player = state.getCurrentPlayer();
        Piece piece = state.getPieceAt(from);
        assertNotNull(piece);
        state.move(new Move(from, to, piece));
        assertNull("Piece should be removed", state.getPieceAt(from));
        assertEquals("Piece should be moved to 'to' position" ,piece , state.getPieceAt(to));
        assertTrue("The player should be changed", player != state.getCurrentPlayer());
    }

    @Test
    public void testMoveNoValidMove() {
        state.reset();
        Position from = new Position("h2");
        Position to = new Position("h5");
        Player player = state.getCurrentPlayer();
        Piece piece = state.getPieceAt(from);
        assertNotNull(piece);
        state.move(new Move(from, to, piece));
        assertNotNull("Piece shouldn't be moved", state.getPieceAt(from));
        assertNull("Piece shouldn't be moved", state.getPieceAt(to));
        assertEquals("The player shouldn't be changed", player, state.getCurrentPlayer());
    }

    @Test
    public void testGetMovesFilteredByCurrentPlayer() {
        state.reset();
        state.getMoves().forEach(m ->
                assertEquals("Moves should be related to current player",
                        m.getPiece().getOwner(), state.getCurrentPlayer())
        );
    }

    @Test
    public void testFilterMovesThatLeadsToEscapeCheckmate() {
        assertEquals("Initial player should be 'White'", state.getCurrentPlayer(), Player.White);
        state.placePiece(new King(Player.White), new Position("a1"));
        state.placePiece(new Bishop(Player.White), new Position("a2"));
        assertTrue("Contains the bishop's moves", collectBishopsMoves(state).size() > 0);
        state.placePiece(new Rook(Player.Black), new Position("a8"));
        assertThat("The bishop's moves should be removed", collectBishopsMoves(state).size(), is(0));
    }

    @Test
    public void testCheckmate() {
        assertEquals("Initial player should be 'White'", state.getCurrentPlayer(), Player.White);
        state.placePiece(new King(Player.White), new Position("a1"));
        state.placePiece(new Rook(Player.Black), new Position("a8"));
        state.placePiece(new Queen(Player.Black), new Position("b8"));
        assertThat(state.getStatus(), equalTo(GameState.Status.BLACK_WINNER));
    }

    @Test
    public void testInProgress() {
        assertEquals("Initial player should be 'White'", state.getCurrentPlayer(), Player.White);
        state.placePiece(new King(Player.White), new Position("a1"));
        state.placePiece(new Rook(Player.White), new Position("h2"));
        state.placePiece(new Rook(Player.Black), new Position("a8"));
        state.placePiece(new Queen(Player.Black), new Position("b8"));
        assertThat(state.getStatus(), equalTo(GameState.Status.IN_PROGRESS));
    }

    @Test
    public void testDraw() {
        assertEquals("Initial player should be 'White'", state.getCurrentPlayer(), Player.White);
        state.placePiece(new King(Player.White), new Position("a1"));
        state.placePiece(new Rook(Player.Black), new Position("b8"));
        state.placePiece(new Queen(Player.Black), new Position("h2"));
        assertThat(state.getStatus(), equalTo(GameState.Status.DRAW));
    }

    private List<Move> collectBishopsMoves(GameState state) {
        return state.getMoves()
                .filter(m -> m.getPiece() instanceof Bishop)
                .collect(toList());
    }

}
