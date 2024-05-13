import java.awt.Color;

public class Joelma extends Player {

    public Joelma(Point location) {
        super(location, Color.pink, Color.white, "Joelma");
    }

    int i = 0;
    Point enemy = null;
    boolean isloading = false;
    Point obj = null;
    Double theta = null;

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
            if (dx * dx + dy * dy >= 300f * 300f)
                MoveTo(enemy);
            else {
                if (theta == null)
                    theta = Math.atan2(dy, dx);
                System.out.println(theta);
                
                double novoX = enemy.getX() + 300f * Math.cos(theta + Math.PI);
                double novoY = enemy.getY() + 300f * Math.sin(theta + Math.PI);
                obj = new Point((float)novoX, (float)novoY);

                dx = obj.getX() - getLocation().getX();
                dy = obj.getY() - getLocation().getY();
                System.out.println(dx * dx + dy * dy);

                if (dx * dx + dy * dy < 15 * 15)
                    theta += 0.05;

                MoveTo(obj);
                if (i++ % 4 == 0) {
                    ShootTo(enemy);
                }
            }
        }
    }
}