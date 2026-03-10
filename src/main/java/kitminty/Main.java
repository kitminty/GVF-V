package kitminty;


import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.awt.*;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

//Isaac was here
public class Main {
     static void main() {
        glfwInit();

        String title = "GVF-V"; // The title of the window, WARNING, if title is
        // null, the code will segfault at glfwCreateWindow()
        boolean resizable = true; // Whether or not the window is resizable

        int m_width = 512; // width of the window
        int m_height = 512; // height of the window

        glfwDefaultWindowHints(); // Loads GLFW's default window settings
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // Sets window to be visible
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE); // Sets whether the window is resizable

        long id = glfwCreateWindow(m_width, m_height, title, NULL, NULL); // Does the actual window creation
        if ( id == NULL ) throw new RuntimeException("Failed to create window");

        glfwMakeContextCurrent(id); // glfwSwapInterval needs a context on the calling thread, otherwise will cause NO_CURRENT_CONTEXT error
        GL.createCapabilities(); // Will let lwjgl know we want to use this context as the context to draw with

        glfwSwapInterval(1); // How many draws to swap the buffer
        glfwShowWindow(id); // Shows the window


        while(!glfwWindowShouldClose(id)) {
            renderPolygon(id);

            glfwSwapBuffers(id);
            glfwPollEvents();
        }
    }

    static void renderPolygon(long WindowID) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        /*
        (GetCursorPosX(WindowID)-WindowSizeX(WindowID)/2f)/(WindowSizeX(WindowID)/2f) //for getting the x value of mouse pos
        -(GetCursorPosY(WindowID)-WindowSizeY(WindowID)/2f)/(WindowSizeY(WindowID)/2f) //for getting the y value of mouse pos

        glLineWidth(10.0f); //for GL_LINE_STRIP
        glPointSize(10.0f); //for GL_POINTS

        glBegin(GL_LINE_STRIP);
        glColor3f(1.0f, 0.3f, 0.3f);
        glVertex2f(0.0f, 0.0f);
        glVertex2f((float)lemx(Time), (float)lemy(Time));
        glEnd();

        glBegin(GL_POINTS);
        glColor3f(0.3f, 1.0f, 1.0f);
        glVertex2f((float)lemx(Time), (float)lemy(Time));
        glEnd();

        glBegin(GL_POLYGON);
        glColor3f(0.3f, 1.0f, 1.0f);
        glVertex2f(0.5f,0.5f);
        glVertex2f(-0.5f,0.5f);
        glVertex2f(-0.5f,-0.5f);
        glVertex2f(0.5f,-0.5f);
        glEnd();

        glBegin(GL_LINES);
        glColor3f(0.3f, 1.0f, 1.0f);
        for(double i=0.0; i<Math.TAU; i += 0.01) {
            glVertex2f((float)lemx(i),(float)lemy(i));
            glVertex2f((float)lemx(i+0.01),(float)lemy(i+0.01));
        }
        glEnd();

        glBegin(GL_LINES);
        glColor3f(0.3f, 1.0f, 1.0f);
        for(double i=0.0; i<Math.TAU; i += 0.01) {
            glVertex2f((float)(0.8*Math.cos(i)),(float)(0.8*Math.sin(i)));
            glVertex2f((float)(0.8*Math.cos(i+0.01)),(float)(0.8*Math.sin(i+0.01)));
        }
        glEnd();
        */

        glPointSize(10.0f);
        glBegin(GL_POINTS);
        glColor3f(1.0f, 0.3f, 0.3f);
        glVertex2f((float) (GetCursorPosX(WindowID)-WindowSizeX(WindowID)/2f)/(WindowSizeX(WindowID)/2f), (float) -(GetCursorPosY(WindowID)-WindowSizeY(WindowID)/2f)/(WindowSizeY(WindowID)/2f));
        glEnd();
    }

    public static double GetCursorPosX(long WindowID) {
         DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
         glfwGetCursorPos(WindowID, posX, null);
         return posX.get();
    }
    public static double GetCursorPosY(long WindowID) {
        DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(WindowID, null, posY);
        return posY.get();
    }

    public static int WindowSizeX(long WindowID) {
        IntBuffer posX = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(WindowID, posX, null);
        return posX.get();
    }

    public static int WindowSizeY(long WindowID) {
        IntBuffer posY = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(WindowID, null, posY);
        return posY.get();
    }

    public static double lemx(double t) {
        return 0.8*((2*Math.cos(t))/(3-Math.cos(2*t)));
    }

    public static double lemy(double t) {
        return 0.8*((Math.sin(2*t))/(3-Math.cos(2*t)));
    }
}