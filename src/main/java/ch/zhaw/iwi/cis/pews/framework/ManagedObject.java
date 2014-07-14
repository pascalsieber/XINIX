package ch.zhaw.iwi.cis.pews.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface ManagedObject
{
	public enum Scope { CLASSLOADER, THREAD, CLIENT, POOLED };
	public enum Transactionality { TRANSACTIONAL, NON_TRANSACTIONAL };
	
	// TODO refactor transactionality to transactional : boolean
	public Scope scope() default Scope.CLIENT;
	public Transactionality transactionality() default Transactionality.NON_TRANSACTIONAL;
	public int poolSize() default ManagedObjectDefaults.DEFAULT_POOL_SIZE;
	public String entityManager() default ManagedObjectDefaults.DEFAULT_ENTITY_MANAGER;
}
