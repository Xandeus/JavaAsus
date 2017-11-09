package LWJGL;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.*;

import entities.AbstractEntity;
import entities.AbstractMoveableEntity;
import entities.Entity;
import entities.MoveableEntity;
public class EntityDemo {
	private static long lastFrame;
	private static long getTime(){
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	private static double getDelta(){
		long currentTime = getTime();
		double delta = (double) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}
	private static class Box extends AbstractMoveableEntity{

		public Box(double x,double y, double width,double height){
			super(x,y,width,height);
		}

		@Override
		public void draw() {
			glRectd(x,y,x +width,y+height);
		}
	}
	
	private static class Point extends AbstractEntity{

		public Point(double x, double y) {
			super(x, y, 1, 1);
		}

		@Override
		public void draw() {
			glBegin(GL_POINTS);
			glVertex2d(x,y);
			glEnd();
		}

		@Override
		public void update(int delta) {
			// TODO Auto-generated method stub
		}
		
		
	}
	
	public EntityDemo(){
		try{
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Hello LWJGL");
			Display.create();
		}catch(LWJGLException e){
			e.printStackTrace();
		}
		//Initialize entity
		
		MoveableEntity box = new Box(100, 100, 50, 50);
		Entity point = new Point(10,10);
		
		//initialization code
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		lastFrame = getTime();
	while(!Display.isCloseRequested()){
		//Render
		
		point.setLocation(Mouse.getX(), 480-Mouse.getY() -1);
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		int delta = (int) getDelta();
		box.update(delta);
		point.update(delta);
		
		if(box.intersects(point)){
			box.setDX(0.2);
		}
		
		point.draw();
		box.draw();
		
			
		Display.update();
		Display.sync(60);
	}
		Display.destroy();
	}
	public static void main(String args[]){
		new EntityDemo();
	}
	
}