package ch.zhaw.iwi.cis.pews.framework;

import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;

public class ManagedObjectDefaults
{
	public static final Scope DEFAULT_SCOPE = Scope.CLIENT;
	public static final Transactionality DEFAULT_TRANSACTIONALITY = Transactionality.NON_TRANSACTIONAL;
	public static final int DEFAULT_POOL_SIZE = 100;
	public static final String DEFAULT_ENTITY_MANAGER = "";
}
