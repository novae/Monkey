package simulacionchango;
import org.newdawn.slick.geom.*;
public abstract class Entidad {
    private float posicionX,posicionY;
    protected Rectangle bordeColision;
    
    public float getPosicionX() {
        return posicionX;
    }

    public float getPosicionY() {
        return posicionY;
    }
    public void setPosicionX(float posicionX) {
        this.posicionX = posicionX;
    }
    public void setPosicionY(float posicionY) {
        this.posicionY = posicionY;
    }    
}
