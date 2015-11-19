package ch.zhaw.iwi.cis.pews.dao;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;

public interface ExerciseDao extends WorkshopObjectDao
{
	public ExerciseImpl findExerciseByID( String id );
}
