package ch.zhaw.iwi.cis.pews.service.xinix.impl;

import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.xinix.XinixImageMatrixDao;
import ch.zhaw.iwi.cis.pews.dao.xinix.impl.XinixImageMatrixDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pews.service.impl.WorkshopObjectServiceImpl;
import ch.zhaw.iwi.cis.pews.service.xinix.XinixImageMatrixService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class XinixImageMatrixServiceImpl extends WorkshopObjectServiceImpl implements XinixImageMatrixService
{
	private XinixImageMatrixDao xinixImageMatrixDao;

	public XinixImageMatrixServiceImpl()
	{
		xinixImageMatrixDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( XinixImageMatrixDaoImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return xinixImageMatrixDao;
	}

	@Override
	public XinixImageMatrix findXinixImageMatrixByID( String id )
	{
		return xinixImageMatrixDao.findXinixImageMatrixByID( id );
	}

}
