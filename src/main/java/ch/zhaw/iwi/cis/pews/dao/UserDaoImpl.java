package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class UserDaoImpl extends IdentifiableObjectDaoImpl implements UserDao
{

	public UserDaoImpl()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public PrincipalImpl findByLoginName( String loginName )
	{
		List< PrincipalImpl > results = getEntityManager()
			.createQuery(
				"select principal FROM PrincipalImpl principal " + "JOIN FETCH principal.credential as cred " + "JOIN FETCH principal.session as session "
						+ "JOIN FETCH principal.sessionInvitations as invitations " + "JOIN FETCH principal.sessionAcceptances as acceptances "
						+ "JOIN FETCH principal.sessionExecutions as executions " + "where principal.loginName = :login_name" )
			.setParameter( "login_name", loginName )
			.getResultList();

		if ( results.size() > 0 )
		{
			return results.get( 0 );
		}
		else
		{
			return null;
		}
	}

	@Override
	protected Class< ? extends IdentifiableObject > getPersistentObjectClass()
	{
		return PrincipalImpl.class;
	}

}
