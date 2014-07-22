package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;

public interface ExerciseDataService extends IdentifiableObjectService
{

	public List< ExerciseDataImpl > findByExerciseID( int exerciseID );

}
