import javax.swing.*;


public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 600;
        int boardheight = boardWidth;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardheight);

        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}
