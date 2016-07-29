package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;

public interface ExerciseDataDao extends WorkshopObjectDao
{

	public List< ExerciseDataImpl > findByExerciseID( String exerciseID );

	public List< ExerciseDataImpl > findByExerciseIDs( List< String > exerciseIDs );

	public List< ExerciseDataImpl > findByWorkshopAndExerciseDataClass( WorkshopImpl workshop, Class< ? > dataClass );

	public ExerciseDataImpl findDataByID( String id );

	/**
	 * enables specialized behavior for persist operations
	 * 
	 * @param obj
	 * @return id of persisted object
	 */
	public String persistExerciseData( ExerciseDataImpl obj );

}
