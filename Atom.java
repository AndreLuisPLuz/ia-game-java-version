import java.awt.Color;

public class Atom extends Player {

    public Atom(Point location) {
        super(location, new Color(148, 0, 211, 100), Color.green, "Atom");
    }

    int searchindex = 0;
    int frame = 0;
    int points = 0;
    Point enemy = null;
    Point enemy2 = null;
    boolean isloading = false;
    int i = 0;
    int j = 0;
    int k = 0;
    int count_fires = 0;
    int stop_fire = 0;
    float dx;
    float dy;

    @Override
    protected void loop() {

        // AccurateSonar();
        // if(getEntitiesInAccurateSonar().isEmpty()){

        // }
        // else
        // {
        // MoveTo(getEntitiesInAccurateSonar().get(0));
        // }
        StartTurbo();
        enemy = getEnemiesInInfraRed().size() > 0 ? getEnemiesInInfraRed().get(0) : null;

        if (enemy == null) {
            InfraRedSensor(5f * i++);
            return;
        }
        if (getEnergy() < 10) {
            StopMove();
            isloading = true;
            StopTurbo();
        }

        if (getEnergy() > 60)
            isloading = false;

        if (getEnergy() > 90)
            isloading = false;

        if (isloading)
            return;

        if (i++ % 8 == 0) {
            InfraRedSensorTo(enemy);
            dx = enemy.getX() - getLocation().getX();
            dy = enemy.getY() - getLocation().getY();

        }

        // if (dx * dx + dy * dy >= 700f * 700f) {
        // AccurateSonar();
        // // if (getEntitiesInAccurateSonar().isEmpty()) {
        // // dx = enemy.getX() ;
        // // dy = enemy.getY() ;
        // MoveTo(getEntitiesInAccurateSonar().get(0));
        // ShootTo(enemy);

        // // } else {
        // // MoveTo(getEntitiesInAccurateSonar().get(0));
        // // }
        // }
        if(j++ % 50 == 0)
        {
            AccurateSonar();
        }
        
        if(j++ % 20 == 0)
        {
            if (getEntitiesInAccurateSonar().size() > 0) {
                MoveTo(getEntitiesInAccurateSonar().get(0));
                if(k++ % 5 == 0)
                {
                    ShootTo(enemy);
                }
                
            } else {
                MoveTo(enemy);
            }
        }
       
        if (dx * dx + dy * dy < 500f * 500f) {
            if (j++ % 5 == 0) {
                ShootTo(enemy);
                enemy2 = new Point((enemy.getX()), (enemy.getY() + 300f));
                MoveTo(enemy2);
    
            }
            
        }

       

    }

}
