package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;

public interface ExerciseDataDao extends ExerciseDao
{

	public List< ExerciseDataImpl > findByExerciseID( int exerciseID );

}
