package ch.zhaw.iwi.cis.pews.service.exercise.data.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.export.ExerciseDataViewObject;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.service.MediaService;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.MediaServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.SimplePrototypingData;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class SimplePrototypingExerciseDataService extends ExerciseDataServiceImpl
{
	private MediaService mediaService;

	public SimplePrototypingExerciseDataService()
	{
		mediaService = ZhawEngine.getManagedObjectRegistry().getManagedObject( MediaServiceImpl.class.getSimpleName() );
	}

	@Override
	public String persistExerciseData( ExerciseDataImpl obj )
	{
		// retrieve referenced mediaObject and set in obj
		MediaObject mediaObject = mediaService.findByID( ( (SimplePrototypingData)obj ).getMediaObject().getID() );
		( (SimplePrototypingData)obj ).setMediaObject( mediaObject );
		return super.persist( obj );
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		return super.genericFindByExerciseID( exerciseID );
	}

	@Override
	public ExerciseDataImpl findExerciseDataByID( String id )
	{
		return super.genericFindDataByID( id );
	}

	@Override
	public List< ExerciseDataViewObject > getExportableDataByExerciseID( ExerciseImpl exercise )
	{
		// TODO finish this, once output of prototyping is image with url
		return new ArrayList< ExerciseDataViewObject >();
	}
}
