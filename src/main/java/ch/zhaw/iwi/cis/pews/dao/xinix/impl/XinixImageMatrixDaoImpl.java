package ch.zhaw.iwi.cis.pews.dao.xinix.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopObjectDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.xinix.XinixImageMatrixDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class XinixImageMatrixDaoImpl extends WorkshopObjectDaoImpl implements XinixImageMatrixDao
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return XinixImageMatrix.class;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public XinixImageMatrix findXinixImageMatrixByID( String id )
	{
		List< XinixImageMatrix > matrices = getEntityManager()
			.createQuery( "from XinixImageMatrix as x LEFT JOIN FETCH x.xinixImages as img where x.id = :_id" )
			.setParameter( "_id", id )
			.getResultList();

		if ( matrices.size() > 0 )
		{
			return matrices.get( 0 );
		}

		return null;
	}

}
