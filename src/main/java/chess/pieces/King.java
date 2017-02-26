package chess.pieces;

import chess.GameState;
import chess.Move;
import chess.Player;
import chess.Position;

import java.util.stream.Stream;

/**
 * The King class
 */
public class King extends Piece {
    public King(Player owner) {
        super(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'k';
    }

    @Override
    public Stream<Move> getMoves(Position position, GameState state) {
        return Stream.of(
                position.relative(-1, -1),
                position.relative(-1, 0),
                position.relative(-1, 1),
                position.relative(0, -1),
                position.relative(1, -1),
                position.relative(0, 1),
                position.relative(1, 0),
                position.relative(1, 1))
                .filter(pos -> checkValidForMove(pos, state))
                .map(toPos -> new Move(position, toPos, this));
    }
}
