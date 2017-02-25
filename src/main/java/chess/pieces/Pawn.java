package chess.pieces;

import chess.GameState;
import chess.Move;
import chess.Player;
import chess.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * The Pawn
 */
public class Pawn extends Piece {
    public Pawn(Player owner) {
        super(owner);
    }

    @Override
    protected char getIdentifyingCharacter() {
        return 'p';
    }

    @Override
    public Stream<Move> getMoves(Position position, GameState state) {
        int shift = this.getOwner() == Player.White ? 1 : -1;
        boolean allowMove2Rows = this.getOwner() == Player.White ? position.getRow() == 2 : position.getRow() == 7;

        List<Position> toPos = new ArrayList<>();

        Position oneStep = position.relative(0, shift);
        if (state.getPieceAt(oneStep) == null) {
            toPos.add(oneStep);
        }

        if (allowMove2Rows && state.getPieceAt(oneStep) == null) {
            Position twoSteps = position.relative(0, 2 * shift);
            if (state.getPieceAt(twoSteps) == null) {
                toPos.add(twoSteps);
            }
        }

        Position enemyOnePosition = position.relative(1, shift);
        if (checkValidWithEnemy(enemyOnePosition, state)) {
            toPos.add(enemyOnePosition);
        }
        Position enemyTwoPosition = position.relative(-1, shift);
        if (checkValidWithEnemy(enemyTwoPosition, state)) {
            toPos.add(enemyTwoPosition);
        }

        return toPos.stream()
                .map(toPosition -> new Move(position, toPosition, this));
    }

    private boolean checkValidWithEnemy(Position position, GameState state) {
        if (!position.isValid()) return false;
        Piece piece = state.getPieceAt(position);
        return piece != null && getOwner() != piece.getOwner();
    }
}
