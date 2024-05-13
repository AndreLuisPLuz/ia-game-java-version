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

    Point randomEntity = new Point(200, 200);
    Point enemyLocation = null;

    int frames = 0;
    int refindDelay = 20;
    float angle = 0;

    boolean reloading = false;
    boolean enemyFound = false;
    boolean refinding = false;

    // EnemyInfo enemyInfo;

    float distance(Point p1, Point p2) {
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
    @Override
    protected void loop() {
        frames++;
        refindDelay--;

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

        //Move to food
        if (frames % 20 == 0) {
            AccurateSonar();
            getEntitiesInAccurateSonar().clear();
        }

        if (getEntitiesInAccurateSonar().size() > 0) {
            //Get closest unit
            Point p = getEntitiesInAccurateSonar().get(frames % getEntitiesInAccurateSonar().size());

            randomEntity = p;
            getEntitiesInAccurateSonar().clear();
        }

        if (!enemyFound) 
            MoveTo(randomEntity);
        else 
            MoveTo(enemyLocation);

        if (refinding && !enemyFound) {
            InfraRedSensor(angle += 4);

            if (angle > 360) {
                angle = 0;
                refinding = false;
                refindDelay = 300;
            }
        }

        //getClosest enemy

        if (enemyFound && frames % 20 == 0) {
            InfraRedSensor(enemyLocation);
            enemyFound = false;
        }

        if (getEnemiesInInfraRed().size() > 0 && enemyFound) {
            Point e = getEnemiesInInfraRed().get(0);

            for (Point i : getEnemiesInInfraRed()) {
                if (distance(getLocation(), e) > distance(getLocation(), i)) {
                    e = i;
                }
            }

            if (distance(getLocation(), e) < 400) {
                enemyLocation = e;

                enemyFound = true;
            }
            getEnemiesInInfraRed().clear();
            
            
        }

        if (enemyFound)
        if (distance(getLocation(), enemyLocation)< 300 && frames % 20 == 0) {
            ShootTo(enemyLocation);
        }
    }
}