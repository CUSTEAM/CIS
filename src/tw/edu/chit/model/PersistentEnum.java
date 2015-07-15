package tw.edu.chit.model;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.HibernateException;
import org.hibernate.type.IntegerType;
import org.hibernate.type.NullableType;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

@SuppressWarnings("unchecked")
abstract class PersistentEnum implements Comparable, Serializable, UserType {

	protected Serializable enumCode;
	protected String name;

	protected transient int hashCode;

	@SuppressWarnings("unchecked")
	private static final Map enumClasses = new HashMap();

	protected PersistentEnum() {
	}

	@SuppressWarnings("unchecked")
	protected PersistentEnum(String name, Serializable enumCode) {
		this.name = name;
		this.enumCode = enumCode;
		hashCode = 7 + returnedClass().hashCode() + 3 * enumCode.hashCode();

		Class enumClass = returnedClass();
		Map entries = (Map) enumClasses.get(enumClass);
		if (entries == null) {
			entries = new HashMap();
			enumClasses.put(enumClass, entries);
		}
		if (entries.containsKey(enumCode)) {
			throw new IllegalArgumentException(
					"The enum code must be unique, '" + enumCode
							+ "' has already been added");
		}
		entries.put(enumCode, this);
	}

	public abstract int compareTo(Object other);

	protected abstract StringType getNullableType();
	
	protected abstract IntegerType getNullableTypeInteger();

	public Object assemble(Serializable cache, Object arg1)
			throws HibernateException {
		return cache;
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y)
			return true;
		else if (x == null || y == null)
			return false;
		else
			return getNullableType().equals(y);
	}

	public final int hashCode() {
		return hashCode;
	}

	public int hashCode(Object o) throws HibernateException {
		return HashCodeBuilder.reflectionHashCode(o);
	}

	public boolean isMutable() {
		return false;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object arg2)
			throws HibernateException, SQLException {
		Serializable enumCode = (Serializable) getNullableType().nullSafeGet(
				rs, names[0]);
		Map entries = (Map) enumClasses.get(returnedClass());
		return (PersistentEnum) ((entries != null) ? entries.get(enumCode)
				: null);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {
		if ((value != null)
				&& !returnedClass().isAssignableFrom(value.getClass())) {
			throw new IllegalArgumentException("Received value is not a ["
					+ returnedClass().getName() + "] but [" + value.getClass()
					+ "]");
		}
		if (value == null) {
			st.setNull(index, getNullableType().sqlType());
		} else {
			Serializable enumCode = ((PersistentEnum) value).getEnumCode();
			st.setObject(index, enumCode, getNullableType().sqlType());
		}
	}

	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return deepCopy(original);
	}

	@SuppressWarnings("unchecked")
	public Class returnedClass() {
		return this.getClass();
	}

	public int[] sqlTypes() {
		return new int[] { getNullableType().sqlType() };
	}

	public final Serializable getEnumCode() {
		return enumCode;
	}

	@SuppressWarnings("unchecked")
	protected Object readResolve() {
		Map entries = (Map) enumClasses.get(returnedClass());
		return (entries != null) ? entries.get(enumCode) : null;
	}

	@SuppressWarnings("unchecked")
	protected static Collection getEnumCollection(Class enumClass) {
		Map entries = (Map) enumClasses.get(enumClass);
		return (entries != null) ? Collections.unmodifiableCollection(entries
				.values()) : Collections.EMPTY_LIST;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public final boolean equals(Object other) {

		if (!(other instanceof PersistentEnum)) {
			return false;
		}

		if (other == this) {
			return true;

		} else if (other == null) {
			return false;

		} else if (((PersistentEnum) other).returnedClass().getName().equals(
				returnedClass().getName())) {
			try {
				return enumCode.equals(((PersistentEnum) other).enumCode);

			} catch (ClassCastException ex) {
				try {
					Method mth = other.getClass().getMethod("getEnumCode",
							(java.lang.Class[]) null);
					Serializable enumCode = (Serializable) mth.invoke(other,
							(java.lang.Object[]) null);
					return this.enumCode.equals(enumCode);
				} catch (Exception ignore) {
				}
				return false;
			}
		} else {
			return false;
		}
	}

}
