package chess.pieces;

import chess.GameState;
import chess.Move;
import chess.Player;
import chess.Position;

import java.util.stream.Stream;

/**
 * The Queen
 */
public class Queen extends Piece {
    public Queen(Player owner) {
        super(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'q';
    }

    @Override
    public Stream<Move> getMoves(Position position, GameState state) {
        return Stream.of(
                toPositions(position, state, 1, 0),
                toPositions(position, state, -1, 0),
                toPositions(position, state, 0, 1),
                toPositions(position, state, 0, -1),
                toPositions(position, state, 1, 1),
                toPositions(position, state, -1, -1),
                toPositions(position, state, -1, 1),
                toPositions(position, state, 1, -1)
        ).flatMap(list -> list.stream()).map(toPosition -> new Move(position, toPosition, this));
    }
}
