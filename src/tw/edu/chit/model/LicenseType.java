package tw.edu.chit.model;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.type.IntegerType;

public final class LicenseType extends PersistentCharacterEnum {

	/* modi by yichen 2012/05/31================================begin=======================
	 * 原證照類別 1:國際認証 2:政府機關 3:英文證照 4:其他 	 
	public static final LicenseType INTERNATIONAL = new LicenseType("國際認証", '1');
	public static final LicenseType GOVENMENT = new LicenseType("政府機關", '2');
	public static final LicenseType ENGLISH = new LicenseType("英文證照", '3');		
	public static final LicenseType OTHER = new LicenseType("其他", '4');	 	
	 * 根據 101.02.06貞伶提供之新證照列表修正內容如下：
	 * 證照類別 (1)公職考試  (2)語文證照-英 文 (3)語文證照-非 英文  (4)國際認證 (5)政府機關 (6)其他 
	 * modi by yichen 2012/05/31=================================end======================== */
	
	public static final LicenseType EXAMINATION = new LicenseType("公職考試", '1');	
	public static final LicenseType ENGLISH = new LicenseType("語文證照-英文", '2');	
	public static final LicenseType LANGUAGE_OTHER = new LicenseType("語文證照-非英文", '3');	
	public static final LicenseType INTERNATIONAL = new LicenseType("國際認証", '4');
	public static final LicenseType GOVENMENT = new LicenseType("政府機關", '5');
	public static final LicenseType OTHER = new LicenseType("其他", '6');

	private static final long serialVersionUID = -5826545213653966618L;

	private static final Map<String, LicenseType> INSTANCES = new HashMap<String, LicenseType>();

	static {
	/* modi by yichen 2012/05/31================================begin=======================
	 * 原證照類別 1:國際認証 2:政府機關 3:英文證照 4:其他 	
		INSTANCES.put(INTERNATIONAL.toString(), INTERNATIONAL);
		INSTANCES.put(GOVENMENT.toString(), GOVENMENT);
		INSTANCES.put(ENGLISH.toString(), ENGLISH);
		INSTANCES.put(OTHER.toString(), OTHER);
	 *  根據 101.02.06貞伶提供之新證照列表修正內容如下：
	 * 證照類別 (1)公職考試  (2)語文證照-英 文 (3)語文證照-非 英文  (4)國際認證 (5)政府機關 (6)其他 
	 * modi by yichen 2012/05/31=================================end======================== */
		INSTANCES.put(EXAMINATION.toString(), EXAMINATION);
		INSTANCES.put(ENGLISH.toString(), ENGLISH);
		INSTANCES.put(LANGUAGE_OTHER.toString(), LANGUAGE_OTHER);
		INSTANCES.put(INTERNATIONAL.toString(), INTERNATIONAL);
		INSTANCES.put(GOVENMENT.toString(), GOVENMENT);
		INSTANCES.put(OTHER.toString(), OTHER);
	}

	public LicenseType() {
	}

	private LicenseType(String name, char persistentValue) {
		super(name, persistentValue);
	}

	@Override
	public String toString() {
		return name;
	}

	public static LicenseType getInstance(String examine) {
		return INSTANCES.get(examine);
	}

	@Override
	protected IntegerType getNullableTypeInteger() {
		// TODO Auto-generated method stub
		return null;
	}

}
