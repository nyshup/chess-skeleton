package chess;

import chess.pieces.Piece;

import java.util.Optional;
import java.util.stream.Stream;

public class Game {

    private static final String NEWLINE = System.getProperty("line.separator");

    private GameState state;

    public Game() {
        this.state = new GameState();
        state.reset();
    }

    public Game(GameState state) {
        this.state = state;
    }

    public Stream<String> getMoves() {
        return state.getMoves()
                .map(m -> String.format("%s %s", m.getFrom(), m.getTo()));
    }


    public boolean doMove(String input) {
        boolean succeed = false;
        String[] str = input.split("\\s+");
        Optional<Position> from = getPosition(str[1]);
        Optional<Position> to = getPosition(str[2]);
        if (from.isPresent() && to.isPresent()) {
            Optional<Move> move = state.getMoves()
                    .filter(m -> m.getTo().equals(to.get()) && m.getFrom().equals(from.get()))
                    .findFirst();
            if (move.isPresent()) {
                state.move(move.get());
                succeed = true;
            }
        }
        return succeed;
    }

    public Player getCurrentPlayer() {
        return state.getCurrentPlayer();
    }

    public GameState.Status getStatus() {
        return state.getStatus();
    }

    private Optional<Position> getPosition(String colRow) {
        try {
            Position ret = new Position(colRow);
            if (ret.isValid()) {
                return Optional.of(ret);
            }
        } catch (Exception e) {}
        return Optional.empty();
    }

    /**
     * Display the board for the user(s)
     */
    public String getBoardAsString() {
        StringBuilder builder = new StringBuilder();
        builder.append(NEWLINE);

        printColumnLabels(builder);
        for (int i = Position.MAX_ROW; i >= Position.MIN_ROW; i--) {
            printSeparator(builder);
            printSquares(i, builder);
        }

        printSeparator(builder);
        printColumnLabels(builder);

        return builder.toString();
    }

    private void printSquares(int rowLabel, StringBuilder builder) {
        builder.append(rowLabel);

        for (char c = Position.MIN_COLUMN; c <= Position.MAX_COLUMN; c++) {
            Piece piece = state.getPieceAt(String.valueOf(c) + rowLabel);
            char pieceChar = piece == null ? ' ' : piece.getIdentifier();
            builder.append(" | ").append(pieceChar);
        }
        builder.append(" | ").append(rowLabel).append(NEWLINE);
    }

    private void printSeparator(StringBuilder builder) {
        builder.append("  +---+---+---+---+---+---+---+---+").append(NEWLINE);
    }

    private void printColumnLabels(StringBuilder builder) {
        builder.append("   ");
        for (char c = Position.MIN_COLUMN; c <= Position.MAX_COLUMN; c++) {
            builder.append(" ").append(c).append("  ");
        }

        builder.append(NEWLINE);
    }
}
