package minesweeper;

public class Main {
    public static void main(String[] args) throws Exception {

        MinesweeperGUI game = new MinesweeperGUI();
        Minesweeper brain = new Minesweeper();
        game.createMenu();

        boolean newGame = true;

        while (true) {
            System.out.print("");
            if (game.getGameMode()) {
                if (newGame) {
                    brain.setMinesweeperInfo(game.getCurrentGameDimension(), game.getMineAmount());
                    newGame = false;
                } else if (game.wasNewMoveMade()) {
                brain.updateGame(game.getMostRecentCellClicked(), game.getFlagClickingMode());
                game.updateGameGUI(brain.getGameInfo(), brain.getMinesLeft(), brain.getGameOverStatus(), brain.getGameWinStatus());
                }
            } else {
                newGame = true;
                brain.setMinesweeperInfo(0, 0);
            }
        }

        
    }

    private static void print(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.printf("%2.0f ", (double) board[j][i]);
            }
            System.out.println();
        }
    }

}