import java.awt.Color;

public class MoranguinhoPlayer extends Player {
    int i = 0;
    Point enemy = null;
    Point cornerToGo;
    boolean isloading = false;

    int startingX;
    int finalX;
    int angleModifierX;
    int startingY;
    int finalY;
    int angleModifierY;

    float angle;
    float modifyAngle;

    int currentShot = 0;

    public MoranguinhoPlayer(Point location) {
        super(location, Color.red, Color.green, "Moranguinho");

        if (getLocation().getX() <= 960 && getLocation().getY() <= 540) {
            cornerToGo = new Point(0, 0);

            startingY = 50;
            angleModifierY = -1;

            startingX = 0;
            angleModifierX = 1;
        } else if (getLocation().getX() <= 960 && getLocation().getY() >= 540) {
            cornerToGo = new Point(0, 1080);
            angleModifierX = 1;
            angleModifierY = 1;
        } else if (getLocation().getX() >= 960 && getLocation().getY() <= 540) {
            cornerToGo = new Point(1920, 0);
            angleModifierX = -1;
            angleModifierY = -1;
        } else if (getLocation().getX() >= 960 && getLocation().getY() >= 540) {
            cornerToGo = new Point(1920, 1080);
            angleModifierX = -1;
            angleModifierY = +1;
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
    
        if (enemy == null) {
            InfraRedSensor(5f * i++);
            MoveTo(cornerToGo);
            return;
        }
        
        InfraRedSensorTo(enemy);
    
        float dx = enemy.getX() - getLocation().getX();
        float dy = enemy.getY() - getLocation().getY();
        if (dx * dx + dy * dy >= 300f * 300f && (i % 10 == 0)) {
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

            if (i++ % 3 == 0)
                ShootTo(angleToShoot());
                System.out.println("atirei");

            if (currentShot >= 100)
                currentShot = 0;
        }
    }
}
