import java.awt.Color;

public class CamperPlayer extends Player{

    public class EnemyInfo {
        public Point position;
        public Float distance;
    }
    
    public CamperPlayer(Point location)
    {
        super(location, new Color(162, 39, 171, 100), Color.BLACK, "GUARDA_CAIXÃO");
    }

    long frames = 0;
    float angle = 0f;
    int enemyTimer = 0;
    Boolean enemyFound = false;
    Point enemyLocation = null;
    Boolean refindingEnemy = false;

    EnemyInfo enemyInfo;

    float aux = 0, aux2 = 30;

    Boolean enemyFound() {
        return  getEnemiesInInfraRed().size() > 0;
    }

    @Override
    protected void loop() {
        frames++;
        enemyTimer--;

        if (getEnergy() < 20) {
            StopMove();
            return;
        }


        System.out.printf("Energy %3.2f | Life : %3.2f\r", getEnergy(), getLife());

        enemyFound = enemyFound();
        
        if (!enemyFound) {
            InfraRedSensor(angle += 2.2);
            return;
        }
        
        if (null == enemyLocation) {
            enemyTimer = 60;
            enemyLocation = getEnemiesInInfraRed().get(0);
            getEnemiesInInfraRed().clear();
            refindingEnemy =false;
        }

        // MoveTo(enemyLocation);
        if (enemyFound && enemyTimer <= 0 && refindingEnemy == false) {
            refindingEnemy = true;
            aux = 1;
            aux2 = 30;
        }

        if (refindingEnemy) {

            if (aux2 > -30) {
                InfraRedSensor(angle  + aux2--);
            }
            
            if (aux2 <= -30 || enemyFound()){
                refindingEnemy = false;
                enemyFound = false;
                enemyLocation = null;
            }

            return;
        }

        if (frames % 20 == 0) ShootTo(enemyLocation);

        //move


    }
}