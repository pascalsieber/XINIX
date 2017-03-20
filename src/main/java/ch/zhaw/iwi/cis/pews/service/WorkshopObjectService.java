package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface WorkshopObjectService extends Service
{
	public < T extends WorkshopObject > String persist( T object );

	public < T extends WorkshopObject > void remove( T object );

	public < T extends WorkshopObject > T findByID( String id );

	public < T extends WorkshopObject > List< T > findAll();

	Object simplifyOwnerInObjectGraph( Object object );

	public < T extends WorkshopObject > List< T > findAllByClientID( String clientID );
}
