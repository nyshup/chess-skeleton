package chess;


import chess.pieces.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static chess.GameState.Status.*;
import static java.util.stream.Collectors.toList;

/**
 * Class that represents the current state of the game.  Basically, what pieces are in which positions on the
 * board.
 */
public class GameState {

    public static enum Status {
        WHITE_WINNER,
        BLACK_WINNER,
        DRAW,
        IN_PROGRESS
    }

    /**
     * The current player
     */
    private Player currentPlayer = Player.White;

    /**
     * A map of board positions to pieces at that position
     */
    private Map<Position, Piece> positionToPieceMap;
    private boolean status;

    /**
     * Create the game state.
     */
    public GameState() {
        positionToPieceMap = new HashMap<Position, Piece>();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Call to initialize the game state into the starting positions
     */
    public void reset() {
        // White Pieces
        placePiece(new Rook(Player.White), new Position("a1"));
        placePiece(new Knight(Player.White), new Position("b1"));
        placePiece(new Bishop(Player.White), new Position("c1"));
        placePiece(new Queen(Player.White), new Position("d1"));
        placePiece(new King(Player.White), new Position("e1"));
        placePiece(new Bishop(Player.White), new Position("f1"));
        placePiece(new Knight(Player.White), new Position("g1"));
        placePiece(new Rook(Player.White), new Position("h1"));
        placePiece(new Pawn(Player.White), new Position("a2"));
        placePiece(new Pawn(Player.White), new Position("b2"));
        placePiece(new Pawn(Player.White), new Position("c2"));
        placePiece(new Pawn(Player.White), new Position("d2"));
        placePiece(new Pawn(Player.White), new Position("e2"));
        placePiece(new Pawn(Player.White), new Position("f2"));
        placePiece(new Pawn(Player.White), new Position("g2"));
        placePiece(new Pawn(Player.White), new Position("h2"));

        // Black Pieces
        placePiece(new Rook(Player.Black), new Position("a8"));
        placePiece(new Knight(Player.Black), new Position("b8"));
        placePiece(new Bishop(Player.Black), new Position("c8"));
        placePiece(new Queen(Player.Black), new Position("d8"));
        placePiece(new King(Player.Black), new Position("e8"));
        placePiece(new Bishop(Player.Black), new Position("f8"));
        placePiece(new Knight(Player.Black), new Position("g8"));
        placePiece(new Rook(Player.Black), new Position("h8"));
        placePiece(new Pawn(Player.Black), new Position("a7"));
        placePiece(new Pawn(Player.Black), new Position("b7"));
        placePiece(new Pawn(Player.Black), new Position("c7"));
        placePiece(new Pawn(Player.Black), new Position("d7"));
        placePiece(new Pawn(Player.Black), new Position("e7"));
        placePiece(new Pawn(Player.Black), new Position("f7"));
        placePiece(new Pawn(Player.Black), new Position("g7"));
        placePiece(new Pawn(Player.Black), new Position("h7"));
    }

    /**
     * Get the piece at the position specified by the String
     * @param colrow The string indication of position; i.e. "d5"
     * @return The piece at that position, or null if it does not exist.
     */
    public Piece getPieceAt(String colrow) {
        Position position = new Position(colrow);
        return getPieceAt(position);
    }

    /**
     * Get the piece at a given position on the board
     * @param position The position to inquire about.
     * @return The piece at that position, or null if it does not exist.
     */
    public Piece getPieceAt(Position position) {
        return positionToPieceMap.get(position);
    }

    /**
     * Method to place a piece at a given position
     * @param piece The piece to place
     * @param position The position
     */
    void placePiece(Piece piece, Position position) {
        positionToPieceMap.put(position, piece);
    }

    /**
     * Get all available moves for current player
     * @return stream of moves
     */
    public Stream<Move> getMoves() {
        return getAllMovesUnfiltered()
                .filter(ownerIs(getCurrentPlayer()))
                .filter(this::checkMoveToEscapeCheckmate);
    }

    private Predicate<Move> ownerIs(Player player) {
        return move -> move.getPiece().getOwner() == player;
    }

    /**
     * Provide move of piece from one position to other
     * @param mv
     */
    public void move(Move mv) {
        getMoves().filter(mv::equals).findFirst().ifPresent(this::moveInternal);
    }

    /**
     * Calculate current game's status
     * @return
     */
    public Status getStatus() {
        if (getMoves().collect(toList()).isEmpty()) {
            if (isKingUnderAttack()) {
                return getCurrentPlayer() == Player.White? BLACK_WINNER: WHITE_WINNER;
            } else {
                return DRAW;
            }
        }
        return IN_PROGRESS;
    }

    private Stream<Move> getAllMovesUnfiltered() {
        return positionToPieceMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().getMoves(entry.getKey(), GameState.this));
    }

    private boolean checkMoveToEscapeCheckmate(Move move) {
        GameState cpState = getCopyState();
        cpState.moveInternal(move);
        return cpState.getAllMovesUnfiltered()
                .filter(ownerIs(cpState.getCurrentPlayer()))
                .allMatch(m -> {
                    Piece piece = cpState.getPieceAt(m.getTo());
                    return piece == null || !(piece instanceof King);
                });
    }

    private void moveInternal(Move mv) {
        Piece piece = getPieceAt(mv.getFrom());
        positionToPieceMap.remove(mv.getFrom());
        placePiece(piece, mv.getTo());
        changePlayer();
    }

    private void changePlayer() {
        currentPlayer = currentPlayer == Player.Black? Player.White: Player.Black;
    }

    private GameState getCopyState() {
        GameState state = new GameState();
        positionToPieceMap.forEach((k, v) -> state.placePiece(v, k));
        state.currentPlayer = getCurrentPlayer();
        return state;
    }

    private boolean isKingUnderAttack() {
        Player enemy = currentPlayer == Player.White ? Player.Black : Player.White;
        Optional<Move> checkmateMove = getAllMovesUnfiltered()
                .filter(ownerIs(enemy))
                .filter(m -> {
                            Piece piece = getPieceAt(m.getTo());
                            return piece != null && piece instanceof King;
                        })
                .findFirst();
        if (checkmateMove.isPresent()) {
            return true;
        }
        return false;
    }
}
