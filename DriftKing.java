import java.awt.Color;

public class DriftKing extends Player {
    public DriftKing(Point location)
    {
        super(location, Color.cyan, Color.white, "Drift King");
    }

    int i = 0;
    Point enemy = null;
    Point fruit = null;
    boolean isloading = false;
    int shootError = 20;
    int searchindex = 0;
    int frame = 0;
    int points = 0;

    Point target = null;
    boolean laser = false;

    // @Override
    // protected void newLoop() {
    //     StartTurbo();

    //     isloading = getEnergy() < 10;
    //     if(isloading == true) {
    //         StopMove();
    //         target = null;
    //         return;
    //     }
        
    //     if(getEntitiesInAccurateSonar().size() == 0) {
    //         AccurateSonar();
    //         laser = getEntitiesInAccurateSonar().size() == 0;
    //         return;
    //     }

    //     if(laser) {
    //         if(target == null) {
    //             InfraRedSensor(5f * i++);
    //             return;
    //         }
    //     } 
        
    //     if (getEnemiesInInfraRed().size() == 0) {
    //         InfraRedSensorTo(getEntitiesInAccurateSonar().get(searchindex++ % getEntitiesInAccurateSonar().size()));
    //         return;
    //     }
    // }

    @Override
    protected void loop()
    {
        StartTurbo();
        enemy = 
            getEnemiesInInfraRed().size() > 0 ?
            getEnemiesInInfraRed().get(0) : null;
        
        if(getEnergy() < 10)
        {
            StopMove();
            isloading = true;
            enemy = null;
        }

        if(getEnergy() > 30)
            isloading = false;

        if(isloading) {
            frame++;
            if (getEnergy() < 10 || frame % 20 == 0)
                return;
            
            if (getEntitiesInStrongSonar() == 0)
            {
                StrongSonar();
                points = getPoints();
                return;
            }

            if (getEntitiesInAccurateSonar().size() == 0)
            {
                AccurateSonar();
                return;
            }

            if (getFoodsInInfraRed().size() == 0)
            {
                InfraRedSensorTo(getEntitiesInAccurateSonar().get(searchindex++ % getEntitiesInAccurateSonar().size()));
                return;
            }
            
            MoveTo(getFoodsInInfraRed().get(0));
            if (getPoints() != points)
            {
                StartTurbo();
                StrongSonar();
                StopMove();
                ResetInfraRed();
                ResetSonar();
            }
        }

        // if(enemy == null && getFoodsInInfraRed().size() > 0) {
        //     MoveTo(getFoodsInInfraRed().get(0));
        // }

        if(enemy == null) {
            InfraRedSensor(5f * i++);
            return;
        }
        
        InfraRedSensorTo(enemy);
        float dx = enemy.getX() - getLocation().getX();
        float dy = enemy.getY() - getLocation().getY();
        if(dx * dx + dy * dy >= 300f * 300f)
            MoveTo(enemy);
        else
        {
            StopMove();
            if (i++ % 5 == 0)
                ShootTo(new Point(enemy.getX() + (shootError++ % 50) - 25, enemy.getY() + (shootError++ % 50) - 25));
        }

        
    }
}
