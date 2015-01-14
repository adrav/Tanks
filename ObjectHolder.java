package tanks;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ObjectHolder {

	private ObjectHolder() {}
	
	private static ObjectHolder instance = new ObjectHolder();
	
	public static ObjectHolder getInstance() {
		return instance;
	}
	
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	
	public void addObject(GameObject gameObject) {
		gameObjects.add(gameObject);
	}
	
	public GameObject getObject(int i) {
		return gameObjects.get(i);
	}
	
	public void deleteObject(int index) {
		gameObjects.remove(index);
	}
	
	public void deleteObject(Collection<?> c) {
		gameObjects.removeAll(c);
	}
	
	public void clearObjects() {
		gameObjects.clear();
	}
	
	public int getSize() {
		return gameObjects.size();
	}
}
