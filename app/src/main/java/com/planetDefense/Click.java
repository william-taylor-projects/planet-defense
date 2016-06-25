package  com.planetDefense;

import android.util.Log;
import android.view.MotionEvent;

public class Click implements IEventListener {
	private MotionEvent HoldEvent;
	private MotionEvent Motion;
	private GameButton Button;
	private IEvent Event;
	private float x = 0;
	private float y = 0;
	
	public Click(GameButton button) {
		Button = button;
	}
	
	public void OnTouch(MotionEvent e, float x, float y) {
		this.Motion = e;
		this.x = x;
		this.y = y;
	}

	@Override
	public void check(IEventManager manager) {
		Event.update();
		
		GL_Image sprite = (GL_Image)Button.GetRawObject();
		
		Vector2D Position = sprite.getPosition();
		Vector2D Size = sprite.getSize();
		
		if(Motion != null && Motion.getAction() == MotionEvent.ACTION_DOWN) {
			if(x >= Position.x() && x <= Position.x() + Size.x()) {
				if(y >= Position.y() && y <= Position.y() + Size.y()) {
					manager.triggerEvent(Event, false);
					OnTouch(null, -1.0f, -1.0f);
				}
			}
		}
		
		if(HoldEvent != null && HoldEvent.getAction() == MotionEvent.ACTION_DOWN) {
			if(x >= Position.x() && x <= Position.x() + Size.x()) {
				if(y >= Position.y() && y <= Position.y() + Size.y()) {
					manager.triggerEvent(Event, true);
					onLongPress(null, -1, -1);
				}
			}
		}
	}

	@Override
	public void eventType(IEvent event) {
		if(event == null) {
			Log.e("Error", "null event");
		} else {
			Event = event;
		}
	}

	public void onLongPress(MotionEvent e, int x2, int y2) {
		this.HoldEvent = e;
		this.x = x2;
		this.y = y2;
	}
}
