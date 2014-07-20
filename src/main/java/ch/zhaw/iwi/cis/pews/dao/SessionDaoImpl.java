package ch.zhaw.iwi.cis.pews.dao;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;

public class SessionDaoImpl extends IdentifiableObjectDaoImpl implements SessionDao
{

	@Override
	protected Class< ? extends IdentifiableObject > getPersistentObjectClass()
	{
		return SessionImpl.class;
	}

}
