package chess.pieces;

import chess.GameState;
import chess.Move;
import chess.Player;
import chess.Position;

import java.util.stream.Stream;

/**
 * The 'Rook' class
 */
public class Rook extends Piece {

    public Rook(Player owner) {
        super(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'r';
    }

    @Override
    public Stream<Move> getMoves(Position position, GameState state) {
        return Stream.of(
                toPositions(position, state, 1, 0),
                toPositions(position, state, -1, 0),
                toPositions(position, state, 0, 1),
                toPositions(position, state, 0, -1)
        ).flatMap(list -> list.stream()).map(toPosition -> new Move(position, toPosition, this));
    }

}
