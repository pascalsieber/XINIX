package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.export.ExerciseDataViewObject;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.CompressableExerciseData;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ExerciseDataService extends WorkshopObjectService
{
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID );

	public List< ExerciseDataImpl > findAllExerciseData();

	public ExerciseDataImpl findExerciseDataByID( String id );

	public void removeExerciseDataByID( String id );

	public byte[] exportByExerciseID( String exerciseID );

	public List< ExerciseDataImpl > findByExerciseIDs( List< String > exerciseIDs );

	public byte[] exportByWorkshopID(String workshopID );

	public < T extends ExerciseDataViewObject > List< T > getExportableDataByExerciseID( ExerciseImpl exercise );

	/**
	 * enables delegation of persist operation to service subclass corresponding to type (i.e. class) of ExerciseDataImpl object
	 * 
	 * @param obj
	 * @return id of persisted object
	 */
	public String persistExerciseData( ExerciseDataImpl obj );

	public List< CompressableExerciseData > getCompressableExerciseDataByWorkshop( WorkshopImpl workshop );

}
