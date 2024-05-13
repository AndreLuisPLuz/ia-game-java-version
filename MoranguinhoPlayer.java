import java.awt.Color;
import java.util.ArrayList;

public class MoranguinhoPlayer extends Player {
    int i = 0;
    int cycle = 0;
    Point enemy = null;
    Point cornerToGo;
    boolean isloading = false;

    int startingX;
    int angleModifierX;
    int startingY;
    int angleModifierY;

    float angle;
    float modifyAngle;

    int currentShot = 0;
    int shotsPerRound = 20;

    public MoranguinhoPlayer(Point location) {
        super(location, Color.red, Color.green, "Moranguinho");

        if (getLocation().getX() <= 960 && getLocation().getY() <= 540) {
            cornerToGo = new Point(0, 0);
            startingY = shotsPerRound * 5;
            angleModifierY = -1;

            startingX = 0;
            angleModifierX = 1;
        } else if (getLocation().getX() <= 960 && getLocation().getY() >= 540) {
            cornerToGo = new Point(0, 1080);
            startingY = 1080 - shotsPerRound * 5;
            angleModifierY = 1;

            startingX = 0;
            angleModifierX = 1;
        } else if (getLocation().getX() >= 960 && getLocation().getY() <= 540) {
            cornerToGo = new Point(1920, 0);
            startingY = shotsPerRound * 5;
            angleModifierY = -1;

            startingX = 1920;
            angleModifierX = -1;
        } else if (getLocation().getX() >= 960 && getLocation().getY() >= 540) {
            cornerToGo = new Point(1920, 1080);
            startingY = 1080 - shotsPerRound * 5;
            angleModifierY = 1;

            startingX = 1920;
            angleModifierX = -1;
        }
    }

    private void fightOrFlight() {
        enemy = 
            getEnemiesInInfraRed().size() > 0 ?
            getEnemiesInInfraRed().get(0) : null;
    
        if (getEnergy() < 30)
        {
            // StopMove();
            isloading = true;
            enemy = null;
        }
    
        if (getEnergy() > 50)
            isloading = false;
    
        if (isloading)
            return;
    
        if (getLife() < 50) {
            MoveTo(cornerToGo);
        }

        if (enemy == null) {
            InfraRedSensor(5f * i++);
            MoveTo(cornerToGo);
            return;
        }
        
        InfraRedSensorTo(enemy);
    
        float dx = enemy.getX() - getLocation().getX();
        float dy = enemy.getY() - getLocation().getY();
        if (dx * dx + dy * dy <= 600f * 600f && (cycle % 7 == 0)) {
            ShootTo(enemy);
        }

        MoveTo(cornerToGo);
    }

    private Point angleToShoot() {
        int x = startingX + (currentShot * 5 * angleModifierX);
        int y = startingY + (currentShot * 5 * angleModifierY);

        currentShot++;

        return new Point(x, y);
    }

    private boolean comparePoint(Point loc1, Point loc2) {
        return (Math.round(loc1.getX()) == Math.round(loc2.getX()) && Math.round(loc1.getY()) == Math.round(loc2.getY()));
    }

    @Override
    protected void loop()
    {
        // fightOrFlight();
        if (!comparePoint(getLocation(), cornerToGo)) {
            fightOrFlight();
        } else {
            StopMove();

            if (getEnergy() < 20)
            {
                isloading = true;
                enemy = null;
            }
        
            if (getEnergy() > 50)
                isloading = false;
        
            if (isloading)
                return;

            if (cycle % 2 == 0)
                ShootTo(angleToShoot());

            if (currentShot >= 20)
                currentShot = 0;
        }

        cycle++;
    }
}
