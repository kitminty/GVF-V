If you are on MacOS you need to use the "-XstartOnFirstThread --enable-native-access=ALL-UNNAMED" JVM args to get it to run correctly

Save for later reference:

        glfwGetMouseButton(WindowID, 0) //left click
        glfwGetMouseButton(WindowID, 1) //right click

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

        glPointSize(10.0f);
        glBegin(GL_POINTS);
        glColor3f(1.0f, 0.3f, 0.3f);
        glVertex2f((float) GetCursorPosX(WindowID), (float) GetCursorPosY(WindowID));
        glEnd();
