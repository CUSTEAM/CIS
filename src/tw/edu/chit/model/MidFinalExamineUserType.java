package tw.edu.chit.model;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;


public class MidFinalExamineUserType implements UserType, Serializable {

	private static final long serialVersionUID = -7075505652813722068L;
	
	private static final int[] SQL_TYPES = { Types.CHAR };

	public Object assemble(Serializable cache, Object owner)
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
		if (x == null || y == null)
			return false;
		return x.equals(y);
	}

	public int hashCode(Object arg0) throws HibernateException {
		return HashCodeBuilder.reflectionHashCode(arg0);
	}

	public boolean isMutable() {
		return false;
	}

	public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
			throws HibernateException, SQLException {
		String examine = resultSet.getString(names[0]);
		return resultSet.wasNull() ? null : Examine.getInstance(examine);
	}

	public void nullSafeSet(PreparedStatement statement, Object value, int index)
			throws HibernateException, SQLException {
		if (value == null)
			statement.setNull(index, Types.CHAR);
		else
			statement.setString(index, value.toString());
	}

	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return deepCopy(original);
	}

	@SuppressWarnings("unchecked")
	public Class returnedClass() {
		return Examine.class;
	}

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

}
