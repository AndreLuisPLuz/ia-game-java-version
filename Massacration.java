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

        enemy = 
            getEnemiesInInfraRed().size() > 0 ?
            getEnemiesInInfraRed().get(0) : null;

        if (enemy == null) {
            InfraRedSensor(5f * i++);
            frames = 0;
            return;
        }

        InfraRedSensorTo(enemy);
        float dx = enemy.getX() - getLocation().getX();
        float dy = enemy.getY() - getLocation().getY();
        if (dx * dx + dy * dy <= 600f * 600f)
            if (i++ % 5 == 0 && frames < 4){
                ShootTo(enemy);
                frames++;
            }
    }
}
