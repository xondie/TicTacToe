import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.Iterables;

/**
 * Methods for playing Tic Tac Toe according to proper rules
 * on a game board of specified size.
 *
 * @author Diana.Kantor
 */
public class TicTacToe
{
    int boardSize; // number of rows / number of columns
    LinkedHashMap<Square, Token> gameBoard;
    boolean isGameOver = false;
    Token winner;
    
    /**
     * Default constructor. Create a new standard game (3 rows/columns).
     */
    public TicTacToe()
    {
        this(3);
    }
    
    /**
     * Create a new game, specifying the size of the board.
     *
     * @param size Size of the game board (number of rows/columns)
     */
    public TicTacToe(int size)
    {
        this.boardSize = size;
        this.gameBoard = new LinkedHashMap<Square, Token>(size*size);
    }
    
    /**
     * Gets the current state of the game board.
     */
    public Map<Square, Token> getCurrentGameBoard()
    {
        return this.gameBoard;
    }
    
    /**
     * Gets the number of rows or columns of this game board.
     */
    public int getBoardSize() 
    {
		return this.boardSize;
	}

	/**
     * Determine which token has the next turn.
     */
    public Token getNextTurn()
    {
        if(isGameOver)
            throw new IllegalStateException(String.format("ERROR: This game is over already."));

        Square lastPlayedSquare = getLastPlayedSquare();
        Token lastPlayedToken = gameBoard.get(lastPlayedSquare);
        
        // First turn always goes to X
        if(lastPlayedToken == null)
            return Token.X;
        else if(lastPlayedToken == Token.O)
            return Token.X;
        else
            return Token.O;
    }
    
    /**
     * Play the current token in the specified square.
     * 
     * @param row Row number of the space to place the token.
     * @param column Column number of the space to place the token.
     * @throws IllegalArgumentException
     */
    public void placeToken(Token token, int row, int column)
            throws IllegalArgumentException
    {
        // Make sure the right token is playing this turn
        if (token != getNextTurn())
            throw new IllegalArgumentException(String.format("ERROR: %s, it is not your turn.", token));
        // Make sure it's a valid square
        if (row > this.boardSize || column > this.boardSize)
            throw new IllegalArgumentException(
                    String.format("ERROR: Your chose an invalid square. Row and column must be between 1 and %d", this.boardSize));
        // If the game is already over, no more tokens can be played.
        if (isGameOver)
            throw new IllegalStateException(
                    String.format("ERROR: This game is over already."));

        // Place the token iff the square is unoccupied
        Square currentSquare = new Square(row, column);
        Token currentToken = gameBoard.get(currentSquare);
        if (currentToken == null)
            gameBoard.put(currentSquare, token);
        else
            throw new IllegalArgumentException("ERROR: Square already occupied.");

        if (didLastPlayWin())
        {
            this.winner = token;
            this.isGameOver = true;
        }
    }

    /**
     * Gets the winner of the game. 
     * @return The winning token, or null if the game is not yet over or if no
     *         one won.
     */
    public Token getWinner()
    {
        return this.winner;
    }
    
    /**
     * Determine whether there are any unplayed spaces
     * left on the board.
     *
     * @return True of the board is full, otherwise false.
     */
    public boolean isBoardFull()
    {
        if(gameBoard.size() == boardSize*boardSize)
        {
            isGameOver = true;
            return true;
        }

        return false;
    }

    /**
     * Gets the last square that was played.
     */
    private Square getLastPlayedSquare()
    {
        if(gameBoard.isEmpty())
            return null;

        Square lastPlayedSquare = Iterables.getLast(gameBoard.keySet());

        return lastPlayedSquare;
    }

	/**
     * Determine if the latest play resulted in a win.
     *
     * @return True if the latest play resulted in a win.
     * Otherwise, false.
     */
    private boolean didLastPlayWin()
    {
        // If one player hasn't gone at least enough times
        // to win, don't bother checking for a winner
        if(gameBoard.size() < boardSize*2-1)
            return false;
        
        // Check all the different possible ways to win
        Square lastPlayedSquare = getLastPlayedSquare();
        Token lastPlayedToken = gameBoard.get(lastPlayedSquare);
        
        // Check across in the current row
        if(didWinAcross(lastPlayedToken, lastPlayedSquare))
            return true;
        
        // Check down in the current column
        if(didWinDown(lastPlayedToken, lastPlayedSquare))
            return true;
        
        // Check L->R diagonal
        if(didWinDiagonalLR(lastPlayedToken, lastPlayedSquare))
            return true;
        
        // Check R->L diagonal
        if(didWinDiagonalRL(lastPlayedToken, lastPlayedSquare))
            return true;
                
        // None of our tests resulted in a winning run of
        // the current token so no one has won yet.
        return false;
    }
    
    /**
     * Determine if the latest play resulted in a win
     * across a row.
     *
     * @param currentToken
     * @param square
     * @return True if the latest play resulted in a win across a row.
     * Otherwise, false.
     */
    private boolean didWinAcross(Token token, Square square)
    {
        boolean noTokensMissing = true;
        int row = square.getRow();
        for(int column=1; column<=boardSize; column++)
        {
            Token currentToken = gameBoard.get(new Square(row, column));
            if(token != currentToken)
            {
                noTokensMissing = false;
                break;
            }
        }
        return noTokensMissing;
    }
    
    /**
     * Determine if the latest play resulted in a win
     * down a column.
     *
     * @param currentToken
     * @param square
     * @return True if the latest play resulted in a win down a column.
     * Otherwise, false.
     */
    private boolean didWinDown(Token token, Square square)
    {
        boolean noTokensMissing = true;
        int column = square.getColumn();
        for(int row=1; row<=boardSize; row++)
        {
            Token currentToken = gameBoard.get(new Square(row, column));
            if(token != currentToken)
            {
                noTokensMissing = false;
                break;
            }
        }
        return noTokensMissing;
    }
    
    /**
     * Determine if the latest play resulted in a win
     * in a diagonal left-to-right direction.
     *
     * @param currentToken
     * @param square
     * @return True if the latest play resulted in a diagonal
     * left-to-right win. Otherwise, false.
     */
    private boolean didWinDiagonalLR(Token token, Square square)
    {
        boolean noTokensMissing = true;
        // If the row and column indexes are equal
        // then we know it's in a L->R diagonal space
        if(square.getRow() == square.getColumn())
        {
            for(int i=1; i<=this.boardSize; i++)
            {
                Token currentToken = gameBoard.get(new Square(i,i));
                if(token != currentToken)
                {
                    noTokensMissing = false;
                    break;
                }
            }
            return noTokensMissing;
        }
        return false;
    }
    
    /**
     * Determine if the latest play resulted in a win
     * in a diagonal right-to-left direction.
     *
     * @param currentToken
     * @param square
     * @return True if the latest play resulted in a diagonal
     * right-to-left win. Otherwise, false.
     */
    private boolean didWinDiagonalRL(Token token, Square square)
    {
        boolean noTokensMissing = true;
        // If the row index + column index equals the board
        // size + 1, then we know it's a R->L diagonal space.
        if(square.getRow()+square.getColumn() == boardSize+1)
        {
            for(int i=1; i<=this.boardSize; i++)
            {
                Token currentToken = gameBoard.get(new Square(i,boardSize+1-i));
                if(token != currentToken)
                {
                    noTokensMissing = false;
                    break;
                }
            }
            return noTokensMissing;
        }
        return false;
    }
}