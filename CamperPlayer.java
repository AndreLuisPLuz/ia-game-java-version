import java.awt.Color;
import java.io.ObjectInputStream.GetField;
import java.util.Random;

import javafx.beans.property.ReadOnlyLongProperty;

public class CamperPlayer extends Player{

    public class EnemyInfo {
        public Point position;
        public Float distance;
    }
    
    public CamperPlayer(Point location)
    {
        super(location, new Color(162, 39, 171, 100), Color.BLACK, "GUARDA_CAIXÃO");
    }

    Point randomPoint = null;

    long frames = 0;
    float angle = 0f;
    int enemyTimer = 0;
    Boolean enemyFound = false;
    Point enemyLocation = null;
    Float enemyDistance = null;
    Boolean refindingEnemy = false;

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

    

    float aux = 0, aux2 = 30;

    Boolean enemyFound() {
        return  getEnemiesInInfraRed().size() > 0;
    }

    @Override
    protected void loop() {
        frames++;
        enemyTimer--;

        if (randomPoint == null) {
            Random rd = new Random();

            randomPoint = new Point(rd.nextFloat() * 1400, rd.nextFloat() * 900);
        }

        if (distanceBetweenPoints(getLocation(), randomPoint) < 20) {
            randomPoint = null;
            return;
        }

        if (getEnergy() < 20) {
            StopMove();
            return;
        }


        System.out.printf("Energy %3.2f | Life : %3.2f Location X: %3.2f Location Y: %3.2f\r", getEnergy(), getLife(), getLocation().getX(), getLocation().getY());

        enemyFound = enemyFound();
        
        if (!enemyFound) {
            InfraRedSensor(angle += 2.2);
            return;
        }
        
        if (null == enemyLocation) {
            enemyTimer = 60;
            enemyLocation = getEnemiesInInfraRed().get(0);
            enemyDistance = distanceBetweenPoints(enemyLocation, getLocation());
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
                aux2 = 30;
            }

            return;
        }

        if (frames % getValue(enemyDistance) == 0) ShootTo(enemyLocation);

        //move
        
        if (frames % 10 == 0) {
            MoveTo(randomPoint);
        }

    }
}