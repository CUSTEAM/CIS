package tw.edu.chit.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.type.IntegerType;

public final class GraduateType extends PersistentCharacterEnum {

	public static final GraduateType TO_GRADUATE = new GraduateType("畢業", '0');
	public static final GraduateType TO_STUDY_IN_SCHOOL = new GraduateType(
			"肄業", '1');
	public static final GraduateType TO_COMPLETE_STUDY = new GraduateType("結業",
			'2');

	private static final long serialVersionUID = 2070704235536059003L;

	private static final Map<String, GraduateType> INSTANCES = new HashMap<String, GraduateType>();

	static {
		INSTANCES.put(TO_GRADUATE.toString(), TO_GRADUATE);
		INSTANCES.put(TO_STUDY_IN_SCHOOL.toString(), TO_STUDY_IN_SCHOOL);
		INSTANCES.put(TO_COMPLETE_STUDY.toString(), TO_COMPLETE_STUDY);
	}

	public GraduateType() {
	}

	public GraduateType(String name, char persistentValue) {
		super(name, persistentValue);
	}

	public String toString() {
		return name;
	}

	public static GraduateType getInstance(String graduateType) {
		return INSTANCES.get(graduateType);
	}

	@Override
	protected IntegerType getNullableTypeInteger() {
		// TODO Auto-generated method stub
		return null;
	}

}
