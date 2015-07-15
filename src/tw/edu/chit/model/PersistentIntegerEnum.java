package tw.edu.chit.model;

import org.hibernate.Hibernate;
import org.hibernate.type.IntegerType;
import org.hibernate.type.NullableType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;

public abstract class PersistentIntegerEnum extends PersistentEnum {

	protected PersistentIntegerEnum() {
	}

	protected PersistentIntegerEnum(String name, int persistentInteger) {
		super(name, new Integer(persistentInteger));
	}

	@Override
	public int compareTo(Object other) {
		if (other == this) {
			return 0;
		}
		return ((Integer) getEnumCode())
				.compareTo((Integer) ((PersistentEnum) other).getEnumCode());

	}

	@Override
	protected IntegerType getNullableTypeInteger() {
		return StandardBasicTypes.INTEGER;
	}

}
