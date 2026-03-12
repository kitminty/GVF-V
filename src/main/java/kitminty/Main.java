package kitminty;


import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.opengl.GL;

import java.awt.*;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

//Isaac was here
public class Main {

    double Zoom = 0;
    double StartPosX = 0;
    double StartPosY = 0;
    double EndPosX = 0;
    double EndPosY = 0;
    double PosX = 0;
    double PosY = 0;
    double CurrentPosX = 0;
    double CurrentPosY = 0;
    boolean WasPressed = false;

    void main() {
        glfwInit();

        glfwDefaultWindowHints(); // Loads GLFW's default window settings
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // Sets window to be visible
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // Sets whether the window is resizable

        long id = glfwCreateWindow(512, 512, "GVF-V", NULL, NULL); // Does the actual window creation

        glfwMakeContextCurrent(id); // glfwSwapInterval needs a context on the calling thread, otherwise will cause NO_CURRENT_CONTEXT error
        GL.createCapabilities(); // Will let lwjgl know we want to use this context as the context to draw with

        glfwSwapInterval(1); // How many draws to swap the buffer
        glfwShowWindow(id); // Shows the window

        while(!glfwWindowShouldClose(id)) {
            RenderPolygon(id);
            DragLogic(id);

            glfwSwapBuffers(id);
            glfwPollEvents();
        }
    }

    public void RenderPolygon(long WindowID) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //resets the frame every loop

        glfwSetScrollCallback(WindowID, new GLFWScrollCallback() { //finds zoom by finding scroll amount
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                Zoom = Zoom + yoffset/2;
            }
        });

        glBegin(GL_LINES);
        glColor3f(0.3f, 1.0f, 1.0f);
        for(double i=0.0; i<Math.TAU; i += 0.01) {
            glVertex2f((float)lemx(i,ZoomCurve(Zoom), PosX),(float)lemy(i,ZoomCurve(Zoom), PosY));
            glVertex2f((float)lemx(i+0.01,ZoomCurve(Zoom), PosX),(float)lemy(i+0.01,ZoomCurve(Zoom), PosY));
        }
        glEnd();
    }

    public void DragLogic(long WindowID) {
        if (glfwGetMouseButton(WindowID, 0) == GLFW_PRESS && !WasPressed) {
            StartPosX = GetCursorPosX(WindowID);
            StartPosY = GetCursorPosY(WindowID);
        }
        WasPressed = glfwGetMouseButton(WindowID, 0) == GLFW_PRESS;

        EndPosX = GetCursorPosX(WindowID);
        EndPosY = GetCursorPosY(WindowID);

        if (glfwGetMouseButton(WindowID, 0) == GLFW_PRESS) {
            PosX = (EndPosX-StartPosX)+CurrentPosX;
            PosY = (EndPosY-StartPosY)+CurrentPosY;
        } else {
            CurrentPosX = PosX;
            CurrentPosY = PosY;
        }
    }

    public double ZoomCurve(double Input) {
        if(Input <= 0) {
            return 1/Math.abs(Input-1);
        } else {
            return Input+1;
        }
    }

    public double GetCursorPosX(long WindowID) {
         DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
         glfwGetCursorPos(WindowID, posX, null);
         return (posX.get()-WindowSizeX(WindowID)/2f)/(WindowSizeX(WindowID)/2f);
    }
    public double GetCursorPosY(long WindowID) {
        DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(WindowID, null, posY);
        return -(posY.get()-WindowSizeY(WindowID)/2f)/(WindowSizeY(WindowID)/2f);
    }

    public int WindowSizeX(long WindowID) {
        IntBuffer posX = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(WindowID, posX, null);
        return posX.get();
    }

    public int WindowSizeY(long WindowID) {
        IntBuffer posY = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(WindowID, null, posY);
        return posY.get();
    }

    public double lemx(double time, double zoom, double xoffset) {
        return zoom*((2*Math.cos(time))/(3-Math.cos(2*time)))+xoffset;
    }

    public double lemy(double time, double zoom, double yoffset) {
        return zoom*((Math.sin(2*time))/(3-Math.cos(2*time)))+yoffset;
    }
}