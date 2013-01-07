package simulacionchango;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Banana extends Entidad {

    private Image ImagenBananas;

    public Banana() throws SlickException {
        ImagenBananas = new Image("imagenes/bananas.png");
        bordeColision = new Rectangle(this.getPosicionX(), this.getPosicionY(), 32, 32);
    }

    public Image getImagenBananas() {
        return ImagenBananas;
    }
}
