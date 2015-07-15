package tw.edu.chit.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.type.IntegerType;

public final class Military extends PersistentCharacterEnum {

	public static final Military NO = new Military("無", '0');
	public static final Military YES = new Military("兵役", '1');
	public static final Military DEMOBILIZED = new Military("退伍", '2');

	private static final long serialVersionUID = 9169443335326919898L;

	private static final Map<String, Military> INSTANCES = new HashMap<String, Military>();

	static {
		INSTANCES.put(YES.toString(), YES);
		INSTANCES.put(NO.toString(), NO);
		INSTANCES.put(DEMOBILIZED.toString(), DEMOBILIZED);
	}

	public Military() {
	}

	public Military(String name, char persistentValue) {
		super(name, persistentValue);
	}

	public String toString() {
		return name;
	}

	public static Military getInstance(String military) {
		return INSTANCES.get(military);
	}

	@Override
	protected IntegerType getNullableTypeInteger() {
		// TODO Auto-generated method stub
		return null;
	}

}
