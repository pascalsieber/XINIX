package ch.zhaw.iwi.cis.pews.service.impl;

import java.math.BigInteger;
import java.security.SecureRandom;

import ch.zhaw.iwi.cis.pews.dao.IdentifiableObjectDao;
import ch.zhaw.iwi.cis.pews.dao.UserDao;
import ch.zhaw.iwi.cis.pews.dao.UserDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.service.UserService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class UserServiceImpl extends IdentifiableObjectServiceImpl implements UserService
{
	private UserDao userdao;

	public UserServiceImpl()
	{
		userdao = ZhawEngine.getManagedObjectRegistry().getManagedObject( UserDaoImpl.class.getSimpleName() );
	}

	@Override
	protected IdentifiableObjectDao getIdentifiableObjectDao()
	{
		return userdao;
	}

	@Override
	public PrincipalImpl findByLoginName( String loginName )
	{
		return userdao.findByLoginName( loginName );
	}

	@Override
	public String requestNewPassword( int userID )
	{
		PrincipalImpl user = findByID( userID );
		user.setCredential( new PasswordCredentialImpl( new BigInteger( 130, new SecureRandom() ).toString( 32 ) ) );
		persist( user );
		return user.getCredential().getPassword();
	}

}
