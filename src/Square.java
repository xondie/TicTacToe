/**
 * A single square on the game board.
 */
public class Square
{
    int row;
    int column;
    
    public Square(int row, int column)
    {
        this.row = row;
        this.column = column;
    }
    
    public int getRow()
    {
        return row;
    }
    
    public int getColumn()
    {
        return column;
    }
    
    @Override
    public int hashCode()
    {
        String squareLoc = this.row + this.column + "";
        return Integer.parseInt(squareLoc);
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(obj.getClass()!=Square.class)
            return false;
        
        Square other = (Square)obj;
        return this.row==other.getRow() && this.column==other.getColumn();
    }
    
    @Override
    public String toString()
    {
        return String.format("[Square row=%d column=%d]", row, column);
    }
}
