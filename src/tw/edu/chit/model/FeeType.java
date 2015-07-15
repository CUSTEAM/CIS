package tw.edu.chit.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.type.IntegerType;

public final class FeeType extends PersistentCharacterEnum {

	public static final FeeType GRANT_IN_AID = new FeeType("助學貸款", '0');
	public static final FeeType MISCELLANEOUS_FEE = new FeeType("學雜費", '1');
	public static final FeeType PART_WORK_PART_STUDY_FEE = new FeeType("工讀費",'2');
	public static final FeeType FEE_BACK = new FeeType("退費", '3');

	private static final long serialVersionUID = -2395529166274039476L;

	private static final Map<String, FeeType> INSTANCES = new HashMap<String, FeeType>();

	static {
		INSTANCES.put(GRANT_IN_AID.toString(), GRANT_IN_AID);
		INSTANCES.put(MISCELLANEOUS_FEE.toString(), MISCELLANEOUS_FEE);
		INSTANCES.put(PART_WORK_PART_STUDY_FEE.toString(), PART_WORK_PART_STUDY_FEE);
		INSTANCES.put(FEE_BACK.toString(), FEE_BACK);
	}

	public FeeType() {
	}

	private FeeType(String name, char persistentValue) {
		super(name, persistentValue);
	}

	public String toString() {
		return name;
	}

	public static FeeType getInstance(String feeType) {
		return INSTANCES.get(feeType);
	}

	@Override
	protected IntegerType getNullableTypeInteger() {
		// TODO Auto-generated method stub
		return null;
	}

}
