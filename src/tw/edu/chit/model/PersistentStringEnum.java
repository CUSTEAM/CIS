package tw.edu.chit.model;

import org.hibernate.Hibernate;
import org.hibernate.type.IntegerType;
import org.hibernate.type.NullableType;
import org.hibernate.type.StandardBasicTypes;

public abstract class PersistentStringEnum extends PersistentEnum {

	protected PersistentStringEnum() {
	}

	protected PersistentStringEnum(String name) {
		super(name, name);
	}

	protected PersistentStringEnum(String name, String persistentString) {
		super(name, persistentString);
	}

	@Override
	public int compareTo(Object other) {
		if (other == this) {
			return 0;
		}
		return ((String) getEnumCode())
				.compareTo((String) ((PersistentEnum) other).getEnumCode());
	}

	@Override
	protected IntegerType getNullableTypeInteger() {
		return StandardBasicTypes.INTEGER;
	}

}
