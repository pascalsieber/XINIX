package ch.zhaw.iwi.cis.pews.dao;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;

public class ExerciseDaoImpl extends IdentifiableObjectDaoImpl implements ExerciseDao
{

	@Override
	protected Class< ? extends IdentifiableObject > getPersistentObjectClass()
	{
		return ExerciseImpl.class;
	}

}
