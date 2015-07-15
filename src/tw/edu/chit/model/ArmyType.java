package tw.edu.chit.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.type.IntegerType;

public class ArmyType extends PersistentCharacterEnum {

	public static final ArmyType NOTHING = new ArmyType("", '0');
	public static final ArmyType OBLIGATION = new ArmyType("義務役", '1');
	public static final ArmyType VOLUNTEER = new ArmyType("志願役", '2');
	public static final ArmyType AlLTERNATIVE = new ArmyType("替代役", '3');

	private static final long serialVersionUID = -173588923070931279L;

	private static final Map<String, ArmyType> INSTANCES = new HashMap<String, ArmyType>();

	public ArmyType() {
		INSTANCES.put(NOTHING.toString(), NOTHING);
		INSTANCES.put(OBLIGATION.toString(), OBLIGATION);
		INSTANCES.put(VOLUNTEER.toString(), VOLUNTEER);
		INSTANCES.put(AlLTERNATIVE.toString(), AlLTERNATIVE);
	}

	public ArmyType(String name, char persistentValue) {
		super(name, persistentValue);
	}

	public String toString() {
		return name;
	}

	public static ArmyType getInstance(String aborigineCode) {
		return INSTANCES.get(aborigineCode);
	}

	@Override
	protected IntegerType getNullableTypeInteger() {
		// TODO Auto-generated method stub
		return null;
	}

}
