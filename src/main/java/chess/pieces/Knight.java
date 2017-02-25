package chess.pieces;

import chess.GameState;
import chess.Move;
import chess.Player;
import chess.Position;

import java.util.stream.Stream;

/**
 * The Knight class
 */
public class Knight extends Piece {
    public Knight(Player owner) {
        super(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'n';
    }

    @Override
    public Stream<Move> getMoves(Position position, GameState state) {
        return Stream.of(
                position.relative(1, 2),
                position.relative(-1, 2),
                position.relative(2, -1),
                position.relative(2, 1),
                position.relative(-1, -2),
                position.relative(1, -2),
                position.relative(-2, 1),
                position.relative(-2, -1))
                .filter(pos -> checkValidForMove(pos, state)).map(toPos -> new Move(position, toPos, this));
    }
}
