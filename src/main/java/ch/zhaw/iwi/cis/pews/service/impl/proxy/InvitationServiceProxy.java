package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.service.InvitationService;
import ch.zhaw.iwi.cis.pews.service.rest.InvitationRestService;

public class InvitationServiceProxy extends WorkshopObjectServiceProxy implements InvitationService
{

	protected InvitationServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, InvitationRestService.BASE );
	}

	@Override
	public void accept( String invitationID )
	{
		getServiceTarget().path( InvitationRestService.ACCEPT ).request( MediaType.APPLICATION_JSON ).post( Entity.json( invitationID ) );
	}

	@Override
	public Invitation findInvitationByID( String id )
	{
		return getServiceTarget().path( InvitationRestService.FIND_BY_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( id ) ).readEntity( Invitation.class );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< Invitation > findAllInvitations()
	{
		return getServiceTarget().path( InvitationRestService.FIND_ALL ).request( MediaType.APPLICATION_JSON ).post( Entity.json( "" ) ).readEntity( List.class );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< Invitation > findByUserID( String userID )
	{
		return getServiceTarget().path( InvitationRestService.FIND_BY_USER_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( userID ) ).readEntity( List.class );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< Invitation > findBySessionID( String sessionID )
	{
		return getServiceTarget().path( InvitationRestService.FIND_BY_USER_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( sessionID ) ).readEntity( List.class );
	}

	@Override
	public void sendByID( String invitationID )
	{
		getServiceTarget().path( InvitationRestService.SEND_BY_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( invitationID ) );
	}

	@Override
	public void sendBySessionID( String sessionID )
	{
		getServiceTarget().path( InvitationRestService.SEND_BY_SESSION_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( sessionID ) );
	}

	@Override
	public void sendByWorkshopID( String workshopID )
	{
		getServiceTarget().path( InvitationRestService.SEND_BY_WORKSHOP_ID ).request( MediaType.APPLICATION_JSON ).post( Entity.json( workshopID ) );
	}

}
