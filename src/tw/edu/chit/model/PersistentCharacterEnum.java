package tw.edu.chit.model;

import org.hibernate.Hibernate;
import org.hibernate.type.NullableType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.StringType;

public abstract class PersistentCharacterEnum extends PersistentEnum {

	protected PersistentCharacterEnum() {
	}

	protected PersistentCharacterEnum(String name, char persistentCharacter) {
		super(name, new Character(persistentCharacter));
	}

	public int compareTo(Object other) {
		if (other == this) {
			return 0;
		}
		return ((Character) getEnumCode())
				.compareTo((Character) ((PersistentEnum) other).getEnumCode());
	}

	protected StringType getNullableType() {
		return StandardBasicTypes.STRING;
	}

}
