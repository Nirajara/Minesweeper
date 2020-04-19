package minesweeper;

import java.util.Arrays;

public class Minesweeper {

    private int[][] game;
    private int minesAmount;
    private int minesLeft;

    private int emptyHidden;
    private int emptyFlag;
    private int mineFlag;
    private int mineHidden;
    private int mineOpened;

    private boolean gameOver = false;
    private boolean gameWin = false;

    public Minesweeper() {

        emptyHidden = -1;
        emptyFlag = -2;
        mineFlag = -3;
        mineHidden = -4;
        mineOpened = -5;
        

    }

    public void setMinesweeperInfo(int dimensions, int minesAmountInfo) {

        gameOver = false;
        gameWin = false;

        game = new int[dimensions][dimensions];
        for (int i = 0; i < game.length; i++) {
            Arrays.fill(game[i], -1);
        }
        minesAmount = minesAmountInfo;
        minesLeft = minesAmount;

        plantMines(minesAmount);

    }

    public int getMinesLeft() {
        return minesLeft;
    }

    public int[][] getGameInfo() {
        return game;
    }

    public boolean getGameOverStatus() {
        return gameOver;
    }

    public boolean getGameWinStatus() {
        return gameWin;
    }

    private void plantMines(int minesAmount) {

        while (minesAmount != 0) {
            
            int x = (int) (Math.random() * game.length);
            int y = (int) (Math.random() * game.length);

            if (game[x][y] != mineHidden) {
                game[x][y] = mineHidden;
                minesAmount--;
            }

        }

    }

    public void updateGame(int[] location, boolean flagClickingMode) {
        try {
            int x = location[0];
            int y = location[1];

            if (flagClickingMode) {
                if (game[x][y] == mineHidden) {
                    game[x][y] = mineFlag;
                    minesLeft--;
                } else if (game[x][y] == emptyHidden) {
                    game[x][y] = emptyFlag;
                    minesLeft--;
                } else if (game[x][y] == mineFlag) {
                    game[x][y] = mineHidden;
                    minesLeft++;
                } else if (game[x][y] == emptyFlag) {
                    game[x][y] = emptyHidden;
                    minesLeft++;
                }
            } else {
                if (game[x][y] == mineHidden) {
                    gameOver = true;
                    revealBoard();
                } else if (game[x][y] == emptyHidden) {
                    game[x][y] = getNumberOfSurroundingMines(x, y);
                    if (game[x][y] == 0) {
                        updateSurroundings(x, y);
                    }
                }
            }

            if (minesLeft == 0) {
                gameWin = checkWin();
            }

        } catch (Exception exception) {
            System.out.println("Updating Game Error: " + exception);
        }
    }

    private void updateSurroundings(int x, int y) {

        boolean yAdd = (y + 1 < game.length);
        boolean yMinus = (y - 1 >= 0);
        boolean xAdd = (x + 1 < game.length);
        boolean xMinus = (x - 1 >= 0);

        if (getGameInfoAt(x, y + 1) == emptyHidden && yAdd) {
            game[x][y + 1] = getNumberOfSurroundingMines(x, y + 1);
            if (game[x][y + 1] == 0) {
                updateSurroundings(x, y + 1);
            }
        }

        if (getGameInfoAt(x + 1, y + 1) == emptyHidden && xAdd && yAdd) {
            game[x + 1][y + 1] = getNumberOfSurroundingMines(x + 1, y + 1);
            if (game[x + 1][y + 1] == 0) {
                updateSurroundings(x + 1, y + 1);
            }
        }

        if (getGameInfoAt(x + 1, y) == emptyHidden && xAdd) {
            game[x + 1][y] = getNumberOfSurroundingMines(x + 1, y);
            if (game[x + 1][y] == 0) {
                updateSurroundings(x + 1, y);
            }
        } 

        if (getGameInfoAt(x + 1, y - 1) == emptyHidden && xAdd && yMinus) {
            game[x + 1][y - 1] = getNumberOfSurroundingMines(x + 1, y - 1);
            if (game[x + 1][y - 1] == 0) {
                updateSurroundings(x + 1, y - 1);
            }
        }

        if (getGameInfoAt(x, y - 1) == emptyHidden && yMinus) {
            game[x][y - 1] = getNumberOfSurroundingMines(x, y - 1);
            if (game[x][y - 1] == 0) {
                updateSurroundings(x, y - 1);
            }
        } 

        if (getGameInfoAt(x - 1, y - 1) == emptyHidden && xMinus && yMinus) {
            game[x - 1][y - 1] = getNumberOfSurroundingMines(x - 1, y - 1);
            if (game[x - 1][y - 1] == 0) {
                updateSurroundings(x - 1, y - 1);
            }
        }

        if (getGameInfoAt(x - 1, y) == emptyHidden && xMinus) {
            game[x - 1][y] = getNumberOfSurroundingMines(x - 1, y);
            if (game[x - 1][y] == 0) {
                updateSurroundings(x - 1, y);
            }
        }

        if (getGameInfoAt(x - 1, y + 1) == emptyHidden && xMinus && yAdd) {
            game[x - 1][y + 1] = getNumberOfSurroundingMines(x - 1, y + 1);
            if (game[x - 1][y + 1] == 0) {
                updateSurroundings(x - 1, y + 1);
            }
        } 

    }

    private int getNumberOfSurroundingMines(int x, int y) {

        int mineAmount = 0;

        boolean yAdd = (y + 1 < game.length);
        boolean yMinus = (y - 1 >= 0);
        boolean xAdd = (x + 1 < game.length);
        boolean xMinus = (x - 1 >= 0);

        if ((getGameInfoAt(x, y + 1) == mineHidden || getGameInfoAt(x, y + 1) == mineFlag) && yAdd) {
            mineAmount++;
        }

        if ((getGameInfoAt(x + 1, y + 1) == mineHidden || getGameInfoAt(x + 1, y + 1) == mineFlag) && xAdd && yAdd) {
            mineAmount++;
        }

        if ((getGameInfoAt(x + 1, y) == mineHidden || getGameInfoAt(x + 1, y) == mineFlag) && xAdd) {
            mineAmount++;
        }

        if ((getGameInfoAt(x + 1, y - 1) == mineHidden || getGameInfoAt(x + 1, y - 1) == mineFlag) && xAdd && yMinus) {
            mineAmount++;
        }

        if ((getGameInfoAt(x, y - 1) == mineHidden || getGameInfoAt(x, y - 1) == mineFlag) && yMinus) {
            mineAmount++;
        }

        if ((getGameInfoAt(x - 1, y - 1) == mineHidden || getGameInfoAt(x - 1, y - 1) == mineFlag) && xMinus && yMinus) {
            mineAmount++;
        }

        if ((getGameInfoAt(x - 1, y) == mineHidden || getGameInfoAt(x - 1, y) == mineFlag) && xMinus) {
            mineAmount++;
        }

        if ((getGameInfoAt(x - 1, y + 1) == mineHidden || getGameInfoAt(x - 1, y + 1) == mineFlag) && xMinus && yAdd) {
            mineAmount++;
        }

        return mineAmount;

    }

    private int getGameInfoAt(int x, int y) {
        x = Math.min(Math.max(0, x), game.length - 1);
        y = Math.min(Math.max(0, y), game.length - 1);

        return game[x][y];

    }

    private boolean checkWin() {
        for (int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[0].length; j++) {
                if (game[i][j] == mineHidden) {
                    return false;
                } else if (game[i][j] == emptyFlag) {
                    return false;
                }
            }
        }
        return true;
    }

    private void revealBoard() {
        for (int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[0].length; j++) {
                if (game[i][j] == mineHidden || game[i][j] == mineFlag) {
                    game[i][j] = mineOpened;
                }
            }
        }
    }

}