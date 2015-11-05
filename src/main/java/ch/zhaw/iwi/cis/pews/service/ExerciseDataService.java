package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.export.ExerciseDataViewObject;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;

public interface ExerciseDataService extends WorkshopObjectService
{
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID );

	public List< ExerciseDataImpl > findAllExerciseData();

	public ExerciseDataImpl findExerciseDataByID( String id );

	public void removeExerciseDataByID( String id );

	public String exportByExerciseID( String exerciseID );

	public String exportByWorkshopID( String workshopID );

	public < T extends ExerciseDataViewObject > List< T > getExportableDataByExerciseID( ExerciseImpl exercise );
}
