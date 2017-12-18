import java.awt.event.KeyEvent;
import java.util.Random;

public class Hero extends Character {
    private String faceDown = "hero-down.gif";
    private String faceRight = "hero-right.gif";
    private String faceLeft = "hero-left.gif";
    private String faceUp = "hero-up.gif";

    enum Directions {
        UP, DOWN, LEFT, RIGHT;
    }

    Hero() {
        maxHealthPoint = 20 + 3 * d6.getRandomDice();
        defendPoint = 2 * d6.getRandomDice();
        strikePoint = 5 + d6.getRandomDice();
        currentHealthPoint = maxHealthPoint;
        initCharacter();
    }

    public void initCharacter(){
        setImage(faceDown);
        x = 0;
        y = 0;
    }

    public void move(Directions dir){
        int newX = x, newY = y;

        switch (dir) {
            case UP:
                newY = y - 1;
                setImage(faceUp);
                break;

            case DOWN:
                newY = y + 1;
                setImage(faceDown);
                break;

            case LEFT:
                newX = x - 1;
                setImage(faceLeft);
                break;

            case RIGHT:
                newX = x + 1;
                setImage(faceRight);
                break;
        }

        if(!map.getTile(newX,newY).isSolid){
            x = newX;
            y = newY;
        }

        if(map.getTile(newX,newY).isSolid) {
        }
    }

    public void levelUp(){
        int chance = new Random().nextInt(10);
        if(chance == 0) {
            currentHealthPoint = maxHealthPoint;
        } else if(chance >= 1 && chance <= 4) {
            currentHealthPoint += maxHealthPoint/3;
        } else {
            currentHealthPoint += maxHealthPoint/10;
        }



    }

}


