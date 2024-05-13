import java.awt.Color;

public class DriftKing extends Player {
    public DriftKing(Point location)
    {
        super(location, Color.cyan, Color.white, "Drift King");
    }

    int frame = 0;
    int i = 0;
    Point enemy = null;
    Point fruit = new Point(0, 0);
    int points = 0;
    
    @Override
    protected void loop()
    {
        frame++;    
        i++;
        StartTurbo();

        if(getEnergy() < 20) {
            StopMove();
            return;
        }
        
        if(enemy != null && frame % 10 == 0)
            ShootTo(enemy);
        if(fruit != null)
            MoveTo(fruit);

        if(frame % 200 == 0)
            enemy = null;
        
        if(frame % 4 == 0)
            InfraRedSensor(5f * i);
        
        
        if(getFoodsInInfraRed().size() > 0) {
            if(fruit == null) {
                fruit = getFoodsInInfraRed().get(0);
                return;
            }
            float newFruitX = getFoodsInInfraRed().get(0).getX() - getLocation().getX();
            float newFruitY = getFoodsInInfraRed().get(0).getY() - getLocation().getY();
            float fruitX = fruit.getX() - getLocation().getX();
            float fruitY = fruit.getY() - getLocation().getY();

            if (newFruitX * newFruitX + newFruitY * newFruitY < fruitX * fruitX + fruitY * fruitY)
                fruit = getFoodsInInfraRed().get(0);
        }

        if(getEnemiesInInfraRed().size() > 0) {
            if(enemy == null) {
                enemy = getEnemiesInInfraRed().get(0);
                return;
            }
            float newEnemyX = getEnemiesInInfraRed().get(0).getX() - getLocation().getX();
            float newEnemyY = getEnemiesInInfraRed().get(0).getY() - getLocation().getY();
            float enemyX = enemy.getX() - getLocation().getX();
            float enemyY = enemy.getY() - getLocation().getY();

            if (newEnemyX * newEnemyX + newEnemyY * newEnemyY < enemyX * enemyX + enemyY * enemyY)
                enemy = getEnemiesInInfraRed().get(0);
        }
        
        if (getPoints() != points)
        {
            fruit = null;
            ResetInfraRed();
            points = getPoints();
        }
    }
}

