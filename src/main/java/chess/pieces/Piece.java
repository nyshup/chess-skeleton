package chess.pieces;

import chess.GameState;
import chess.Move;
import chess.Player;
import chess.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * A base class for chess pieces
 */
public abstract class Piece {
    private final Player owner;

    protected Piece(Player owner) {
        this.owner = owner;
    }

    public char getIdentifier() {
        char id = getIdentifyingCharacter();
        if (owner.equals(Player.White)) {
            return Character.toLowerCase(id);
        } else {
            return Character.toUpperCase(id);
        }
    }

    public Player getOwner() {
        return owner;
    }

    protected abstract char getIdentifyingCharacter();

    /**
     * Get all posible moves from position in current state for piece.
     * This method doesn't filter moves by current player in state.
     * @param position The position
     * @param state The current state
     * @return All moves for the piece.
     */
    public abstract Stream<Move> getMoves(Position position, GameState state);

    protected boolean checkValidForMove(Position position, GameState state) {
        if (!position.isValid()) return false;
        Piece piece = state.getPieceAt(position);
        return piece == null || getOwner() != piece.getOwner();
    }

    /**
     * Gather positions that could be used as "to" position for moves.
     * This method iteratively get position using shifts (colShift, rowShift),
     * checks new position, add position to result if checks are passed.
     * These steps will be repeated until checks fail.
     * @param position current position of peace
     * @param state current state
     * @param colShift horizontal shift
     * @param rowShift vertical shift
     * @return
     */
    protected List<Position> toPositions(Position position, GameState state, int colShift, int rowShift) {
        List<Position> positionList = new ArrayList<>();
        Position currentPosition = position.relative(colShift, rowShift);
        while (checkValidForMove(currentPosition, state)) {
            positionList.add(currentPosition);
            if (state.getPieceAt(currentPosition) != null) {
                break;
            }
            currentPosition = currentPosition.relative(colShift, rowShift);
        }
        return positionList;
    }
}
