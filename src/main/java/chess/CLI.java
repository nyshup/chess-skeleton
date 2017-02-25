package chess;

import java.io.*;

/**
 * This class provides the basic CLI interface to the Chess game.
 */
public class CLI {

    private final BufferedReader inReader;
    private final PrintStream outStream;

    private Game game = null;

    public CLI(InputStream inputStream, PrintStream outStream) {
        this.inReader = new BufferedReader(new InputStreamReader(inputStream));
        this.outStream = outStream;
        writeOutput("Welcome to Chess!");
    }

    /**
     * Write the string to the output
     * @param str The string to write
     */
    private void writeOutput(String str) {
        this.outStream.println(str);
    }

    /**
     * Retrieve a string from the console, returning after the user hits the 'Return' key.
     * @return The input from the user, or an empty-length string if they did not type anything.
     */
    private String getInput() {
        try {
            this.outStream.print("> ");
            return inReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from input: ", e);
        }
    }

    void startEventLoop() {
        writeOutput("Type 'help' for a list of commands.");
        doNewGame();

        while (true) {
            showBoard();
            showStatus();

            String input = getInput();
            if (input == null) {
                break; // No more input possible; this is the only way to exit the event loop
            } else if (input.length() > 0) {
                if (input.equals("help")) {
                    showCommands();
                } else if (input.equals("new")) {
                    doNewGame();
                } else if (input.equals("quit")) {
                    writeOutput("Goodbye!");
                    System.exit(0);
                } else if (input.equals("board")) {
                    writeOutput("Current Game:");
                } else if (input.equals("list")) {
                    game.getMoves().forEach(this::writeOutput);
                } else if (input.startsWith("move")) {
                    if(!game.doMove(input)) {
                        writeOutput("No move was provided. Type 'list' to show allowed moves.");
                    }
                } else {
                    writeOutput("I didn't understand that.  Type 'help' for a list of commands.");
                }
            }
        }
    }

    private void showStatus() {
        GameState.Status status = game.getStatus();
        switch (status) {
            case BLACK_WINNER:
                writeOutput("The game is over.  Congrats to Black.");
                break;
            case WHITE_WINNER:
                writeOutput("The game is over.  Congrats to White.");
                break;
            case DRAW:
                writeOutput("The game is over.  Draw.");
                break;
            default:
                writeOutput(game.getCurrentPlayer() + "'s Move");
        }
    }

    private void doNewGame() {
        game = new Game();
    }

    private void showBoard() {
        writeOutput(game.getBoardAsString());
    }

    private void showCommands() {
        writeOutput("Possible commands: ");
        writeOutput("    'help'                       Show this menu");
        writeOutput("    'quit'                       Quit Chess");
        writeOutput("    'new'                        Create a new game");
        writeOutput("    'board'                      Show the chess board");
        writeOutput("    'list'                       List all possible moves");
        writeOutput("    'move <colrow> <colrow>'     Make a move");
    }

    public static void main(String[] args) {
        CLI cli = new CLI(System.in, System.out);
        cli.startEventLoop();
    }
}
