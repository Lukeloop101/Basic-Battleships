import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class battleship {
    private static final int GRID_SIZE = 5;
    //the amount is one extra
    private static final int SHIP_COUNT = 4;
    //same size
    private static boolean[][] ships = new boolean[GRID_SIZE][GRID_SIZE];
    private static JButton[][] buttons = new JButton[GRID_SIZE][GRID_SIZE];
    private static int shipsRemaining = SHIP_COUNT;
    //a counter for how many we clicked
    private static int attempts = 0;
    //creating the outside box
    private static void createGameWindow()
    {
        JFrame frame = new JFrame("Battleship");
        
        //size control could make a variable and move everything that can change
        frame.setSize(500,400);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Make the grid not on top of each other
        frame.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));

        //add things to make the game board
        for (int rows = 0; rows < GRID_SIZE; rows++) {
            for (int columns = 0; columns < GRID_SIZE; columns++) {
                JButton button = new JButton();
                button.setBackground(new Color(0x617FA6));

                //makes so when clicked it does stuff for the clicked button correctly to where the ships are
                button.addActionListener(new ButtonClickListener(rows, columns));
                buttons[rows][columns] = button;
                frame.add(button);
            }
        }




        //at end so everythign properly done
        frame.setVisible(true);
    }

    //placing ships
    private static void initializeShips() {
        Random random = new Random();
        int placedShips = 0;

        //only one cool placement of ship being next to each other. although realise now not nessary
        //if want to make more first make in a loop for how many you want
        int coolPlacedShips=0;
        //first
        int row = random.nextInt(GRID_SIZE);
        int col = random.nextInt(GRID_SIZE);

        if (!ships[row][col]) {
            ships[row][col] = true;
            placedShips++;
            //now doing a second next to
            boolean shipChecker=false;
            while (shipChecker==false)
            {
                boolean randomBoolean = random.nextBoolean();
                int randomNumber;

                if (randomBoolean==true) {
                    randomNumber = 1;
                } else {
                    randomNumber = -1;
                }
                //if true we do row if false we do column
                randomBoolean = random.nextBoolean();
                //checks row will be in range and not get outside error
                if(randomBoolean==true&&row+randomNumber>=0&&row+randomNumber<GRID_SIZE)
                {
                    ships[row+randomNumber][col] = true;
                    //not nessary but will help if I code properly to allow for more cool placed ships although I dont want to deal with 0 outcomes avalible and then break although very unlikely.
                    coolPlacedShips++;
                    shipChecker=true;
                }
                else if(randomBoolean==false&&col+randomNumber>=0&&col+randomNumber<GRID_SIZE)
                {
                    ships[row][col+randomNumber] = true;
                    //not nessary but will help if I code properly to allow for more cool placed ships although I dont want to deal with 0 outcomes avalible and then break although very unlikely.
                    coolPlacedShips++;
                    shipChecker=true;
                }

            }

            
        }
        //base 1 by 1 ships as I lame 
        while (placedShips < SHIP_COUNT) 
        {
            row = random.nextInt(GRID_SIZE);
            col = random.nextInt(GRID_SIZE);

            if (!ships[row][col]) 
            {
                ships[row][col] = true;
                placedShips++;
            }
        }
    }

    private static class ButtonClickListener implements ActionListener {
        //they are final so Somehow something else cant change the instance of them
        private final int row;
        private final int col;
    
        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }
    
        //just a way to idenify user input in gui
        public void actionPerformed(ActionEvent e) {
            //collects the button placement  for where clicked so we can call just button
            JButton button = buttons[row][col];
    
            // Check if already clicked
            if (button.getText().isEmpty()) {
                attempts++; // Increment attempts
                if (ships[row][col]) { // Hit a ship
                    button.setText("X");
                    button.setBackground(Color.GREEN);
                    ships[row][col] = false; // Mark this part of the ship as hit
                    shipsRemaining--;
    
                    // used at -1 as i added the cool.
                    if (shipsRemaining == -1) {
                        JOptionPane.showMessageDialog(null, "Congratulations! You sunk all the ships in " + attempts + " attempts.");
                        resetGame(); // Reset the game
                    }
                } else { // Miss
                    button.setText("O");
                    button.setBackground(Color.WHITE);
                }
            }
        }
    }

    private static void resetGame() {
        //reset the values
        shipsRemaining = SHIP_COUNT;
        attempts = 0;
        //reset ship placement
        initializeShips();

        for (int rows = 0; rows < GRID_SIZE; rows++) {
            for (int columns = 0; columns < GRID_SIZE; columns++) {
                //replace everything back to base
                buttons[rows][columns].setBackground(new Color(0x617FA6));
                buttons[rows][columns].setText("");
            }
        }
    
    }

    public static void main(String[] args) {
        initializeShips();
        createGameWindow();
    }
}
