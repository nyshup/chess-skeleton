package chess;

import chess.pieces.Pawn;
import chess.pieces.Piece;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.mockito.Mockito.when;


public class TestUtils {

    public static void setPiecesOnBoard(Player player, GameState state, String... colRows) {
        Stream.of(colRows).forEach(colrow ->
                when(state.getPieceAt(new Position(colrow))).thenReturn(new Pawn(player)));

    }

    public static List<String> getToPositions(Piece piece, Position currentPosition, GameState state) {
        return piece.getMoves(currentPosition, state).map(move -> move.getTo().toString()).collect(toList());
    }
}
