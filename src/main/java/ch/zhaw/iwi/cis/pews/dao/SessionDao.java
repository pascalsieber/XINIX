package ch.zhaw.iwi.cis.pews.dao;

import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;

public interface SessionDao extends WorkshopObjectDao
{
	public SessionImpl findSessionByID( String id );
}
