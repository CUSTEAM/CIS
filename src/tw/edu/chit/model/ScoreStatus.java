package tw.edu.chit.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.type.IntegerType;

public class ScoreStatus extends PersistentCharacterEnum {

	public static final ScoreStatus OK = new ScoreStatus("OK", '1');
	public static final ScoreStatus STATUS_2 = new ScoreStatus("1/2", '2');
	public static final ScoreStatus STATUS_3 = new ScoreStatus("2/3", '3');

	private static final long serialVersionUID = 6964397491063579814L;

	private static final Map<String, ScoreStatus> INSTANCES = new HashMap<String, ScoreStatus>();

	static {
		INSTANCES.put(OK.toString(), OK);
		INSTANCES.put(STATUS_2.toString(), STATUS_2);
		INSTANCES.put(STATUS_3.toString(), STATUS_3);
	}

	public ScoreStatus() {
	}

	private ScoreStatus(String name, char persistentValue) {
		super(name, persistentValue);
	}

	public String toString() {
		return name;
	}

	public static ScoreStatus getInstance(String scoreStatus) {
		return INSTANCES.get(scoreStatus);
	}

	@Override
	protected IntegerType getNullableTypeInteger() {
		// TODO Auto-generated method stub
		return null;
	}

}
