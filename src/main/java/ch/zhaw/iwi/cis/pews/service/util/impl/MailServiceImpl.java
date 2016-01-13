package ch.zhaw.iwi.cis.pews.service.util.impl;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;

import ch.zhaw.iwi.cis.pews.PewsConfig;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.util.MailService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class MailServiceImpl implements MailService
{

	@Override
	public void sendProfile( UserImpl recipient, String messageString, String messageSubject, String messageFrom )
	{
		Properties props = new Properties();
		props.put( "mail.smtp.host", PewsConfig.getMailHost() );
		props.put( "mail.smtp.starttls.enable", "true" );
		props.put( "mail.smtp.auth", "true" );
		props.put( "mail.smtp.port", PewsConfig.getMailPort() );

		Session mailSession = Session.getDefaultInstance( props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication( PewsConfig.getMailUserName(), PewsConfig.getMailPassword() );
			}
		} );

		// append login information to message
		messageString += "\n\n" + PewsConfig.getMailWebClientInfo() + " " + recipient.getAuthenticationUrl();

		try
		{
			Message message = new MimeMessage( mailSession );
			message.setFrom( new InternetAddress( messageFrom ) );
			message.setRecipients( Message.RecipientType.TO, InternetAddress.parse( recipient.getLoginNameEmailPart() ) );
			message.setSubject( messageSubject );
			message.setContent( messageString, "text/html; charset=utf-8" );

			Transport.send( message );
		}
		catch ( MessagingException e )
		{
			throw new RuntimeException( e );
		}
	}

	@Override
	public void sendInvitation( Invitation invitation, String messageString, String messageSubject, String messageFrom )
	{
		Properties props = new Properties();
		props.put( "mail.smtp.host", PewsConfig.getMailHost() );
		props.put( "mail.smtp.starttls.enable", "true" );
		props.put( "mail.smtp.auth", "true" );
		props.put( "mail.smtp.port", PewsConfig.getMailPort() );

		Session mailSession = Session.getDefaultInstance( props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication( PewsConfig.getMailUserName(), PewsConfig.getMailPassword() );
			}
		} );

		// append auto join link to message
		String encodedLoginName = new String( Base64.encodeBase64( ( (UserImpl)invitation.getInvitee() ).getLoginName().getBytes() ) );
		String encodedPassword = new String( Base64.encodeBase64( invitation.getInvitee().getCredential().getPassword().getBytes() ) );

		String link = PewsConfig.getWebClientAuthenticationUrl() + PewsConfig.getWebClientAuthenticationUserParam() + encodedLoginName + PewsConfig.getWebClientAuthenticationPasswordParam()
				+ encodedPassword + PewsConfig.getWebClientAuthenticationInvitationTarget() + PewsConfig.getWebClientAuthenticationInvitationParam() + invitation.getSession().getID();

		messageString += "\n\n" + "<a href=\"" + link + "\">" + PewsConfig.getMailWebClientInfo() + "</a>";

		try
		{
			Message message = new MimeMessage( mailSession );
			message.setFrom( new InternetAddress( messageFrom ) );
			message.setRecipients( Message.RecipientType.TO, InternetAddress.parse( ( (UserImpl)invitation.getInvitee() ).getLoginNameEmailPart() ) );
			message.setSubject( messageSubject );
			message.setContent( messageString, "text/html; charset=utf-8" );

			Transport.send( message );
		}
		catch ( MessagingException e )
		{
			throw new RuntimeException( e );
		}
	}

}
