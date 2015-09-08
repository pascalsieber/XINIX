package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;

public interface ExerciseDataService extends WorkshopObjectService
{
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID );

	public List< ExerciseDataImpl > findAllExerciseData();

	public ExerciseDataImpl findExerciseDataByID( String id );

	public void removeExerciseDataByID( String id );
}
