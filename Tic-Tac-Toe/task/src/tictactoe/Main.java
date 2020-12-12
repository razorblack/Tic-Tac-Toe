package tictactoe;

import java.util.Scanner;

public class Main {

    String input = ""; //String to store first user input moves

    // Class object to call instance methods
    final static Main obj = new Main();

    // Scanner object to take input from user
    final static Scanner scanner = new Scanner(System.in);

    // 2D array to store complete ui of tic-tac-toe except dashes on top and bottom
    static char[][] ui = new char[3][5];

    int xCoordinate = 0; // Variable to store the x inputted co-ordinates
    int yCoordinate = 0; // Variable to store the y inputted co-ordinates

    public static void main(String[] args) {
        // Printing initial state of UI of tic-tac-toe
        printInitialState();

        // Starting the game
        startGame();

    }

    // Method to print initial empty UI of tic-tac-toe
    public static void printInitialState() {
        obj.input = "_________"; // Giving empty string as input (length = 9 for 9 cells)

        // Placing empty input string in 2D array to initialise the UI
        putInputIntoUiArray(obj.input);

        // Displaying the initial ui of tic-tac-toe
        outputUI();
    }

    // Method to keep the game running until end
    public static void startGame() {
        // First we have to initialise input with possible alternate moves (starting with 'X')
        initialiseInputAgain();

        int lengthOfInput = 9; // Length of input string
        int index = 0; //Variable to store index for characters at input string

        do {
            // Taking input of co-ordinates for the next move
            nextMove();

            // Storing the move into the inputted co-ordinates of the user
            storeAtInputCoordinates(obj.input.charAt(index));
            index++;

            // Displaying the final UI of tic-tac-toe
            outputUI();
        } while (index < lengthOfInput && isGameRunning()); // Run until game gets end

        // Printing the state of the game Who wins, Draw, Impossible
        analyseWinning();
    }

    // Method to take first input string of moves and initialises to 'input'
    public static void initialiseInputAgain() {
        obj.input = "XOXOXOXOX";
    }

    //Method to store the input moves in string into 2D array
    public static void putInputIntoUiArray(String input) {
        int temp = 0; //A temporary variable to keep track of input string index
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 0 || j == 4) {
                    ui[i][j] = '|';
                } else {
                    ui[i][j] = input.charAt(temp);
                    temp++;
                }
            }
        }
    }

    // Method to display the UI of the tic-tac-toe matrix
    public static void outputUI() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (ui[i][j] == '_') {
                    System.out.print("  ");
                } else {
                    System.out.print(ui[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("---------");
    }

    // Method to ask co-ordinates for next move from user
    protected static void nextMove() {
        // Variable to store boolean whether the condition make the situation to ask again from user
        boolean isIHaveToAskAgain;
        do {
            System.out.print("Enter the coordinates: ");
            try {
                obj.xCoordinate = scanner.nextInt();
                obj.yCoordinate = scanner.nextInt();
                isIHaveToAskAgain = false;
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                isIHaveToAskAgain = true;
            }

            if (!isIHaveToAskAgain) {
                if (obj.xCoordinate < 1 || obj.xCoordinate > 3 || obj.yCoordinate < 1 || obj.yCoordinate > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    isIHaveToAskAgain = true;
                } else {
                    if (isCellOccupied(obj.xCoordinate, obj.yCoordinate)) {
                        System.out.println("This cell is occupied! Choose another one!");
                        isIHaveToAskAgain = true;
                    }
                }
            }
        } while (isIHaveToAskAgain);
    }

    // Method to check whether cell at provided co-ordinates is occupied or not
    private static boolean isCellOccupied(int xCoordinate, int yCoordinate) {
        int iCoordinate; // Co-ordinate corresponding to xCoordinate in array representation
        int jCoordinate; // Co-ordinate corresponding to yCoordinate in array representation

        iCoordinate = Math.abs(yCoordinate - 3);
        jCoordinate = xCoordinate;

        if (ui[iCoordinate][jCoordinate] == '_') {
            return false; // Cell is not occupied
        } else {
            return true; // Cell is occupied
        }
    }

    // Method to store 'X' or 'O' at inputted co-ordinates in 2D array
    public static void storeAtInputCoordinates(char moveToInsert) {
        int iCoordinate; // Co-ordinate corresponding to xCoordinate in array representation
        int jCoordinate; // Co-ordinate corresponding to yCoordinate in array representation

        iCoordinate = Math.abs(obj.yCoordinate - 3);
        jCoordinate = obj.xCoordinate;

        // Inserting the move in 2D array at inputted co-ordinates
        ui[iCoordinate][jCoordinate] = moveToInsert;
    }

    // Method to check is the next move is possible to play or not
    private static boolean isGameRunning() {
        if (obj.xWins() || obj.oWins() || obj.impossible()) {
            return false;
        } else {
            return true;
        }
    }

    // Method to analyse the game status and print corresponding message
    protected static void analyseWinning() {
        if (obj.impossible()) {
            System.out.println("Impossible");
        } else if (obj.draw()) {
            System.out.println("Draw");
        } else if (obj.xWins()) {
            System.out.println("X wins");
        } else {
            System.out.println("O wins");
        }
    }

    // Method to check for impossible games i.e Both wins and someone takes more than one chance
    private boolean impossible() {
        if (xWins() && oWins()) {
            return true;
        } else return Math.abs(xNum() - oNum()) >= 2;

    }

    // Method to check for draw
    private boolean draw() {
        if (!isEmptyCells()) {
            if (xWins() && oWins()) {
                return true;
            }
        }
        return false;
    }

    // Method to check whether 'X' wins or not
    private boolean xWins() {
        boolean xWinsResult = false;
        if (rowCheck('X') || columnCheck('X') || diagonalCheck('X')) {
            xWinsResult = true;
        }
        return xWinsResult;
    }

    // Method to check whether 'O'wins or not
    private boolean oWins() {
        boolean oWinsResult = false;
        if (rowCheck('O') || columnCheck('O') || diagonalCheck('O')) {
            oWinsResult = true;
        }
        return oWinsResult;
    }

    // Method to check whether any empty cells '_' present in 2D array
    private boolean isEmptyCells() {
        // Variable to store count of '_'s
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j < 4; j++) {
                if (ui[i][j] == '_') {
                    count++;
                }
            }
        }
        return count > 0;

    }

    // Method to return the number of 'X's present in 2D array
    private int xNum() {
        // Variable to store count of 'X's
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j < 4; j++) {
                if (ui[i][j] == 'X') {
                    count++;
                }
            }
        }
        return count;
    }

    // Method to return the number of 'O's present in 2D array
    private int oNum() {
        // Variable to store count of 'O's
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j < 4; j++) {
                if (ui[i][j] == 'O') {
                    count++;
                }
            }
        }
        return count;
    }

    // Method to check that if 'X' or 'O' is present 3 times in any row
    // Here char value is 'X' or 'O'
    protected boolean rowCheck(char value) {
        // Variable to count number of occurrence of char value
        int valueCount = 0;

        // Nested Loop to check occurrence in every single row
        for (char[] arr : ui) {
            for (char c : arr) {
                if (c == value) {
                    valueCount++;
                }
            }
            // Return true if value comes out 3 else set valueCount to 0 for next row checking
            if (valueCount == 3) {
                return true;
            } else {
                valueCount = 0;
            }
        }
        return false;
    }

    // Method to check that if 'X' or 'O' is present 3 times in any column
    // Here char value is 'X' or 'O'
    protected boolean columnCheck(char value) {

        // Variable to count number of occurrence of char value
        int valueCount = 0;

        // Nested Loop to check occurrence in every single column
        for (int j = 1; j < 4; j++) {
            for (int i = 0; i < 3; i++) {
                if (ui[i][j] == value) {
                    valueCount++;
                }
            }
            // Return true if value comes out 3 else set valueCount to 0 for next row checking
            if (valueCount == 3) {
                return true;
            } else {
                valueCount = 0;
            }
        }
        return false;
    }

    // Method to check  that if 'X' or 'O' is present 3 times in any diagonal
    // Here char value is 'X' or 'O'
    protected boolean diagonalCheck(char value) {
        int valueCount1 = 0; // Variable to store count for principal diagonal
        int valueCount2 = 0; // Variable to store count for secondary diagonal

        // Loop to check for value count for 'X' or 'O'
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j < 4; j++) {
                // Checking at principal diagonal
                if (i == j && ui[i][j] == value) {
                    valueCount1++;
                }
                // Checking at secondary diagonal
                if (i + j == 3 && ui[i][j] == value) {
                    valueCount2++;
                }
            }
        }
        // Return true if any count variable's value is 3 else return false
        return valueCount1 == 3 || valueCount2 == 3;
    }
}
