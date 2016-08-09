package ch.zhaw.iwi.cis.pews.service.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ch.zhaw.iwi.cis.pews.PewsConfig;
import ch.zhaw.iwi.cis.pews.dao.UserDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.UserDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.user.PasswordCredentialImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.UserService;
import ch.zhaw.iwi.cis.pews.service.util.MailService;
import ch.zhaw.iwi.cis.pews.service.util.impl.MailServiceImpl;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class UserServiceImpl extends WorkshopObjectServiceImpl implements UserService
{
	private UserDao userdao;
	private MailService mailService;

	public UserServiceImpl()
	{
		userdao = ZhawEngine.getManagedObjectRegistry().getManagedObject( UserDaoImpl.class.getSimpleName() );
		mailService = ZhawEngine.getManagedObjectRegistry().getManagedObject( MailServiceImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return userdao;
	}

	@Override
	public PrincipalImpl findUserByID( String id )
	{
		return (PrincipalImpl)simplifyOwnerInObjectGraph( userdao.findUserByID( id ) );
	}

	@Override
	public PrincipalImpl findByLoginName( String loginName )
	{
		return (PrincipalImpl)simplifyOwnerInObjectGraph( userdao.findByLoginName( loginName ) );
	}

	@Override
	public PrincipalImpl findByLoginNameForUserContext( String loginName )
	{
		return userdao.findByLoginNameForUserContext( loginName );
	}

	@Override
	public String persistForClient( PrincipalImpl principal )
	{
		return getWorkshopObjectDao().persist( principal );
	}

	@Override
	public void sendProfile( String userID )
	{
		UserImpl user = (UserImpl)findUserByID( userID );
		String messageString = PewsConfig.getMailTextProfile();
		messageString += "\n\n" + "Login: " + user.getLoginName() + "\n" + "Passwort: " + user.getCredential().getPassword();

		mailService.sendProfile( user, messageString, PewsConfig.getMailSubjectForProfile(), PewsConfig.getMailUserName() );
	}

	@Override
	public List< PrincipalImpl > findAllUsersForLoginService()
	{
		return userdao.finAllUsersForLoginService();
	}
}
