package tw.edu.chit.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.type.IntegerType;

public final class Exam extends PersistentCharacterEnum {

	public static final Exam NO = new Exam("否", 'N');
	public static final Exam YES = new Exam("是", 'Y');
	
	private static final long serialVersionUID = 3979887485391045744L;

	private static final Map<String, Exam> INSTANCES = new HashMap<String, Exam>();

	static {
		INSTANCES.put(YES.toString(), YES);
		INSTANCES.put(NO.toString(), NO);
	}

	public Exam() {
	}

	public Exam(String name, char persistentValue) {
		super(name, persistentValue);
	}

	public String toString() {
		return name;
	}

	public static Exam getInstance(String exam) {
		return INSTANCES.get(exam);
	}

	@Override
	protected IntegerType getNullableTypeInteger() {
		// TODO Auto-generated method stub
		return null;
	}

}
