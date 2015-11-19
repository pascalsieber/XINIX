package ch.zhaw.iwi.cis.pews.dao.xinix.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopObjectDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.xinix.XinixImageMatrixDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
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
		List< XinixImageMatrix > matrices = getEntityManager().createQuery( "from XinixImageMatrix as x LEFT JOIN FETCH x.xinixImages where x.id = :_id" ).setParameter( "_id", id ).getResultList();

		if ( matrices.size() > 0 )
		{
			return (XinixImageMatrix)cloneResult( matrices.get( 0 ) );
		}

		return null;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< XinixImageMatrix > findAllXinixImageMatrices()
	{
		List< XinixImageMatrix > matrices = getEntityManager()
			.createQuery( "from XinixImageMatrix as x LEFT JOIN FETCH x.xinixImages where x.client.id = :_client_id" )
			.setParameter( "_client_id", UserContext.getCurrentUser().getClient().getID() )
			.getResultList();
		return (List< XinixImageMatrix >)cloneResult( new ArrayList< XinixImageMatrix >( new HashSet< XinixImageMatrix >( matrices ) ) );
	}

}
