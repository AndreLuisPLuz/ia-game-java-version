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

    @Override
    protected void loop() {
        if(getEntitiesInAccurateSonar().size() == 0) AccurateSonar();
        else {
            if(getEnergy() > 30) {
                // if(return_enemy_point() != null) {
                //     Float newX = -(return_enemy_point().getX());
                //     Float newY = -(return_enemy_point().getY());
                //     Point oposite_direction = new Point(newX, newY);

                //     MoveTo(oposite_direction);
                // }
                if(return_food_point() != null) {
                    while(getLocation() != return_food_point()) {
                        MoveTo(return_food_point());
                    }
                }
            }
        }
    }
}
