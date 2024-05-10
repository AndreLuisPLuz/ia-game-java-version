import java.awt.Color;
import java.util.ArrayList;

public class MoranguinhoPlayer extends Player {
    public MoranguinhoPlayer(Point location) {
        super(location, Color.red, Color.green, "Moranguinho");
    }

    boolean isSearchingEnemies = false;
    boolean isAnalyzingSearch = false;
    boolean isAttacking = false;

    int cyclesPast = 0;
    int fruitsEaten = 0;

    Point enemy = null;
    ArrayList<Point> closeEntities;

    @Override
    protected void loop() {
        if (cyclesPast == 0) {
            AccurateSonar();

            cyclesPast++;
            return;
        } else if (cyclesPast == 1) {
            closeEntities = getEntitiesInAccurateSonar();
            ResetSonar();

            cyclesPast++;
            return;
        }

        if (getEnergy() <= 50) {
            StopMove();
            ResetInfraRed();
            ResetSonar();

            cyclesPast++;
            return;
        }

        isSearchingEnemies = (closeEntities.size() > 0);

        if (isSearchingEnemies && !isAnalyzingSearch) {
            InfraRedSensorTo(closeEntities.get(0));
            isAnalyzingSearch = true;

            cyclesPast++;
            return;
        }

        if (isAnalyzingSearch && !isAttacking) {
            enemy = 
                getEnemiesInInfraRed().size() > 0 ?
                getEnemiesInInfraRed().get(0) : null;
    
            if (enemy == null) {
                isAnalyzingSearch = false;

                cyclesPast++;
                return;
            }

            isAttacking = true;

            cyclesPast++;
            return;
        }

        if (isAttacking) {
            if (getEnergy() >= 50) {
                ShootTo(enemy);
            }

            isAttacking = false;

            cyclesPast++;
            return;
        } else {
            try {
                MoveTo(closeEntities.get(0));
            }
            catch (Exception e) {
                MoveTo(new Point(1080, 920));
            }
        }

        cyclesPast++;
    }
}
