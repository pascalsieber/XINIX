package ch.zhaw.iwi.cis.pews.service.exercise.data.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.WorkshopDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.export.SimplePrototypingDataViewObject;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.MediaService;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.MediaServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.SimplePrototypingData;

import javax.inject.Inject;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class SimplePrototypingExerciseDataService extends ExerciseDataServiceImpl
{
	private MediaService mediaService;

	public SimplePrototypingExerciseDataService()
	{
	    super();
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
	public List< ExerciseDataImpl > findByExerciseIDs( List< String > exerciseIDs )
	{
		return super.genericFindByExerciseIDs( exerciseIDs );
	}

	@Override
	public ExerciseDataImpl findExerciseDataByID( String id )
	{
		return super.genericFindDataByID( id );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< SimplePrototypingDataViewObject > getExportableDataByExerciseID( ExerciseImpl exercise )
	{
		List< SimplePrototypingDataViewObject > export = new ArrayList< SimplePrototypingDataViewObject >();

		List< ExerciseDataImpl > data = this.findByExerciseID( exercise.getID() );
		for ( ExerciseDataImpl d : data )
		{
			SimplePrototypingData obj = (SimplePrototypingData)d;
			export.add( new SimplePrototypingDataViewObject( obj.getID(), exercise.getWorkshop().getID(), exercise.getWorkshop().getName(), exercise.getID(), exercise.getName(), exercise
				.getQuestion(), obj.getOwner().getID(), ( (UserImpl)obj.getOwner() ).getLoginName(), obj.getMediaObject().getUrl() ) );
		}

		return export;
	}
}
