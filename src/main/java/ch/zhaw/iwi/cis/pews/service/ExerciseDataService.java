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

<<<<<<< HEAD
	public String exportByExerciseID( String exerciseID );

	public String exportByWorkshopID( String workshopID );
=======
	public < T extends ExerciseDataViewObject > List< T > exportByExerciseID( String exerciseID );
>>>>>>> 60a1ae12c556ff6b268195e89ed0a187000131a4

	public < T extends ExerciseDataViewObject > List< T > getExportableDataByExerciseID( ExerciseImpl exercise );
}
