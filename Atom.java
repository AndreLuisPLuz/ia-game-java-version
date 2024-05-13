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

        if (i++ % 5 == 0) {
            InfraRedSensorTo(enemy);
            dx = enemy.getX() - getLocation().getX();
            dy = enemy.getY() - getLocation().getY();
            if (dx * dx + dy * dy >= 700f * 700f) {
                AccurateSonar();
                StopTurbo();
                if (getEntitiesInAccurateSonar().isEmpty()) {
                    dx = enemy.getX() + 100f;
                    dy = enemy.getY() + 100f;
                    MoveTo(enemy);
                } else {
                    MoveTo(getEntitiesInAccurateSonar().get(0));
                }

            }

        }

        else if (dx * dx + dy * dy >= 500f * 500f)
        {
            dx = enemy.getX() + 400f;
            dy = enemy.getY() + 400f;
            MoveTo(enemy);
        }
        else {
            // StopMove();
            // if (count_fires >= 10) {
            //     stop_fire++;
            // }

            // if (stop_fire >= 25) {
            //     count_fires = 0;
            //     stop_fire = 0;
            // }

            // if (count_fires <= 5) {
            //     if (i++ % 1 == 0) {
            //         ShootTo(enemy);
            //         count_fires++;

            //     }
            // }
            if (j++ % 5 == 0) {
                    ShootTo(enemy);
                    enemy2 = new Point((enemy.getX()), (enemy.getY() + 300f));
                    MoveTo(enemy2);
                    

                }

        }

    }

}
