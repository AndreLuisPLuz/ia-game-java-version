import java.awt.Color;

public class MoranguinhoPlayer extends Player {
    int i = 0;
    Point enemy = null;
    Point cornerToGo;
    boolean isloading = false;

    public MoranguinhoPlayer(Point location) {
        super(location, Color.red, Color.green, "Moranguinho");

        if (location.getX() <= 1000 && location.getY() <= 1000) {
            cornerToGo = new Point(0, 0);
        } else if (location.getX() <= 1000 && location.getY() >= 1000) {
            cornerToGo = new Point(0, 2000);
        } else if (location.getX() >= 1000 && location.getY() <= 1000) {
            cornerToGo = new Point(1000, 0);
        } else if (location.getX() >= 1000 && location.getY() >= 1000) {
            cornerToGo = new Point(1000, 1000);
        }
    }

    private void fightOrFlight() {
        enemy = 
            getEnemiesInInfraRed().size() > 0 ?
            getEnemiesInInfraRed().get(0) : null;
    
        if (getEnergy() < 30)
        {
            StopMove();
            isloading = true;
            enemy = null;
        }
    
        if (getEnergy() > 50)
            isloading = false;
    
        if (isloading)
            return;
    
        if (enemy == null) {
            InfraRedSensor(5f * i++);
            return;
        }
        
        InfraRedSensorTo(enemy);
    
        float dx = enemy.getX() - getLocation().getX();
        float dy = enemy.getY() - getLocation().getY();
        if (dx * dx + dy * dy >= 200f * 200f) {
            ShootTo(enemy);
        }
        else {
            MoveTo(cornerToGo);
        }
    }

    @Override
    protected void loop()
    {
        fightOrFlight();
        // if (getLocation() != cornerToGo) {
        //     fightOrFlight();
        // } else {

        // }
    }
}
