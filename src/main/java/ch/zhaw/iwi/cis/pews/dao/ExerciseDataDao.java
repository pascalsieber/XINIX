package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;

public interface ExerciseDataDao extends WorkshopObjectDao
{

	public List< ExerciseDataImpl > findByExerciseID( String exerciseID );

	public List< ExerciseDataImpl > findByWorkshopAndExerciseDataClass( Class< ? > dataClass );

	public ExerciseDataImpl findDataByID( String id );
}
