package kitminty;

import static kitminty.Main.MousePosX;
import static kitminty.Main.MousePosY;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.opengl.GL11.*;

public class PointInstance {
    double PointPosX = 0;
    double PointPosY = 0;
    boolean PointIsClicked = false;
    boolean WasClicked = false;
    boolean PointShouldMove = false;
    public void PointLogic(long WindowID, double zoom, double xoffset, double yoffset) {
        glPointSize(10.0f);
        glBegin(GL_POINTS);
        glColor3f(1.0f, 0.3f, 0.3f);
        glVertex2d((PointPosX*zoom+xoffset), (PointPosY*zoom+yoffset));
        glEnd();

        PointIsClicked = glfwGetMouseButton(WindowID, 0) == GLFW_PRESS && Math.hypot(((MousePosX - PointPosX*zoom) - xoffset), ((MousePosY - PointPosY*zoom) - yoffset)) < 0.02/zoom;
        if (PointIsClicked && !WasClicked) {
            Main.ScreenShouldMove = !Main.ScreenShouldMove;
            PointShouldMove = !Main.ScreenShouldMove;
        }
        WasClicked = PointIsClicked;

        if (PointShouldMove) {
            PointPosX = (MousePosX - xoffset)/zoom;
            PointPosY = (MousePosY - yoffset)/zoom;
        }
    }
}
