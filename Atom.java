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
            enemy = null;
            StopTurbo();

        }

        if (getEnergy() > 60)
            isloading = false;

        if (isloading)
            return;

        InfraRedSensorTo(enemy);
        float dx = enemy.getX() - getLocation().getX();
        float dy = enemy.getY() - getLocation().getY();
        if (dx * dx + dy * dy >= 600f * 600f)
        {
            AccurateSonar();
            if (getEntitiesInAccurateSonar().isEmpty()) {

            } else {
                MoveTo(getEntitiesInAccurateSonar().get(0));
            }

        }
        else if (dx * dx + dy * dy >= 300f * 300f) 
            MoveTo(enemy);
        else {
            StopMove();
            if (i++ % 1 == 0){
                ShootTo(enemy);
            }

          
        }

    }

}
