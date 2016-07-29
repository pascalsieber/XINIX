package ch.zhaw.iwi.cis.pews.service;

import ch.zhaw.iwi.cis.pews.model.input.CachedInput;

public interface CachedInputService extends WorkshopObjectService
{
	public CachedInput findBySessionID( String sessionID );

	public String persistCachedInput( CachedInput cachedInput );
}
