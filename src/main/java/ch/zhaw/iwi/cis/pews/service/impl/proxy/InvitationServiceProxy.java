package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.service.InvitationService;
import ch.zhaw.iwi.cis.pews.service.rest.InvitationRestService;

public class InvitationServiceProxy extends IdentifiableObjectServiceProxy implements InvitationService
{

	protected InvitationServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, InvitationRestService.BASE );
	}

	@Override
	public void accept( int invitationID )
	{
		getServiceTarget().path( InvitationRestService.ACCEPT ).request(MediaType.APPLICATION_JSON).post( Entity.json(invitationID) );
	}

}
