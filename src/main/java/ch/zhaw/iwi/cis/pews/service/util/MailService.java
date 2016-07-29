package ch.zhaw.iwi.cis.pews.service.util;

import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;

public interface MailService
{

	void sendProfile( UserImpl recipient, String messageString, String messageSubject, String messageFrom );

	void sendInvitation( Invitation invitation, String messageString, String messageSubject, String messageFrom );

}
