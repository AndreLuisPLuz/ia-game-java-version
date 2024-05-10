import java.awt.Color;

public class Atom extends Player {

    public Atom(Point location) {
        super(location, Color.black, Color.green, "Atom");
    }

    int searchindex = 0;
    int frame = 0;
    int points = 0;
    Point enemy = null;
    boolean isloading = false;
    int i = 0;
    int count_fires=0;
    int stop_fire =0;

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

        if (isloading)
            return;

        InfraRedSensorTo(enemy);
        float dx = enemy.getX() - getLocation().getX();
        float dy = enemy.getY() - getLocation().getY();
        if (dx * dx + dy * dy >= 500f * 500f)
        {
            AccurateSonar();
            StopTurbo();
            if (getEntitiesInAccurateSonar().isEmpty()) {

            } else {
                MoveTo(getEntitiesInAccurateSonar().get(0));
            }

        }
        else if (dx * dx + dy * dy >= 300f * 300f) 
            MoveTo(enemy);
        else {
            StopMove();
            if (count_fires >= 10) {
                stop_fire++;    
            }

            if(stop_fire>=25){
                count_fires =0;
                stop_fire =0;
            }

            if (count_fires <=10) {
                if (i++ % 1 == 0){
                    ShootTo(enemy);
                    count_fires++;
                    
                }
            }

            

          
        }

    }

}
