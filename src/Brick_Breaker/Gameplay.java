package Brick_Breaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play=false;
    private int score = 0;

    private int totalBricks = 21;
    private Timer time;
    private int delay = 1;
    private int playerX=310;

    private int ballposX=120;
    private int ballposY=200;

    private int ballSpeed = 4;
    private int ballXdir = -ballSpeed;
    private int ballYdir = -ballSpeed;

    private boolean moveLeft = false;
    private boolean moveRight = false;



    private MapGenrator map;

    public Gameplay(){
        map = new MapGenrator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay,this);
        time.start();
    }

    public void paint(Graphics g){


        //background
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);

        //drawing map
        map.draw((Graphics2D)g);

        //border
        g.setColor(Color.blue);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);

        //scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(" "+score,590,30);

        //the paddle
        g.setColor(Color.DARK_GRAY);
        g.fillRect(playerX,550,100,8);

        //the ball
        g.setColor(Color.MAGENTA);
        g.fillOval(ballposX,ballposY,20,20);

        if(totalBricks <= 0){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString(" You Won ",260,300);

            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Press ENTER to restart "+score,230,350);
        }


        if(ballposY >570){
            play=false;
            ballXdir=0;
            ballYdir=0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over,\n Scores : "+score,190,300);

            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Press ENTER to restart "+score,230,350);
        }

        g.dispose();
    }

    public void actionPerformed(ActionEvent e){
        time.start();
        if(play){
            if (moveRight && playerX < 600) {
                playerX += 10; // Increase for faster movement
            }
            if (moveLeft && playerX > 0) {
                playerX -= 10;
            }

            if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8)))
            {
                ballYdir = -ballYdir;
            }

            A: for(int i=0;i<map.map.length;i++){
                for(int j=0;j<map.map[0].length;j++){
                    if(map.map[i][j]>0){
                        int brickX = j*map.brickwidth +80;
                        int brickY = i*map.brickheight +50;
                        int brickwidth = map.brickwidth;
                        int brickheight = map.brickheight;

                        Rectangle rect = new Rectangle(brickX,brickY,brickwidth,brickheight);
                        Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
                        Rectangle brickRect = rect;

                        if(ballRect.intersects(brickRect)){
                            map.setBrickValue(0,i,j);
                            totalBricks--;
                            score +=5;

                            if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            }else {
                                ballYdir =-ballYdir;
                            }
                            break A;
                        }



                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX < 0){
                ballXdir = -ballXdir;
            }
            if(ballposY < 0){
                ballYdir = -ballYdir;
            }
            if(ballposX > 670){
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }
    public void keyTyped(KeyEvent e){}

    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            moveRight = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            moveLeft = false;
        }
    }
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX < 600){  // Only move right if within bounds
                moveRight = true;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX > 0){    // Only move left if within bounds
                moveLeft = true;
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -ballSpeed;
                ballYdir = -ballSpeed;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenrator(3,7);
                repaint();
            }
        }
    }


    public void moveRight(){
        play = true;
        playerX +=20 ;
    }
    public void moveLeft(){
        play = true;
        playerX -=20 ;
    }
}