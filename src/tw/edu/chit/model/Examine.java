package tw.edu.chit.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.type.IntegerType;

public final class Examine extends PersistentCharacterEnum {

	public static final Examine MID = new Examine("期中", 'M');
	public static final Examine FINAL = new Examine("學期", 'F');

	private static final long serialVersionUID = 6382184615793636814L;

	private static final Map<String, Examine> INSTANCES = new HashMap<String, Examine>();

	static {
		INSTANCES.put(MID.toString(), MID);
		INSTANCES.put(FINAL.toString(), FINAL);
	}

	public Examine() {
	}

	private Examine(String name, char persistentValue) {
		super(name, persistentValue);
	}

	public String toString() {
		return name;
	}

	public static Examine getInstance(String examine) {
		return INSTANCES.get(examine);
	}

	@Override
	protected IntegerType getNullableTypeInteger() {
		// TODO Auto-generated method stub
		return null;
	}

}