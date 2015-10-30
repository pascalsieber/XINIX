package ch.zhaw.iwi.cis.pews.service.xinix.impl;

import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.xinix.XinixImageDao;
import ch.zhaw.iwi.cis.pews.dao.xinix.impl.XinixImageDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.service.impl.WorkshopObjectServiceImpl;
import ch.zhaw.iwi.cis.pews.service.xinix.XinixImageService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class XinixImageServiceImpl extends WorkshopObjectServiceImpl implements XinixImageService
{
	private XinixImageDao xinixImageDao;

	public XinixImageServiceImpl()
	{
		xinixImageDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( XinixImageDaoImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return xinixImageDao;
	}

}
