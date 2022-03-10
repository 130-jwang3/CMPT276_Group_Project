package com.CMPT276_Group1.project.entity;


import com.CMPT276_Group1.project.*;
import com.CMPT276_Group1.project.object.*;

import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    public int hasRegularReward = 0;
    public int hasSpecialReward = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValue();
        getPlayerImage();
    }

    public void setDefaultValue() {
        x = 48;
        y = 48;
        speed = 4;
        direction = "down";

        //Player Status
        maxLife=6;
        life=maxLife;
    }

    public void getPlayerImage() {
        up1 = setUp("main_character_up_1");
        up2 = setUp("main_character_up_2");
        down1 = setUp("main_character_down_1");
        down2 = setUp("main_character_down_2");
        left1 = setUp("main_character_left_1");
        left2 = setUp("main_character_left_2");
        right1 = setUp("main_character_right_1");
        right2 = setUp("main_character_right_2");
    }

    public BufferedImage setUp(String imageName) {
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
            image = utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void update() {
        if (keyHandler.downPressed || keyHandler.upPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            } else {
                direction = "right";
            }

            //check tile collision
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            //check object collision
            int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            //check trap
            gamePanel.trap.checkEvent();

            //if collision is false player can move
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> y -= speed;
                    case "down" -> y += speed;
                    case "left" -> x -= speed;
                    case "right" -> x += speed;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gamePanel.obj[i].name;
            switch (objectName) {
                case "Regular Reward" -> {
                    hasRegularReward++;
                    gamePanel.obj[i] = null;
                    gamePanel.playSoundEffect(1);
                }
                case "Special Reward" -> {
                    hasSpecialReward++;
                    gamePanel.obj[i] = null;
                    gamePanel.playSoundEffect(2);
                }
                case "Exit" -> {
                    if (hasRegularReward == 1) {
                        gamePanel.stopMusic();
                        gamePanel.playSoundEffect(3);
                        gamePanel.gameState=gamePanel.finishState;
                    }
                }
            }
        }
    }

    public void draw(Graphics2D g2D) {
        BufferedImage image = null;
        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
        }
        g2D.drawImage(image, x, y, null);
    }
}