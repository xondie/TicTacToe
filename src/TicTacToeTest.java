import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TicTacToeTest
{
    
    @Test
    public void testPlaceToken()
    throws Exception
    {
        // Test winning row
        TicTacToe game = new TicTacToe(3);
        game.placeToken(Token.X, 1,1); // Play X
        assertNull(game.getWinner());
        game.placeToken(Token.O, 2,2); // Play O
        assertNull(game.getWinner());
        game.placeToken(Token.X, 1,2); // Play X
        assertNull(game.getWinner());
        game.placeToken(Token.O, 3,3); // Play O
        assertNull(game.getWinner());
        game.placeToken(Token.X, 1,3); // Play X
        assertEquals(Token.X, game.getWinner());
        
        // Test winning column
        TicTacToe game2 = new TicTacToe(3);
        game2.placeToken(Token.X, 1,1); // Play X
        assertNull(game2.getWinner());
        game2.placeToken(Token.O, 2,2); // Play O
        assertNull(game2.getWinner());
        game2.placeToken(Token.X, 2,1); // Play X
        assertNull(game2.getWinner());
        game2.placeToken(Token.O, 3,3); // Play O
        assertNull(game2.getWinner());
        game2.placeToken(Token.X, 3,1); // Play X
        assertEquals(Token.X, game2.getWinner());
        
        // Test winning L->R diagonal
        TicTacToe game3 = new TicTacToe(3);
        game3.placeToken(Token.X, 1,1); // Play X
        assertNull(game3.getWinner());
        game3.placeToken(Token.O, 1,2); // Play O
        assertNull(game3.getWinner());
        game3.placeToken(Token.X, 2,2); // Play X
        assertNull(game3.getWinner());
        game3.placeToken(Token.O, 3,1); // Play O
        assertNull(game3.getWinner());
        game3.placeToken(Token.X, 3,3); // Play X
        assertEquals(Token.X, game3.getWinner());
        
        // Test winning R->L diagonal
        TicTacToe game4 = new TicTacToe(3);
        game4.placeToken(Token.X, 3,1); // Play X
        assertNull(game4.getWinner());
        game4.placeToken(Token.O, 1,2); // Play O
        assertNull(game4.getWinner());
        game4.placeToken(Token.X, 2,2); // Play X
        assertNull(game4.getWinner());
        game4.placeToken(Token.O, 3,3); // Play O
        assertNull(game4.getWinner());
        game4.placeToken(Token.X, 1,3); // Play X
        assertEquals(Token.X, game4.getWinner());
        
        // Test winning L->R diagonal with large board
        TicTacToe game5 = new TicTacToe(5);
        game5.placeToken(Token.X, 5,5); // Play X
        assertNull(game5.getWinner());
        game5.placeToken(Token.O, 1,2); // Play O
        assertNull(game5.getWinner());
        game5.placeToken(Token.X, 2,2); // Play X
        assertNull(game5.getWinner());
        game5.placeToken(Token.O, 3,1); // Play O
        assertNull(game5.getWinner());
        game5.placeToken(Token.X, 4,4); // Play X
        assertNull(game5.getWinner());
        game5.placeToken(Token.O, 1,5); // Play O
        assertNull(game5.getWinner());
        game5.placeToken(Token.X, 3,3); // Play X
        assertNull(game5.getWinner());
        game5.placeToken(Token.O, 3,4); // Play O
        assertNull(game5.getWinner());
        game5.placeToken(Token.X, 1,1); // Play X
        assertEquals(Token.X, game5.getWinner());
        // try to play after game already over
    	try
    	{
    	    game5.placeToken(Token.O, 1, 3); // fails because space occupied
    	    fail("Should have thrown an exception for trying to play after game is over.");
    	}
    	catch(IllegalStateException ise) {}
        
        // Game with lots of errors
        TicTacToe game6 = new TicTacToe();
        game6.placeToken(Token.X, 1, 1);
    	try
    	{
    	    game6.placeToken(Token.O, 1, 1); // fails because space occupied
    	    fail("Should have thrown an exception for trying to play the same square twice.");
    	}
    	catch(IllegalArgumentException iae) {}
    	try
    	{
    	    game6.placeToken(Token.O, 4, 4); // fails because not a valid space
    	    fail("Should have thrown an exception for trying to play non-existent square twice.");
    	}
    	catch(IllegalArgumentException iae) {}
    	try
    	{
    	    game6.placeToken(Token.X, 4, 4); // fails because wrong token is playing
    	    fail("Should have thrown an exception for trying to play the wrong token.");
    	}
    	catch(IllegalArgumentException iae) {}

    }
    
    @Test
    public void testIsBoardFull()
    {
        TicTacToe game = new TicTacToe(3);
        game.placeToken(Token.X, 1, 1);
        assertFalse(game.isBoardFull());
        game.placeToken(Token.O, 1, 2);
        assertFalse(game.isBoardFull());
        game.placeToken(Token.X, 1, 3);
        assertFalse(game.isBoardFull());
        game.placeToken(Token.O, 2, 1);
        assertFalse(game.isBoardFull());
        game.placeToken(Token.X, 2, 2);
        assertFalse(game.isBoardFull());
        game.placeToken(Token.O, 2, 3);
        assertFalse(game.isBoardFull());
        game.placeToken(Token.X, 3, 2);
        assertFalse(game.isBoardFull());
        game.placeToken(Token.O, 3, 1);
        assertFalse(game.isBoardFull());
        game.placeToken(Token.X, 3, 3);
        assertTrue(game.isBoardFull());
    }
    
    @Test
    public void testGetNextTurn()
    {
    	TicTacToe game = new TicTacToe();
    	assertEquals(Token.X, game.getNextTurn());
    	game.placeToken(Token.X, 1, 2);
    	assertEquals(Token.O, game.getNextTurn());
    	try
    	{
    	    game.placeToken(Token.O, 1, 2); // fails because space occupied
    	    fail("Should have thrown an exception for trying to play the same square twice.");
    	}
    	catch(IllegalArgumentException iae) {}
    	// After failure, expect same token to have turn
    	assertEquals(Token.O, game.getNextTurn());
    }
}


