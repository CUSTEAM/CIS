package tw.edu.chit.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.type.IntegerType;

public class InSchoolType extends PersistentCharacterEnum {

	public static final InSchoolType TO_APPLY = new InSchoolType("申請入學", '0');
	public static final InSchoolType TO_COMMEND = new InSchoolType("推薦甄選", '1');
	public static final InSchoolType TO_UNITE = new InSchoolType("聯合分發", '2');
	public static final InSchoolType TO_INDEPENDENT = new InSchoolType("獨立招生", '3');

	private static final long serialVersionUID = -3162072708167418676L;

	private static final Map<String, InSchoolType> INSTANCES = new HashMap<String, InSchoolType>();

	public InSchoolType() {
		INSTANCES.put(TO_APPLY.toString(), TO_APPLY);
		INSTANCES.put(TO_COMMEND.toString(), TO_COMMEND);
		INSTANCES.put(TO_UNITE.toString(), TO_UNITE);
		INSTANCES.put(TO_INDEPENDENT.toString(), TO_INDEPENDENT);
	}

	public InSchoolType(String name, char persistentValue) {
		super(name, persistentValue);
	}

	public String toString() {
		return name;
	}

	public static InSchoolType getInstance(String inSchoolType) {
		return INSTANCES.get(inSchoolType);
	}

	@Override
	protected IntegerType getNullableTypeInteger() {
		// TODO Auto-generated method stub
		return null;
	}

}
