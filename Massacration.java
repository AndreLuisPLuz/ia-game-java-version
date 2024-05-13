import java.awt.Color;

public class Massacration extends Player{
    Massacration(Point location) {
        super(location, Color.pink, Color.blue, "Massacration");
    }

    int i = 0;
    Point enemy = null;
    boolean foundenemy = false;
    boolean isloading = false;
    int frames = 0;
    int ismoving = 0;
    Point newpoint;

    @Override
    protected void loop()
    {
        if(getEnergy() < 20) {
            isloading = true;
        } 

        if(getEnergy() > 40) {
            isloading = false;
        }

        if(isloading) return;

        if (ismoving == 1) {
            StartMove(newpoint);
            ResetInfraRed();
        } else {
            StopMove();
            enemy = null;
        }

        enemy = 
            getEnemiesInInfraRed().size() > 0 ?
            getEnemiesInInfraRed().get(0) : null;

        if (enemy == null) {
            InfraRedSensor(5f * i++);
            frames = 0;
            return;
        }

        InfraRedSensorTo(enemy);
        float dx1 = enemy.getX() - getLocation().getX();
        float dy1 = enemy.getY() - getLocation().getY();
        if (dx1 * dx1 + dy1 * dy1 <= 600f * 600f)
            if (i++ % 5 == 0){
                ShootTo(enemy);
                ismoving = 0;
                frames++;
            }
            if (frames >= 10) {
                newpoint = new Point(-enemy.getX(), -enemy.getY());
                ismoving = 1; 
            }
    }
}
