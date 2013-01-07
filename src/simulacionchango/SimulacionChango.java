package simulacionchango;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class SimulacionChango extends BasicGame {

    static int height = 640;
    static int width = 800;
    static boolean fullscreen = false;
    static boolean showFPS = true;
    static String title = "Simulacion Chango";
    static int fpslimit = 60;
    Input entradaTeclado;
    private Animation Animacion;
    private Mono mono;
    private Silla silla;
    private Banana bananas;
    private boolean[][] bloqueados;
    private TiledMap Fondo;
    private int aparicion;
    private boolean EncuentraBanana = false, EncuentraSilla = false;
    private Image FondoTermina;
    private int TipoBusqueda;

    public SimulacionChango(String title) {
        super(title);
        aparicion = 0;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        try {
            Fondo = new TiledMap("imagenes/Fondo/Background.tmx");
            FondoTermina = new Image("imagenes/FondoTermina.png");
            mono = new Mono();
            silla = new Silla();
            bananas = new Banana();
            bananas.setPosicionX(540);
            bananas.setPosicionY(380);
            Animacion = mono.getMonoAbajo();
            TipoBusqueda = 0;
            bloqueados = new boolean[Fondo.getWidth()][Fondo.getHeight()];
            mapeoBloqueados();
        } catch (SlickException ex) {
            Logger.getLogger(SimulacionChango.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
        if (aparicion >= 2) {
            generaMovimiento(delta);
        }
        bananas.bordeColision.setX(bananas.getPosicionX());
        bananas.bordeColision.setY(bananas.getPosicionY());
        if (mono.bordeColision.intersects(silla.bordeColision) && mono.bordeColision.getX() != 0 && silla.bordeColision.getX() != 0) {
            EncuentraSilla = true;
            TipoBusqueda = 1;
            silla.setPosicionX(mono.getPosicionX());
            silla.setPosicionY(mono.getPosicionY() - 15);
        }
        if (mono.bordeColision.intersects(bananas.bordeColision) && EncuentraSilla) {
            EncuentraBanana = true;
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        if (!EncuentraBanana) {
            Fondo.render(0, 0);
            switch (aparicion) {
                case 0:
                    g.drawString("PRESIONA CLICK IZQUIERDO PARA AGREGAR AL MONO", 18, 40);
                    if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                        mono.setPosicionY(gc.getInput().getMouseY());
                        mono.setPosicionX(gc.getInput().getMouseX());
                        Animacion.draw(mono.getPosicionX(), mono.getPosicionY());
                        aparicion++;
                    }
                    break;
                case 1:
                    g.drawString("PRESIONA CLICK IZQUIERDO PARA AGREGAR LA SILLA", 18, 40);
                    if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
                        silla.setPosicionY(gc.getInput().getMouseY());
                        silla.setPosicionX(gc.getInput().getMouseX());
                        silla.getImagenSilla().draw(gc.getInput().getMouseY(), gc.getInput().getMouseY());
                        aparicion++;
                    }
                    Animacion.draw(mono.getPosicionX(), mono.getPosicionY());
                    break;
                default:
                    Animacion.draw(mono.getPosicionX(), mono.getPosicionY());
                    silla.getImagenSilla().draw(silla.getPosicionX(), silla.getPosicionY());
                    break;
            }
            bananas.getImagenBananas().draw(bananas.getPosicionX(), bananas.getPosicionY());
        } else if (EncuentraSilla) {
            FondoTermina.draw(0, 0);
        }
    }

    private void mapeoBloqueados() {
        for (int ejeX = 0; ejeX < Fondo.getWidth(); ejeX++) {
            for (int ejeY = 0; ejeY < Fondo.getHeight(); ejeY++) {
                int tileID = Fondo.getTileId(ejeX, ejeY, 1);
                String valor = Fondo.getTileProperty(tileID, "blocked", "false");
                if ("true".equals(valor)) {
                    bloqueados[ejeX][ejeY] = true;
                } else {
                    bloqueados[ejeX][ejeY] = false;
                }
            }
        }
    }

    private boolean estaBloqueado(float x, float y) {
        int xBloque = (int) x / 32;
        int yBloque = (int) y / 32;
        return bloqueados[xBloque][yBloque];
    }

    private void generaMovimiento(int delta) {
       
        float aux;

//        System.out.println("silla:"+silla.getPosicionX());
//        System.out.println("mono"+mono.getPosicionX());
        if (TipoBusqueda == 0) {//Busca la Silla
            if (silla.getPosicionX() > mono.getPosicionX()) {
                if (!(estaBloqueado(mono.getPosicionX() + 32 + 1, mono.getPosicionY()) || estaBloqueado(mono.getPosicionX() + 32 + 1, mono.getPosicionY() + 32 - 1))) {
                    Animacion = mono.getMonoDerecha();
                    Animacion.update(delta);
                    aux = mono.getPosicionX() + 1;
                    mono.setPosicionX(aux);
                } else {
                    Animacion = mono.getMonoArriba();
                    Animacion.update(delta);
                    aux = mono.getPosicionY() - 1;
                    mono.setPosicionY(aux);
                }
            } else if (silla.getPosicionX() <mono.getPosicionX()) {
                if (!(estaBloqueado(mono.getPosicionX() - 1, mono.getPosicionY()) || estaBloqueado(mono.getPosicionX() - 1, mono.getPosicionY() + 32 - 1))) {
                    Animacion = mono.getMonoIzquierda();
                    Animacion.update(delta);
                    aux = mono.getPosicionX() - 1;
                    mono.setPosicionX(aux);
                } else {
                    Animacion = mono.getMonoArriba();
                    Animacion.update(delta);
                    aux = mono.getPosicionY() - 1;
                    mono.setPosicionY(aux);
                }
            } else if (Math.round(silla.getPosicionX()) == Math.round(mono.getPosicionX())) {
                if (silla.getPosicionY() < mono.getPosicionY()) {
                    if (!(estaBloqueado(mono.getPosicionX(), mono.getPosicionY() - 1) || estaBloqueado(mono.getPosicionX() + 32 - 1, mono.getPosicionY() - 1))) {

                        Animacion = mono.getMonoArriba();
                        Animacion.update(delta);
                        aux = mono.getPosicionY() -1;
                        mono.setPosicionY(aux);
                    } else {
                        Animacion = mono.getMonoIzquierda();
                        Animacion.update(delta);
                        aux = mono.getPosicionX() - 1;
                        mono.setPosicionX(aux);
                    }
                } else if (silla.getPosicionY() > mono.getPosicionY()) {
                    if (!(estaBloqueado(mono.getPosicionX(), mono.getPosicionY() + 32 + 1) || estaBloqueado(mono.getPosicionX() + 32 - 1, mono.getPosicionY() + 32 + 1))) {

                        Animacion = mono.getMonoAbajo();
                        Animacion.update(delta);
                        aux = mono.getPosicionY() + 1;
                        mono.setPosicionY(aux);
                    } else {
                        Animacion = mono.getMonoDerecha();
                        Animacion.update(delta);
                        aux = mono.getPosicionX() + 1;
                        mono.setPosicionX(aux);
                    }
                }
            }
        } else {//Ya tiene la silla y va a por las bananas Tio!!!

            if (bananas.getPosicionX() > mono.getPosicionX()) {
                if (!(estaBloqueado(mono.getPosicionX() + 32 + 1, mono.getPosicionY()) || estaBloqueado(mono.getPosicionX() + 32 + 1, mono.getPosicionY() + 32 - 1))) {
                    Animacion = mono.getMonoDerecha();
                    Animacion.update(delta);
                    aux = mono.getPosicionX() + 1;
                    mono.setPosicionX(aux);
                } else {
                    Animacion = mono.getMonoArriba();
                    Animacion.update(delta);
                    aux = mono.getPosicionY() - 1;
                    mono.setPosicionY(aux);
                }
            } else if (bananas.getPosicionX() < mono.getPosicionX()) {
                if (!(estaBloqueado(mono.getPosicionX() - 1, mono.getPosicionY()) || estaBloqueado(mono.getPosicionX() - 1, mono.getPosicionY() + 32 - 1))) {
                    Animacion = mono.getMonoIzquierda();
                    Animacion.update(delta);
                    aux = mono.getPosicionX() - 1;
                    mono.setPosicionX(aux);
                } else {
                    Animacion = mono.getMonoArriba();
                    Animacion.update(delta);
                    aux = mono.getPosicionY() - 1;
                    mono.setPosicionY(aux);
                }
            } else if (Math.round(bananas.getPosicionX()) == Math.round(mono.getPosicionX())) {
                if (bananas.getPosicionY() < mono.getPosicionY()) {
                    if (!(estaBloqueado(mono.getPosicionX(), mono.getPosicionY() - 1) || estaBloqueado(mono.getPosicionX() + 32 - 1, mono.getPosicionY() - 1))) {

                        Animacion = mono.getMonoArriba();
                        Animacion.update(delta);
                        aux = mono.getPosicionY() - 1;
                        mono.setPosicionY(aux);
                    } else {
                        Animacion = mono.getMonoIzquierda();
                        Animacion.update(delta);
                        aux = mono.getPosicionX() - 1;
                        mono.setPosicionX(aux);
                    }
                } else if (bananas.getPosicionY() > mono.getPosicionY()) {
                    if (!(estaBloqueado(mono.getPosicionX(), mono.getPosicionY() + 32 + 1) || estaBloqueado(mono.getPosicionX() + 32 - 1, mono.getPosicionY() + 32 + 1))) {

                        Animacion = mono.getMonoAbajo();
                        Animacion.update(delta);
                        aux = mono.getPosicionY() + 1;
                        mono.setPosicionY(aux);
                    } else {
                        Animacion = mono.getMonoDerecha();
                        Animacion.update(delta);
                        aux = mono.getPosicionX() + 1;
                        mono.setPosicionX(aux);
                    }
                }
            }
        }
        mono.bordeColision.setX(mono.getPosicionX() + 7);
        mono.bordeColision.setY(mono.getPosicionY() + 7);
        silla.bordeColision.setX(silla.getPosicionX() + 7);
        silla.bordeColision.setY(silla.getPosicionY() + 7);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new SimulacionChango(title));
        app.setDisplayMode(width, height, fullscreen);
        app.setSmoothDeltas(true);
        app.setTargetFrameRate(fpslimit);
        app.setShowFPS(true);
        app.start();
    }
}