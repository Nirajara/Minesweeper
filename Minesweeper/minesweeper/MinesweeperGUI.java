package minesweeper;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.awt.event.KeyListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class MinesweeperGUI {

    private JFrame gameGUI;
    private JButton[][] gameCells;

    private JFrame menu;

    private JFrame customMenu;

    private int currentGameDimension;
    private int totalMines;
    private JLabel minesLeft;
    private boolean gameMode;
    private boolean flagClickingMode;
    private boolean mouseRightClick;
    private int[] mostRecentCellClicked;
    private boolean newMove;

    private int spacing;

    // for custom menu GUI
    private int customMenuWidth;
    private int customMenuHeight;

    // for menu GUI
    private int menuWidth;
    private int menuHeight;
    private int menuItemWidth;
    private int menuItemHeight;

    // for game GUI
    private int easyDimension;
    private int mediumDimension;
    private int hardDimension;
    private int easyMines;
    private int mediumMines;
    private int hardMines;
    private int cellSize;
    private int frameWidthCompensation;
    private int frameHeightCompensation;

    // for warning GUI
    private int warningWidth;
    private int warningHeight;

    // for all
    private int backButtonWidth;
    private int backButtonHeight;

    public MinesweeperGUI() {

        gameMode = false;
        flagClickingMode = false;
        mouseRightClick = false;
        mostRecentCellClicked = new int[2];
        Arrays.fill(mostRecentCellClicked, -1);
        newMove = false;

        spacing = 4;

        customMenuWidth = 400;
        customMenuHeight = 200;

        menuWidth = 500;
        menuHeight = 250;
        menuItemWidth = 100;
        menuItemHeight = 25;

        easyDimension = 12;
        easyMines = 18;
        mediumDimension = 15;
        mediumMines = 35;
        hardDimension = 20;
        hardMines = 80;
        cellSize = 30;
        frameWidthCompensation = 12;
        frameHeightCompensation = 35;

        warningWidth = 400;
        warningHeight = 140;

        backButtonWidth = 45;
        backButtonHeight = 25;

    }

    public int getCurrentGameDimension() {
        return currentGameDimension;
    }

    public int getMineAmount() {
        return totalMines;
    }

    public boolean getGameMode() {
        return gameMode;
    }

    public boolean getFlagClickingMode() {
        return (flagClickingMode || mouseRightClick);
    }

    public int[] getMostRecentCellClicked() {
        return mostRecentCellClicked;
    }

    public boolean wasNewMoveMade() {
        return newMove;
    }

    public void createMenu() {

        menu = makeGUI(menuWidth, menuHeight, "Menu");

        JLabel welcomeText = new JLabel(
                "<html><center>Welcome to Minesweeper<br><br>Select your game mode or create a custom game</center></html>");

        welcomeText.setSize(welcomeText.getPreferredSize());

        welcomeText.setLocation(getCenter(menu.getWidth(), welcomeText.getWidth()), 0);

        int buttonXCenter = getCenter(menu.getWidth(), menuItemWidth);

        JButton easy = makeMenuButton("Easy", buttonXCenter, menuItemHeight * 3);
        JButton medium = makeMenuButton("Medium", buttonXCenter, menuItemHeight * 4);
        JButton hard = makeMenuButton("Hard", buttonXCenter, menuItemHeight * 5);
        JButton custom = makeMenuButton("Custom", buttonXCenter, menuItemHeight * 6);

        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                menu.setVisible(false);

                if (e.getSource() == easy) {
                    createGame(easyDimension, easyMines);
                } else if (e.getSource() == medium) {
                    createGame(mediumDimension, mediumMines);
                } else if (e.getSource() == hard) {
                    createGame(hardDimension, hardMines);
                } else if (e.getSource() == custom) {
                    createCustomMenu();
                }
            }

        };

        easy.addActionListener(actionListener);
        medium.addActionListener(actionListener);
        hard.addActionListener(actionListener);
        custom.addActionListener(actionListener);

        menu.add(welcomeText);

        menu.add(easy);
        menu.add(medium);
        menu.add(hard);
        menu.add(custom);

        menu.setVisible(true);

    }

    public void updateGameGUI(int[][] gameInfo, int minesLeft, boolean gameOverStatus, boolean gameWinStatus) {
        newMove = false;
        mouseRightClick = false;
        gameGUI.requestFocusInWindow();

        this.minesLeft.setText(": " + Integer.toString(minesLeft));

        try {
            for (int i = 0; i < gameInfo.length; i++) {
                for (int j = 0; j < gameInfo.length; j++) {
                    if (gameInfo[i][j] < 0) {
                        switch (gameInfo[i][j]) {
                            case -1: 
                                gameCells[i][j].setIcon(null);
                                break;
                            case -2:
                                gameCells[i][j].setIcon(Icons.Flag.getInstance().getFlagImage());
                                break;
                            case -3: 
                                gameCells[i][j].setIcon(Icons.Flag.getInstance().getFlagImage());
                                break;
                            case -4:  
                                gameCells[i][j].setIcon(null);
                                break;
                            case -5:
                                gameCells[i][j].setIcon(Icons.Mine.getInstance().getMineImage());
                                break;
                            default:
                                break;
                        }
                    } else {
                        gameCells[i][j].setBackground(Color.WHITE);
                        switch (gameInfo[i][j]) {
                            case 1:
                                gameCells[i][j].setText("1");
                                gameCells[i][j].setForeground(Color.BLUE);
                                break;
                            case 2:
                                gameCells[i][j].setText("2");
                                gameCells[i][j].setForeground(Color.GREEN);
                                break;
                            case 3:
                                gameCells[i][j].setText("3");
                                gameCells[i][j].setForeground(Color.RED);
                                break;
                            case 4:
                                gameCells[i][j].setText("4");
                                gameCells[i][j].setForeground(Color.MAGENTA);
                                break;
                            case 5:
                                gameCells[i][j].setText("5");
                                gameCells[i][j].setForeground(Color.YELLOW);
                                break;
                            case 6:
                                gameCells[i][j].setText("6");
                                gameCells[i][j].setForeground(Color.BLACK);
                                break;
                            case 7:
                                gameCells[i][j].setText("7");
                                gameCells[i][j].setForeground(Color.DARK_GRAY);
                                break;
                            case 8:
                                gameCells[i][j].setText("8");
                                gameCells[i][j].setForeground(Color.GRAY);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Updating Game Issue: " + e);
        }

        if (gameWinStatus) {

            gameMode = false;

            JFrame gameWin = makeGUI(warningWidth, warningHeight, "Game Over");
            gameWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JLabel gameWinText = new JLabel("<html><center>Congratulations! You won!</center></html>");
            
            gameWinText.setSize(175, 20);
            gameWinText.setLocation(getCenter(gameWin.getWidth(), gameWinText.getWidth()), spacing * 2);

            JButton menuButton = makeMenuButton("Menu", getCenter(gameWin.getWidth(), menuItemWidth) - menuItemWidth, gameWin.getHeight()/3);
            JButton playAgain = makeMenuButton("Play Again!", getCenter(gameWin.getWidth(), menuItemWidth) + menuItemWidth, gameWin.getHeight()/3);

            ActionListener actionListener = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == menuButton) {
                        gameGUI.dispose();
                        gameWin.dispose();
                        menu.setVisible(true);
                    } else if (e.getSource() == playAgain) {
                        gameGUI.dispose();
                        gameWin.dispose();
                        createGame(currentGameDimension, totalMines);
                    }
                }
                
            };

            menuButton.addActionListener(actionListener);
            playAgain.addActionListener(actionListener);

            gameWin.add(gameWinText);
            gameWin.add(menuButton);
            gameWin.add(playAgain);

            gameWin.setVisible(true);

        } else if (gameOverStatus) {
            gameMode = false;

            JFrame gameOver = makeGUI(warningWidth, warningHeight, "Game Over");
            gameOver.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JLabel gameOverText = new JLabel("Game Over :(");
            
            gameOverText.setSize(gameOverText.getPreferredSize());
            gameOverText.setLocation(getCenter(gameOver.getWidth(), gameOverText.getWidth()), spacing * 2);

            JButton menuButton = makeMenuButton("Menu", getCenter(gameOver.getWidth(), menuItemWidth) - menuItemWidth, gameOver.getHeight()/3);
            JButton tryAgain = makeMenuButton("Try Again", getCenter(gameOver.getWidth(), menuItemWidth) + menuItemWidth, gameOver.getHeight()/3);

            ActionListener actionListener = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == menuButton) {
                        gameGUI.dispose();
                        gameOver.dispose();
                        menu.setVisible(true);
                    } else if (e.getSource() == tryAgain) {
                        gameGUI.dispose();
                        gameOver.dispose();
                        createGame(currentGameDimension, totalMines);
                    }
                }
                
            };

            menuButton.addActionListener(actionListener);
            tryAgain.addActionListener(actionListener);

            gameOver.add(gameOverText);
            gameOver.add(menuButton);
            gameOver.add(tryAgain);

            gameOver.setVisible(true);
        }
        
    }

    private void createCustomMenu() {
        customMenu = makeGUI(customMenuWidth, customMenuHeight, "Custom Menu");

        JLabel instructionText = new JLabel("Create your custom game!");
        JLabel fieldText = new JLabel("Enter field size (11-20): ");
        JLabel mineText = new JLabel("Enter number of mines:");

        instructionText.setSize(instructionText.getPreferredSize());
        fieldText.setSize(fieldText.getPreferredSize());
        mineText.setSize(mineText.getPreferredSize());

        instructionText.setLocation(getCenter(customMenu.getWidth(), instructionText.getWidth()), customMenu.getHeight() / 10);
        fieldText.setLocation(customMenu.getWidth() / 4, customMenu.getHeight() / 4);
        mineText.setLocation(customMenu.getWidth() / 4, customMenu.getHeight() / 4 + fieldText.getHeight() + (2 * spacing));

        JTextField fieldInput = new JTextField(spacing);
        JTextField mineInput = new JTextField(spacing);

        fieldInput.setSize(menuItemWidth / 4, menuItemHeight);
        mineInput.setSize(menuItemWidth / 4, menuItemHeight);

        fieldInput.setLocation(fieldText.getX() + fieldText.getWidth() + spacing, fieldText.getY() - spacing);
        mineInput.setLocation(mineText.getX() + mineText.getWidth() + spacing, mineText.getY() - spacing);

        JButton submit = makeMenuButton("Submit", getCenter(customMenu.getWidth(), menuItemWidth), mineText.getY() + (mineText.getHeight() * 3));
        JButton back = makeBackButton();

        JLabel fieldError = new JLabel("Error!");
        JLabel mineError = new JLabel("Error!");

        fieldError.setForeground(Color.RED);
        mineError.setForeground(Color.RED);

        fieldError.setSize(fieldError.getPreferredSize());
        mineError.setSize(mineError.getPreferredSize());

        fieldError.setLocation(fieldInput.getX() + fieldInput.getWidth() + (spacing * 2), fieldText.getY());
        mineError.setLocation(mineInput.getX() + mineInput.getWidth() + (spacing * 2), mineText.getY());

        fieldError.setVisible(false);
        mineError.setVisible(false);

        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                customMenu.setVisible(false);

                if (e.getSource() == submit) {
                    try {
                        int fieldSize = Integer.parseInt(fieldInput.getText());
                        int mineAmount = Integer.parseInt(mineInput.getText());
                        mineAmount = Math.min(fieldSize * fieldSize, mineAmount);

                        if ((fieldSize >= 11) && (fieldSize <= 20) && (mineAmount > 0)) {
                            createGame(fieldSize, mineAmount);
                        } else {
                            throw new Exception("Invalid Input");
                        }

                    } catch (Exception exception) {
                        System.out.println("Custom Menu Error = " + exception);
                        fieldInput.setText("");
                        mineInput.setText("");
                        fieldError.setVisible(true);
                        mineError.setVisible(true);
                        customMenu.setVisible(true);
                        customMenu.setLocationRelativeTo(null);
                    }
                } else if (e.getSource() == back) {
                    menu.setVisible(true);
                    menu.setLocationRelativeTo(null);
                }

            }

        };

        submit.addActionListener(actionListener);
        back.addActionListener(actionListener);

        customMenu.add(instructionText);
        customMenu.add(fieldText);
        customMenu.add(mineText);

        customMenu.add(fieldInput);
        customMenu.add(mineInput);

        customMenu.add(submit);
        customMenu.add(back);

        customMenu.add(fieldError);
        customMenu.add(mineError);

        customMenu.setVisible(true);
    }

    private void createGame(int gameDimension, int mineAmount) {

        gameGUI = makeGUI((gameDimension * cellSize) + frameWidthCompensation, (gameDimension * cellSize)  + (backButtonHeight * 2) + frameHeightCompensation, "Minesweeper");
        gameCells = new JButton[gameDimension][gameDimension];

        currentGameDimension = gameDimension;
        totalMines = mineAmount;
        gameMode = true;
        flagClickingMode = false;

        JLabel mineIcon = new JLabel();

        minesLeft = new JLabel(": " + Integer.toString(totalMines));

        mineIcon.setIcon(Icons.Mine.getInstance().getMineImage());
        mineIcon.setSize(mineIcon.getPreferredSize());
        mineIcon.setLocation(getCenter(gameGUI.getWidth(), mineIcon.getWidth()), spacing * 2);
        minesLeft.setSize(minesLeft.getPreferredSize());
        minesLeft.setLocation(mineIcon.getX() + minesLeft.getWidth(), mineIcon.getY() + (spacing/2));

        JButton back = makeBackButton();

        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (e.getSource() == back) {
                    JFrame warning = makeGUI(warningWidth, warningHeight, "WARNING!");
                    
                    warning.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JLabel warningText = new JLabel("<html><center>WARNING: Your game progress will not be saved.</center></html>");
                    
                    warningText.setSize(300, 50);
                    warningText.setLocation(getCenter(warning.getWidth(), warningText.getWidth()), spacing);

                    JButton cancel = makeMenuButton("Cancel", getCenter(warning.getWidth(), menuItemWidth) - menuItemWidth, warning.getHeight()/3);
                    JButton proceed = makeMenuButton("Proceed", getCenter(warning.getWidth(), menuItemWidth) + menuItemWidth, warning.getHeight()/3);

                    ActionListener actionListener = new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (e.getSource() == cancel) {
                                warning.dispose();
                            } else if (e.getSource() == proceed) {
                                Arrays.fill(mostRecentCellClicked, -1);
                                gameGUI.dispose();
                                gameMode = false;
                                menu.setVisible(true);
                                warning.dispose();
                            }
                        }

                    };

                    cancel.addActionListener(actionListener);
                    proceed.addActionListener(actionListener);

                    warning.add(warningText);
                    warning.add(cancel);
                    warning.add(proceed);

                    warning.setVisible(true);
                }

            }

        };

        KeyListener keyListener = new KeyListener() {

            int keysPressed = 0;

            boolean ctrlDown = false;
            boolean shiftDown = false;

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    keysPressed++;
                    ctrlDown = true;
                }

                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    shiftDown = true;
                }

                flagClickingMode = (ctrlDown || shiftDown);

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    keysPressed--;
                    if (keysPressed == 0) {
                        ctrlDown = false;
                    }
                }
                
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    shiftDown = false;
                }

                flagClickingMode = (ctrlDown || shiftDown);
			}

        };

        MouseInputListener mouseListener = new MouseInputListener() {
            
            boolean flagClickingModeTemp;

            @Override
            public void mouseMoved(MouseEvent e) {}
        
            @Override
            public void mouseDragged(MouseEvent e) {}
        
            @Override
            public void mouseReleased(MouseEvent e) {}
        
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    mouseRightClick = true;
                }
            }
        
            @Override
            public void mouseExited(MouseEvent e) {}
        
            @Override
            public void mouseEntered(MouseEvent e) {}
        
            @Override
            public void mouseClicked(MouseEvent e) {
                mostRecentCellClicked[0] = ((JButton) e.getSource()).getX()/cellSize;
                mostRecentCellClicked[1] = (((JButton) e.getSource()).getY() - (backButtonHeight * 2))/cellSize;    
                newMove = true;
            }
        };

        for (int i = 0; i < gameCells.length; i++) {
            for (int j = 0; j < gameCells[0].length; j++) {
                gameCells[i][j] = makeGameButton(i * cellSize, (j * cellSize) + (backButtonHeight * 2));
                gameCells[i][j].setBackground(Color.LIGHT_GRAY);
                gameCells[i][j].addMouseListener(mouseListener);
                gameGUI.add(gameCells[i][j]);
            }
        }

        back.addActionListener(actionListener);

        gameGUI.addKeyListener(keyListener);
        gameGUI.add(back);
        gameGUI.add(mineIcon);
        gameGUI.add(minesLeft);
        gameGUI.setVisible(true);

    }

    private JFrame makeGUI(int width, int height, String name) {
        
        JFrame gui = new JFrame(name);

        gui.setSize(width, height);
        gui.setLayout(null);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //centers the gui
        gui.setLocationRelativeTo(null);

        gui.setResizable(false);

        //allows for keyboard input
        gui.setFocusable(true);

        return gui;

    }

    private JButton makeMenuButton(String text, int xLocation, int yLocation) {

        JButton button = new JButton(text);

        button.setSize(menuItemWidth, menuItemHeight);
        button.setLocation(xLocation, yLocation);

        return button;

    }

    private JButton makeGameButton(int xLocation, int yLocation) {
        
        JButton button = new JButton();

        button.setSize(cellSize, cellSize);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setLocation(xLocation, yLocation);

        return button;

    }

    private JButton makeBackButton() {

        JButton button = new JButton("Back");

        button.setSize(backButtonWidth, backButtonHeight);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setLocation(0, 0);
        
        return button;

    }

    private int getCenter(int frameDimension, int itemDimension) {
        int center = (frameDimension/2) - (itemDimension/2);
        return center;
    }

}