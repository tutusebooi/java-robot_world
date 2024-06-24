package za.co.wethinkcode.robotworlds.robot;

public class Shield {
    private int nrOfShield;
    private boolean shieldOn;

    public Shield() {
        this.nrOfShield = 3;
        this.shieldOn = true;
    }

    public int getNrOfShield() {
        return nrOfShield;
    }

    public boolean isShieldOn() {
        return shieldOn;
    }

    public void checkIfShieldOn() {
        if (nrOfShield <= 0) {
            setShieldOn(false);
        }
    }

    public void removeShieldAfterHit() {
        nrOfShield --;
    }

    public void setNrOfShield(int nrOfShield) {
        this.nrOfShield = nrOfShield;
    }

    private void setShieldOn(boolean shieldOn) {
        this.shieldOn = shieldOn;
    }

    @Override
    public String toString() {
        return "Shield { " +
                "nrOfShield = " + nrOfShield +
                ", shieldOn = " + shieldOn +
                '}';
    }
}
