package tw.edu.chit.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.type.IntegerType;

public final class LicenseLocale extends PersistentCharacterEnum {
	
	/* modi by yichen 2012/05/31================================begin=======================
	 * 原地區類別(國內/國外) 1:國內 2:國外
	public static final LicenseLocale DOMESTIC = new LicenseLocale("國內", '1');
	public static final LicenseLocale FOREIGN = new LicenseLocale("國外", '2');
	 * 根據 101.02.06貞伶提供之新證照列表修正內容如下：
	 * 地區類別  1:國內 2:國外 3:大陸地區（含港澳） 
	 * modi by yichen 2012/05/31=================================end======================== */
	
	public static final LicenseLocale DOMESTIC = new LicenseLocale("國內", '1');
	public static final LicenseLocale FOREIGN = new LicenseLocale("國外", '2');
	public static final LicenseLocale CHINA = new LicenseLocale("大陸地區（含港澳）", '3');

	private static final long serialVersionUID = 4346280292007556031L;

	private static final Map<String, LicenseLocale> INSTANCES = new HashMap<String, LicenseLocale>();

	static {
		/* modi by yichen 2012/05/31================================begin=======================
		 * 原地區類別(國內/國外) 1:國內 2:國外
		INSTANCES.put(DOMESTIC.toString(), DOMESTIC);
		INSTANCES.put(FOREIGN.toString(), FOREIGN);
		 * 根據 101.02.06貞伶提供之新證照列表修正內容如下：
		 * 地區類別  1:國內 2:國外 3:大陸地區（含港澳） 
		 * modi by yichen 2012/05/31=================================end======================== */
		
		INSTANCES.put(DOMESTIC.toString(), DOMESTIC);
		INSTANCES.put(FOREIGN.toString(), FOREIGN);
		INSTANCES.put(CHINA.toString(), CHINA);
	}

	public LicenseLocale() {
	}

	private LicenseLocale(String name, char persistentValue) {
		super(name, persistentValue);
	}

	@Override
	public String toString() {
		return name;
	}

	public static LicenseLocale getInstance(String examine) {
		return INSTANCES.get(examine);
	}

	@Override
	protected IntegerType getNullableTypeInteger() {
		// TODO Auto-generated method stub
		return null;
	}

}
