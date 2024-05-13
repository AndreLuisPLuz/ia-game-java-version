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

    Point movePoint = new Point(200, 200);
    Point enemyLocation = null;

    int frames = 0;
    int refindDelay = 100;
    float angle = 0;

    boolean enemyFound = false;
    boolean refinding = false;

    // EnemyInfo enemyInfo;

    float distanceBetweenPoints(Point p1, Point p2) {
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
        dx = (float)Math.sqrt(dx * dx);

        float dy = enemyLocation.getY() - getLocation().getY();
        dy = (float)Math.sqrt(dy*dy);

        float vx = dx / (float)Math.sqrt(dx*dx + dy*dy);
        float vy = dy / (float)Math.sqrt(dx*dx + dy*dy);

        movePoint = new Point(enemyLocation.getX() + dist * vy, enemyLocation.getY() - dist * vx);
    }


    @Override
    protected void loop() {
        frames++;
        float dist = -1;

        if (getEnergy() < 20) {
            return;
        }

        
        
        if (!enemyFound && !refinding) {
            InfraRedSensor(angle *= 1.1);
        }

        if (enemyLocation != null) {
            dist = distanceBetweenPoints(getLocation(), enemyLocation);

            if (dist > 100) {
                StopTurbo();
            } else {
                StartTurbo();
            }
            genMovePoint(30);
            StartMove(movePoint);
        }

        if (getEnemiesInInfraRed().size() > 0) {
            enemyLocation = getEnemiesInInfraRed().get(0);
            enemyFound = true;
        }

        
        

    }
}