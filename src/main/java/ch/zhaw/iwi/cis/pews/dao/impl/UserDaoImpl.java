package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.UserDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class UserDaoImpl extends WorkshopObjectDaoImpl implements UserDao
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
				"select principal FROM PrincipalImpl principal " + "LEFT JOIN FETCH principal.credential as cred " + "LEFT JOIN FETCH principal.session as session "
						+ "LEFT JOIN FETCH principal.sessionInvitations as invitations " + "LEFT JOIN FETCH principal.sessionAcceptances as acceptances "
						+ "LEFT JOIN FETCH principal.sessionExecutions as executions " + "where principal.loginName = :login_name" )
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

	@SuppressWarnings( "unchecked" )
	@Override
	public List< PrincipalImpl > finAllUsersForLoginService()
	{
		return getEntityManager().createQuery( "from " + getWorkshopObjectClass().getSimpleName() ).getResultList();
	}

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return PrincipalImpl.class;
	}

}
