package ch.zhaw.iwi.cis.pews.dao.data.impl;

import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDataDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneKeyword;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class P2POneKeywordDao extends ExerciseDataDaoImpl
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return P2POneKeyword.class;
	}
	
}
