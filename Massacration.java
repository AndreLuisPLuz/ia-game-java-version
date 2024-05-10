import java.awt.Color;

public class Massacration extends Player{
    Massacration(Point location) {
        super(location, Color.pink, Color.blue, "Massacration");
    }

    Point enemy = null;
    Point food;

    public Point return_food_point() {
        Point firstfound_point = getEntitiesInAccurateSonar().get(0);
        InfraRedSensorTo(firstfound_point);
        food = 
            getFoodsInInfraRed().size() > 0 ? 
            getFoodsInInfraRed().get(0) : null;

        if(food != null) return food;
        else return null;
    }

    public Point return_enemy_point() {
        Point firstfound_point = getEntitiesInAccurateSonar().get(0);
        InfraRedSensorTo(firstfound_point);
        enemy = 
            getEnemiesInInfraRed().size() > 0 ? 
            getEnemiesInInfraRed().get(0) : null;

        if(enemy != null) return enemy;
        else return null;
    }

    Boolean isloading;
    int i = 0;

    @Override
    protected void loop() {
        enemy = 
            getEnemiesInInfraRed().size() > 0 ?
            getEnemiesInInfraRed().get(0) : null;

        food = 
            getFoodsInInfraRed().size() > 0 ?
            getFoodsInInfraRed().get(0) : null;

        if (getEnergy() < 10)
        {
            StopMove();
            isloading = true;
            enemy = null;
        }

        if (getEnergy() > 60)
            isloading = false;

        if (isloading)
            return;

        if (enemy == null) {
            InfraRedSensor(5f * i++);
            return;
        }

        InfraRedSensorTo(enemy);
        float dx = enemy.getX() - getLocation().getX();
        float dy = enemy.getY() - getLocation().getY();
         
        // int y = 0;
        // float x = getLocation().getX() + y;
        
        // Point point = new Point(x, getLocation().ge)

        if (dx * dx + dy * dy >= 100f * 100f)
            ShootTo(enemy);
    }
}
