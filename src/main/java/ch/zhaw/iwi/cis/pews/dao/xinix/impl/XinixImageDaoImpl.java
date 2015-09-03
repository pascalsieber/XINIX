package ch.zhaw.iwi.cis.pews.dao.xinix.impl;

import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopObjectDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.xinix.XinixImageDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImage;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class XinixImageDaoImpl extends WorkshopObjectDaoImpl implements XinixImageDao
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return XinixImage.class;
	}

}
