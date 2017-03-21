package ch.zhaw.iwi.cis.pews.dao.data.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.P2POneKeywordDao;
import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneKeyword;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class P2POneKeywordDaoImpl extends ExerciseDataDaoImpl implements P2POneKeywordDao
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return P2POneKeyword.class;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public P2POneKeyword findByKeywordString( String keywordString )
	{
		List< P2POneKeyword > results = em
			.createQuery( "from P2POneKeyword p where LOWER(p.keyword) = LOWER(:keyword_string)" )
			.setParameter( "keyword_string", keywordString )
			.getResultList();
		if ( results.size() > 0 )
		{
			return results.get( 0 );
		}
		else
		{
			throw new RuntimeException( "P2POneKeyword with keyword = " + keywordString + " could not be found!" );
		}
	}

}
