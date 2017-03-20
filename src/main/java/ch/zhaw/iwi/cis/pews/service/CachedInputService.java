package ch.zhaw.iwi.cis.pews.service;

import ch.zhaw.iwi.cis.pews.model.input.CachedInput;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface CachedInputService extends WorkshopObjectService
{
	public CachedInput findBySessionID( String sessionID );

	public String persistCachedInput( CachedInput cachedInput );
}
