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

    Point randomEntity = null;
    Point enemyLocation = null;

    int frames = 0;
    int refindDelay = 20;
    float angle = 0;

    boolean reloading = false;
    boolean enemyFound = false;
    boolean refinding = false;

    // EnemyInfo enemyInfo;

    float distance(Point p1, Point p2) {
        if (p1 == null || p2 == null)
            return 1000 * 1000f;

        // Calcula a diferença entre as coordenadas x e y dos dois pontos
        double deltaX = p2.getX() - p1.getX();
        double deltaY = p2.getY() - p1.getY();
        
        // Calcula o quadrado da diferença em x e y
        double squaredDistance = deltaX * deltaX + deltaY * deltaY;
        
        // Calcula a raiz quadrada do quadrado da distância
        Double distance = Math.sqrt(squaredDistance);
        
        return distance.floatValue();
    }

    int getValue(float distance) {
        if (distance >= 400) {
            return 20;
        } else if (distance <= 0) {
            return 0;
        } else {
            // Interpolate the value between 0 and 400
            return (int)(20 * (distance / 400));
        }
    }

    int shoot_delay = 10;
  
    void genMovePoint(float dist) {
        float dx = enemyLocation.getX() - getLocation().getX();
        float dy = enemyLocation.getY() - getLocation().getY();
        float distance = (float)Math.sqrt(dx * dx + dy * dy);
    
        float vx = dx / distance;
        float vy = dy / distance;
    
        randomEntity = new Point(enemyLocation.getX() + dist * vx, enemyLocation.getY() + dist * vy);
    }

    int refind_delay = 300;

    int shoot_index = 0;

    boolean recenter = false;


    @Override
    protected void loop() {
        frames++;
        refindDelay--;

        if (getLocation().getX() >= 1920 || getLocation().getY() >= 1080) {
            recenter = true;
        }


        float dist = -1;

        if (refindDelay < 0) {
            refinding = true;

        }

        if (reloading) {
            if (getEnergy() > 50) {
                reloading = false;
            }

            return;
        }

        if (getEnergy() < 20) {
            reloading = true;
        }

        if (getEnergy() < 10) {
            return;
        }

        //Move to food
        if (frames % 10 == 0) {
            getEntitiesInAccurateSonar().clear();
            AccurateSonar();
        }

        if (randomEntity == null && getEntitiesInAccurateSonar().size() > 0) {
            //Get closest unit
            Point p = getEntitiesInAccurateSonar().get(frames % getEntitiesInAccurateSonar().size());

            randomEntity = p;
            getEntitiesInAccurateSonar().clear();
        }

        if (randomEntity != null) {
            MoveTo(randomEntity);
        }

        if (randomEntity == null && getEntitiesInAccurateSonar().size() == 0) {
            if (enemyLocation != null) {
                MoveTo(enemyLocation);
            }
            
        }

        if (randomEntity != null)
        if (distance(getLocation(), randomEntity) < 5) {
            randomEntity = null;
        }

        if (!enemyFound) {
            InfraRedSensor(angle += 6);
        }
        //getClosest enemy
        if (enemyLocation != null) {
            float distance_enemy = distance(getLocation(), enemyLocation);
            if (distance_enemy < 500) {
                StartTurbo();
            } else {
                StopTurbo();
            }

            shoot_delay = distance_enemy < 350 ? 5 : 15;
        }

        if (enemyFound && frames % 5 == 0) {
            InfraRedSensorTo(enemyLocation);
            enemyFound = false;
            
            if (getEnemiesInInfraRed().size() > 0) {
                enemyFound = true;
                enemyLocation = getEnemiesInInfraRed().get(0);
                getEnemiesInInfraRed().clear();
            }
        }

        if (getEnemiesInInfraRed().size() > 0 && !enemyFound) {
            enemyLocation = getEnemiesInInfraRed().get(0);
            enemyFound = true;

            // getEnemiesInInfraRed().clear();
        }

        if (enemyFound)
        if (distance(getLocation(), enemyLocation)< 700 && frames % shoot_delay == 0) {
            ShootTo(enemyLocation);
        }

        if (distance(getLocation(), enemyLocation) > 850) {
            enemyFound = false;
            enemyLocation = null;
            getEnemiesInInfraRed();
        }
    }
}