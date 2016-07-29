package ch.zhaw.iwi.cis.pews.dao;

import ch.zhaw.iwi.cis.pews.model.input.CachedInput;

public interface CachedInputDao extends WorkshopObjectDao
{

	public CachedInput findBySessionID( String sessionID );

}
