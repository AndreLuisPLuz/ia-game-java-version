import java.awt.Color;

public class Massacration2 extends Player{
    Massacration2(Point location) {
        super(location, Color.pink, Color.blue, "Massacration");
    }

    int i = 0;
    Point enemy = null;
    boolean foundenemy = false;
    boolean isloading = false;
    int frames = 0;
    float distance = 0;

    int isRunning = -1;

    @Override
    protected void loop()
    {
        if(getEnergy() < 20) {
            isloading = true;
        }
        
        if (isRunning > -1)
        {
            isRunning++;
            if (isRunning == 20)
            {
                isRunning = -1;
            }

            MoveTo(new Point(
                getLocation().getX() + 5,
                getLocation().getY() + 5
            ));
            return;
        }

        // if(getEnergy() == 30) {
            
        //     if(getLocation() == newpoint) {
        //         StopMove();
        //         return;
        //     }
        // }
        
        if(getEnergy() > 35) {
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
        } else {
            InfraRedSensorTo(enemy);
            float dx = enemy.getX() - getLocation().getX();
            float dy = enemy.getY() - getLocation().getY();
            distance = dx * dx + dy * dy;
        }

        if (distance >= 600f * 600f) {
            InfraRedSensor(5f * i++);
            frames = 0;
            return;
        } else {
            if (i++ % 5 == 0 && frames < 15){
                ShootTo(enemy);
                frames++;
            }
            else if (frames > 10)
            {
                isRunning = 0;
            }
        }
            
    }
}
