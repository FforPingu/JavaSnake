import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Tile {
        int x;
        int y;

        Tile(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    
    int boardWidth;
    int boardHeight;
    int tileSize = 25;


    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //food

    Tile food;
    Random random;

    // game logic 
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;

    //dark green color for snake head

    Color dark_green = new Color(0,128,0);



    SnakeGame(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);

        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();


        food = new Tile(10,10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void restartGame() {
        snakeHead = new Tile(5, 5);
        snakeBody.clear();
        placeFood();
        velocityX = 0;
        velocityY = 0;
        gameOver = false;
        gameLoop.start();
    }

    // called when the score increases 
    public void increaseVelocity(){
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        // grid paint
        
          /*for (int i = 0; i < boardWidth / tileSize; i++) {
          
          g.drawLine(i * tileSize, 0, i * tileSize, boardHeight);
          g.drawLine(0, i * tileSize, boardWidth, i* tileSize);
          
          }
         */
         
        
        

        // food paint
        g.setColor(Color.red);
        //g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize,true);
        
        
        // snake paint
        
        g.setColor(dark_green);
        //g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize,true);

        // snake body paint
        g.setColor(Color.green);
        for(int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            //g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize,true);
        }

        // score paint


        
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();
        
        if(gameOver){
            int textWidth = fm.stringWidth("Game Over: "+ String.valueOf(snakeBody.size()));
            int positionTextX = (boardWidth - textWidth) /2;
            int positionTextY = (boardWidth - textWidth) /2;

            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()),positionTextX ,positionTextY );
        }
        else{
            g.setColor(Color.green);
            g.drawString("Score: "+ String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
    }


    public void placeFood(){
        this.food.x = random.nextInt(boardWidth / tileSize);
        this.food.y = random.nextInt(boardHeight / tileSize);
    }

    public Boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;

    }

    


    public void move(){
        // snake food
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        // snake body
        for (int i = snakeBody.size()-1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }


        //snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // game over conditions
        for(int i = 0 ; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            if(collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }

        if(snakeHead.x * tileSize < 0 || snakeHead.x * tileSize> boardWidth ||
           snakeHead.y* tileSize < 0  || snakeHead.y * tileSize > boardHeight){
                gameOver=true;
           }
        
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
    }

    //key Settings
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityX = 0;
            velocityY = -1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityX = 0;
            velocityY = 1;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX  != -1){
            velocityX = 1;
            velocityY = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityX = -1;
            velocityY = 0;
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER ){
            restartGame();
        }
    }

    

    // implemented but not used 
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}

}
