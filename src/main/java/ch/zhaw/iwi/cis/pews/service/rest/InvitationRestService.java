package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.user.Invitation;
import ch.zhaw.iwi.cis.pews.service.InvitationService;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;
import ch.zhaw.iwi.cis.pews.service.impl.InvitationServiceImpl;

@Path( InvitationRestService.BASE )
@Consumes( MediaType.APPLICATION_JSON )
@Produces( MediaType.APPLICATION_JSON )
public class InvitationRestService extends WorkshopObjectRestService
{
	public static final String BASE = "/workshopService/session/invitation";

	public static final String ACCEPT = "/accept";
	public static final String FIND_BY_USER_ID = "/findByUserID";

	private InvitationService invitationService;

	public InvitationRestService()
	{
		super();
		invitationService = ZhawEngine.getManagedObjectRegistry().getManagedObject( InvitationServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( PERSIST )
	public String persist( Invitation obj )
	{
		return super.persist( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_BY_ID )
	public Invitation findByID( String id )
	{
		return invitationService.findInvitationByID( id );
	}

	@POST
	@Path( REMOVE )
	public void remove( Invitation obj )
	{
		super.remove( obj );
	}

	@SuppressWarnings( "unchecked" )
	@POST
	@Path( FIND_ALL )
	public List< Invitation > findAll()
	{
		return invitationService.findAllInvitations();
	}

	@POST
	@Path( ACCEPT )
	public void accept( String invitationID )
	{
		invitationService.accept( invitationID );
	}

	@POST
	@Path( FIND_BY_USER_ID )
	public List< Invitation > findByUserID( String userID )
	{
		return invitationService.findByUserID( userID );
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return invitationService;
	}
}
