package ch.zhaw.iwi.cis.pews.service.util;

import ch.zhaw.iwi.cis.pews.model.user.UserImpl;

public interface MailService
{
	void sendMail( UserImpl recipient, String messageString, String messageSubject, String messageFrom );
}
