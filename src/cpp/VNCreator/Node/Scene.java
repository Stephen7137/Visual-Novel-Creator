package cpp.VNCreator.Node;

import java.util.ArrayList;

public class Scene {
	
	String background;
	ArrayList<Actor> cast;
	
	public String getBackground(){
		return background;
	}
	
	public void setBackground(String background){
		this.background = background;
	}
	
	public void addActor(String Filename, int x1Location, int y1Location,
			int x2Location,	int y2Location,	float delay){
		Actor actor = new Actor(Filename, x1Location, y1Location,
				x2Location,	y2Location,	delay);
		if(cast == null){
			cast = new ArrayList<Actor>();
		}
		cast.add(actor);
	}
		
	public class Actor{
		
		String Filename;
		int x1Location;
		int y1Location;
		int x2Location;
		int y2Location;
		float delay;	
		
		public Actor(String Filename, int x1Location, int y1Location,
				int x2Location,	int y2Location,	float delay){
			this.Filename = Filename;
			this.x1Location = x1Location;
			this.y1Location = y1Location;
			this.x2Location = x2Location;
			this.y2Location =  y2Location;
			this.delay = delay;
		}
	}
}
