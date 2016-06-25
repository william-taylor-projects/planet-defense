package  com.planetDefense;

public class Collision implements IEventListener {
	public interface CollisionArray {
		public void collisionID(Integer num);
	}
	
	private GL_Image Object;
	private GL_Image Obj;
	private IEvent event;
	private int Number;
	
	public Collision() {
		Number = -1;
	}
	
	public void surfaces(GL_Image i, GL_Image b, int num) {
		Number = num;
		Object = i;
		Obj = b;
	}
	
	public void surfaces(GL_Image i, GL_Image b) {
		Object = i;
		Obj = b;
	}
	
	@Override
	public void check(IEventManager manager) {		
		Vector2D posTwo = Object.getPosition();
		Vector2D posOne = Obj.getPosition();
		Vector2D szTwo = Object.getSize();
		Vector2D szOne = Obj.getSize();
		
		float x2 = posTwo.x();
		float x1 = posOne.x();
		float w2 = (x2 + szTwo.x());
		float w1 = (x1 + szOne.x());
		
		if(x1 <= w2 && w1 >= x2) {
			
			float y2 = posTwo.y();
			float h2 = (y2 + szTwo.y());
			float y1 = posOne.y();
			float h1 = (y1 + szOne.y());
			
			if(y1 <= h2 && h1 >= y2) {
				if(this.event instanceof CollisionArray) {
					((CollisionArray)event).collisionID(Number);
				} 
				
				manager.triggerEvent(event, null);
			}
		} 
	}

	@Override
	public void eventType(IEvent event) {
		this.event = event;
	}
}
