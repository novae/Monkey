
package simulacionchango;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Silla extends Entidad{
    private Image imagenSilla;

    public Silla() throws SlickException {
        imagenSilla = new Image("imagenes/silla.png");
        bordeColision = new Rectangle(this.getPosicionX(), this.getPosicionY(),26, 26);
    }
    public Image getImagenSilla() {
        return imagenSilla;
    }
    
    
}
