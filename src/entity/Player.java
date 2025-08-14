package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 24;

        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(new File("./res/player/boy_up_1.png"));
            up2 = ImageIO.read(new File("./res/player/boy_up_2.png"));
            left1 = ImageIO.read(new File("./res/player/boy_left_1.png"));
            left2 = ImageIO.read(new File("./res/player/boy_left_2.png"));
            down1 = ImageIO.read(new File("./res/player/boy_down_1.png"));
            down2 = ImageIO.read(new File("./res/player/boy_down_2.png"));
            right1 = ImageIO.read(new File("./res/player/boy_right_1.png"));
            right2 = ImageIO.read(new File("./res/player/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        boolean movementKeyPressed = keyH.upPressed || keyH.leftPressed || keyH.rightPressed || keyH.downPressed;

        if (movementKeyPressed) {

            if (keyH.upPressed) {
                direction = "up";
            }

            else if (keyH.leftPressed) {
                direction = "left";
            }

            else if (keyH.downPressed) {
                direction = "down";
            }

            else if (keyH.rightPressed) {
                direction = "right";
            }

            // * CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // * IF COLLISION IS FALSE PLAYER CAN'T MODE
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                image = (spriteNum == 1) ? up1 : up2;
                break;
            case "left":
                image = (spriteNum == 1) ? left1 : left2;
                break;
            case "down":
                image = (spriteNum == 1) ? down1 : down2;
                break;
            case "right":
                image = (spriteNum == 1) ? right1 : right2;
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}
