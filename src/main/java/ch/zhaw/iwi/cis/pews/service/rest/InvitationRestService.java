package ch.zhaw.iwi.cis.pews.service.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
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

	private InvitationService invitationService;

	public InvitationRestService()
	{
		super();
		invitationService = ZhawEngine.getManagedObjectRegistry().getManagedObject( InvitationServiceImpl.class.getSimpleName() );
	}

	@POST
	@Path( PERSIST )
	public String persistInvitation( Invitation obj )
	{
		return super.persist( obj );
	}

	@POST
	@Path( FIND_BY_ID )
	public Invitation findInvitationByID( String id )
	{
		return super.findByID( id );
	}

	@POST
	@Path( REMOVE )
	public void removeInvitation( Invitation obj )
	{
		super.remove( obj );
	}

	@POST
	@Path( FIND_ALL )
	public List< Invitation > findAllInvitations( @Context HttpServletRequest request )
	{
		return super.findAll( getUserService().getClientFromAuth( request ) );
	}

	@POST
	@Path( ACCEPT )
	public void accept( String invitationID )
	{
		invitationService.accept( invitationID );
	}

	@Override
	protected WorkshopObjectService getWorkshopObjectService()
	{
		return invitationService;
	}
}
