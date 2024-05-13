import java.awt.Color;

public class Joelma extends Player {

    public Joelma(Point location) {
        super(location, Color.pink, Color.white, "Joelma");
    }

    int i = 0;
    Point enemy = null;
    boolean isloading = false;
    Point enemy2 = null;

    @Override
    protected void loop() {
        StartTurbo();
        enemy = getEnemiesInInfraRed().size() > 0 ? getEnemiesInInfraRed().get(0) : null;

        if (getEnergy() < 10) {
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

        if (i++ % 3 == 0) {
            InfraRedSensorTo(enemy);
            float dx = enemy.getX() - getLocation().getX();
            float dy = enemy.getY() - getLocation().getY();
            if (dx * dx + dy * dy >= 500f * 500f)
                MoveTo(enemy);
            else {
                if (i++ % 4 == 0) {
                    ShootTo(enemy);
                    enemy2 = new Point((enemy.getX()), (enemy.getY() + 200f));
                    MoveTo(enemy2);
                }
            }
        }
    }
}