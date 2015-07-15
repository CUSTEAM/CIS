package tw.edu.chit.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.type.IntegerType;

public final class Other extends PersistentCharacterEnum {

	public static final Other YES = new Other("是", 'Y');
	public static final Other NO = new Other("不是", 'N');

	private static final long serialVersionUID = -4786842991908886267L;

	private static final Map<String, Other> INSTANCES = new HashMap<String, Other>();

	static {
		INSTANCES.put(YES.toString(), YES);
		INSTANCES.put(NO.toString(), NO);
	}

	public Other() {
	}

	public Other(String name, char persistentCharacter) {
		super(name, persistentCharacter);
	}

	public String toString() {
		return name;
	}

	public static Other getInstance(String other) {
		return INSTANCES.get(other);
	}

	@Override
	protected IntegerType getNullableTypeInteger() {
		// TODO Auto-generated method stub
		return null;
	}

}
