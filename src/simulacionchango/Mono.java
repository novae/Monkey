package simulacionchango;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Mono extends Entidad {

    public Animation MonoDerecha, MonoIzquierda, MonoArriba, MonoAbajo;
    private SpriteSheet SpriteMono;

    public Mono() {
        try {
            SpriteMono = new SpriteSheet("imagenes/Mono.png", 40, 40);
            MonoDerecha = new Animation(SpriteMono, 0, 0, 15, 0, true, 100, false);
            MonoIzquierda = new Animation(SpriteMono, 0, 1, 15, 1, true, 100, false);
            MonoAbajo = new Animation(SpriteMono, 0, 2, 7, 2, true, 100, false);
            MonoArriba = new Animation(SpriteMono, 0, 3, 7, 3, true, 100, false);
            bordeColision = new Rectangle(this.getPosicionX(), this.getPosicionY(), 26, 26);

        } catch (SlickException ex) {
            Logger.getLogger(Mono.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Animation getMonoDerecha() {
        return MonoDerecha;
    }
    public Animation getMonoIzquierda() {
        return MonoIzquierda;
    }
    public Animation getMonoArriba() {
        return MonoArriba;
    }
    public Animation getMonoAbajo() {
        return MonoAbajo;
    }
}
