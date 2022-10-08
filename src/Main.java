import java.util.Arrays;
import java.util.Scanner;
import javax.swing.*;

public class Main {
    private final static int VIDE = 0;
    private final static int YELLOW = 1;
    private final static int RED = 2;

    public static void main(String[] args) {
        int n_lines = 6;
        int n_col = 7;
        int[][] matrix = new int[n_lines][n_col];
        Scanner input = new Scanner(System.in);
        initializer(matrix);
        int player = YELLOW;
        /* matrix[y en partant du haut][x en partant de la gauche] */
        do {
            clearConsole();
            show(matrix);
            System.out.println("\n" + which_player(player) + " In which column would you like to play?");
            int column;
            try {
                column = input.nextInt();
            } catch (Exception e) {
                input.next();
                continue;
            }
            if (play(matrix, column, player)) {
                if (isItOver(matrix, last_move(matrix, column - 1))) {
                    clearConsole();
                    show(matrix);
                    System.out.println("\nGAME OVER, CONGRATULATIONS TO " + which_player(player));
                    JOptionPane.showMessageDialog(null, "GAME OVER, CONGRATULATIONS TO " + which_player(player));
                    break;
                }
                player = switch_player(player);
            }
        } while (true);
    }

    private static int[] last_move(int[][] matrix, int column) {
        int[] last_move = new int[2];
        for (int ligne = 0; ligne < matrix.length; ligne++) {
            if (matrix[ligne][column] != VIDE) {
                last_move[0] = ligne;
                last_move[1] = column;
                break;
            }
        }
        return last_move;
    }

    private static void initializer(int[][] matrix) {
        //This function initialize the grid
        for (int[] ints : matrix) {
            Arrays.fill(ints, VIDE);
        }
    }
    private static boolean play(int[][] matrix, int column, int player) {
        //This method returns true if the move is valid and plays it
        if (column > matrix[0].length) {
            return false;
        }
        for (int i = matrix.length - 1; i >= 0; --i) {
            if (matrix[i][column - 1] == VIDE) {
                matrix[i][column - 1] = player;
                return true;
            }
        }
        return false;
    }
    private static String which_player(int player_number) {
        //This method is used to return the name of the player given the number of the player
        if (player_number == YELLOW) {
            return "\uD83D\uDFE8";
        } else {
            if (player_number == RED) {
            return "\uD83D\uDFE5";
            }
        }
        return "  ";
    }
    private static int switch_player(int player) {
        //This method switches players after each turn
        if (player == YELLOW) {
            return RED;
        } else {
            return YELLOW;
        }
    }

    private static void printableMatrix(int[][] matrix) {
        String[][] matrix_printable = new String[matrix.length][matrix[0].length];
        for (int line = 0; line < matrix_printable.length; line++) {
            for (int column = 0; column < matrix_printable[line].length; column++) {
                matrix_printable[line][column] = (which_player(matrix[line][column]));
                }
            }
        System.out.println(Arrays.deepToString(matrix_printable));
        }

    private static void print(int a) {
        //This function allows to print the grid in a more readable way
        if (a == 0) {
            System.out.print("  |");
        } else if (a == 1) {
            System.out.print(which_player(YELLOW) + "|");
        } else if (a == 2) {
            System.out.print(which_player(RED) + "|");
        }
    }

    private static void show(int[][] matrix) {
        for (int[] line : matrix) {
            System.out.print("|");
            //System.out.println(" |");
            for (int emplacement : line) {
                print(emplacement);
            }
            System.out.println();
            //System.out.println("_______________________________");
        }
    }
    private static void clearConsole() {
        //This method tries to clear the console after every move
        /*try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                String[] command = {"cls"};
                Process proc = new ProcessBuilder(command).start();
            } else {
                String[] command = {"clear"};
                Process proc = new ProcessBuilder(command).start();
            }
        } catch (final Exception e) {
            System.out.println("Impossible to clear");
        }*/
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    private static boolean isItOver(int[][] matrix, int[] last_case) {
        //This function checks if the last move made by a player is a winning move by checking in all direction from the last move if there are 4 pawns aligned
        int[][] possibleDirections = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        for (int[] direction : possibleDirections) {
            if (checkInDirection(matrix, direction, last_case)) {
                return true;
            }
        }
        return false;
     }
    private static boolean checkInDirection(int[][] matrix, int[] direction, int[] last_case) {
        //This function checks if there are 4 pawns of the same color from a given case and a given direction
        int player = matrix[last_case[0]][last_case[1]];
        int y = last_case[0];
        int x = last_case[1];
        int counter = 0;
        return_time:
        for (int i = 0; i < 4; ++i) {
            if ((x < 0 || y < 0 || y > matrix.length-1) || (x > matrix[0].length-1)) {
            }
            if (matrix[y][x] == player) {
                ++counter;
                y += direction[0];
                x += direction[1];
            }
        }
        return counter == 4;
    }
}