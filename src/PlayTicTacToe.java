import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class PlayTicTacToe
{
    /**
     * Interactive text console for playing Tic Tac Toe.
     */
    public static void main(String[] args)
    {
        // Set up the game board. Ask for size of board.
        System.out.print("What dimension would you like your Tic Tac Toe board to be? Standard is 3. ");

        // Set up variables and input stream
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String boardSizeStr = null;
        TicTacToe game = null;
        int boardSize = 0;
        String sizeErrorMsgFormat = "Invalid game board size, %s. Expecting a positive integer.";

        // Attempt to read user input for game board size.
        try
        {
            boardSizeStr = br.readLine();
            boardSize = Integer.parseInt(boardSizeStr);
            if(boardSize > 0)
                game = new TicTacToe(boardSize);
            else
            {
                System.out.println(String.format(sizeErrorMsgFormat, boardSizeStr));
                System.exit(1);
            }
        }
        catch(NumberFormatException nfe)
        {
            System.out.println(String.format(sizeErrorMsgFormat, boardSizeStr));
            System.exit(1);
        }
        catch (IOException e)
        {
            System.out.println("IO error trying to read your desired game board size.");
            System.exit(1);
        }

        // Play the game.
        boolean gameOver = false;
        while(!gameOver)
        {
            String coordInputErrorMsg =
            String.format("Bad Inputs. Expecting two integers between 1 and %d, separated by a comma, such as \"1,2\". Try again.", boardSize);

            // Who gets to go next?
            Token token = game.getNextTurn();

            try
            {
                // Get user input for coordinates of next square to play
                System.out.println(String.format("Player %s, choose your square. Enter row,column", token));
                String coords = br.readLine();

                // Parse inputs
                String[] coordArray = coords.split(",");
                if(coordArray.length != 2)
                throw new IllegalArgumentException(coordInputErrorMsg);

                String rowStr = coordArray[0];
                String columnStr = coordArray[1];
                int row = Integer.parseInt(rowStr);
                int column = Integer.parseInt(columnStr);


                // Play the current token on the requested square
                game.placeToken(token, row, column);
                // Print out the current state of the board
                displayBoard(game);

                // Check if the game is over, either because someone won, or there are no more spaces.
                Token winner = game.getWinner();
                if(winner != null)
                {
                    System.out.println(String.format("Congratulations, %s! You won!", winner));
                    gameOver = true;
                }
                else
                {
                    if(game.isBoardFull())
                    {
                        System.out.println("Board is full. No one wins.");
                        gameOver = true;
                    }
                }
            }
            catch(NumberFormatException nfe)
            {
                System.out.println(coordInputErrorMsg);
            }
            catch(IllegalArgumentException iae)
            {
                System.out.println(iae.getMessage());
            }
            catch (IOException ioe)
            {
                System.out.println("IO error.");
                System.exit(1);
            }
        }
        System.exit(0);
    }
    
    /**
     * Print out current board state as text.
     */
    private static void displayBoard(TicTacToe game)
    {
        Map<Square, Token> gameBoard = game.getCurrentGameBoard();
        int boardSize = game.getBoardSize();
        
        for(int row=1; row<=boardSize; row++)
        {
            for(int column=1; column<=boardSize; column++)
            {
                Token token = gameBoard.get(new Square(row, column));
                System.out.print(String.format("%s ", token==null?"~":token));
            }
            System.out.println();
        }
    }

}