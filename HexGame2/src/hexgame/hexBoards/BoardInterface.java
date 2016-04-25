package hexgame.hexBoards;

import hexgame.gameMechanics.MoveInterface;

public interface BoardInterface
{

  public static final int BLANK = Piece.BLANK;
  public static final int RED = Piece.RED;
  public static final int BLUE = Piece.BLUE;
  public static final int MAX_SUPPORTED_BOARD_SIZE = 99;
  public static final int MIN_SUPPORTED_BOARD_SIZE = 1;
  public static final int DEFAULT_BOARD_SIZE = 7;

  public int getSize();

  public int get(int x, int y);

  public String getName();

  public void setName(String name);

  public boolean hasChanged();

  public void changeNoted();

  /**
   * Specifiy the size of the board that we are playing on. Both numbers must be greater than zero
   *
   * @param sizeX how wide the board will be
   * @param sizeY how tall the board will be
   * @throws InvalidBoardSizeException  If either size value is less than one.
   * @throws BoardAlreadySizedException If the board has already been created.
   * @returns boolean   true if the board could be set successfully
   */
  public boolean setBoardSize(int sizeX, int sizeY) throws InvalidBoardSizeException, BoardAlreadySizedException;

  /**
   * This method will return a two dimentional array of Pieces which represents the current state of the
   * board. As this is just a copy of the data it is safe to send to a Player.
   *
   * @throws NoBoardDefinedException Thrown when a call is made to this method before the boardSize
   *                                 method.
   * @returns Piece[][]  a two dimentional representation of the game board.
   */
  public Piece[][] getBoardView() throws NoBoardDefinedException;

  /**
   * Places a piece on the board at the specified location.
   *
   * @param colour the colour of the piece to place (RED or BLUE)
   * @param move   the position where you wish to place a piece
   * @return boolean   true if the piece was placed successfully
   * @throws PositionAlreadyTakenException if there is already a Piece in this position
   * @throws InvalidPositionException      if the specified position is invalid - e.g. (-1, -1)
   * @throws InvalidColourException        if the colour being set is invalid. E.g. if you try to place two BLUE pieces one after the other
   * @throws NoBoardDefinedException       if the board has yet to be defined with setBoardSize()
   */
  public boolean placePiece(Piece colour, MoveInterface move) throws PositionAlreadyTakenException, InvalidPositionException, InvalidColourException, NoBoardDefinedException;

  /**
   * Checks to see if either player has won.
   *
   * @return Piece   RED if red has won, BLUE if blue has won, UNSET if neither player has won.
   * @throws NoBoardDefinedException Indicates that this method has been called before the boardSize
   *                                 method
   */
  public Piece gameWon() throws NoBoardDefinedException;
}
