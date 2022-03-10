package com.CMPT276_Group1.project;

import com.CMPT276_Group1.project.entity.*;

import java.awt.event.*;

public class KeyHandler implements KeyListener {
    public boolean upPressed,downPressed,leftPressed, rightPressed;
    GamePanel gamePanel;
    //Debug
    boolean checkDrawTime=false;

    public KeyHandler(GamePanel gamePanel){
        this.gamePanel=gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();

        //Title state
        if(gamePanel.gameState==gamePanel.titleState){
            if(code==KeyEvent.VK_W){
                gamePanel.ui.commandNum--;
                if(gamePanel.ui.commandNum<0){
                    gamePanel.ui.commandNum=2;
                }
            }
            if(code==KeyEvent.VK_S){
                gamePanel.ui.commandNum++;
                if(gamePanel.ui.commandNum>2){
                    gamePanel.ui.commandNum=0;
                }
            }
            if(code==KeyEvent.VK_ENTER){
                if(gamePanel.ui.commandNum==0){
                    gamePanel.gameState= gamePanel.playState;
                    gamePanel.stopMusic();
                    gamePanel.playMusic(0);
                }
                if(gamePanel.ui.commandNum==1){
                    //load game
                }
                if(gamePanel.ui.commandNum==2){
                    System.exit(0);
                }
            }
        }

        //Play state
        if(gamePanel.gameState== gamePanel.playState){
            if(code==KeyEvent.VK_W){
                upPressed=true;
            }
            if(code==KeyEvent.VK_S){
                downPressed=true;
            }
            if(code==KeyEvent.VK_A){
                leftPressed=true;
            }
            if(code==KeyEvent.VK_D){
                rightPressed=true;
            }
            if(code==KeyEvent.VK_P){
                gamePanel.gameState=gamePanel.pauseState;
            }
        }else if(gamePanel.gameState== gamePanel.pauseState){
            if(code==KeyEvent.VK_P){
                gamePanel.gameState=gamePanel.playState;
            }
        }

        //Finish state
        if(gamePanel.gameState==gamePanel.finishState){
            if(code==KeyEvent.VK_ENTER){
                gamePanel.setupGameObject();
                gamePanel.player=new Player(gamePanel, gamePanel.keyHandler);
            }
        }
        //Debug
        if(code==KeyEvent.VK_T){
            checkDrawTime= !checkDrawTime;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code=e.getKeyCode();
        if(code==KeyEvent.VK_W){
            upPressed=false;
        }
        if(code==KeyEvent.VK_S){
            downPressed=false;
        }
        if(code==KeyEvent.VK_A){
            leftPressed=false;
        }
        if(code==KeyEvent.VK_D){
            rightPressed=false;
        }
    }
}