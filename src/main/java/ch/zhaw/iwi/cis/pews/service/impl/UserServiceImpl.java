package ch.zhaw.iwi.cis.pews.service.impl;

import ch.zhaw.iwi.cis.pews.dao.IdentifiableObjectDao;
import ch.zhaw.iwi.cis.pews.service.UserService;

public class UserServiceImpl extends IdentifiableObjectServiceImpl implements UserService
{

	@Override
	protected IdentifiableObjectDao getIdentifiableObjectDao()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void acceptInvitation( int userID, int sessionID )
	{
		// TODO Auto-generated method stub
		
	}

}
