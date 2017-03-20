package ch.zhaw.iwi.cis.pews.service.exercise.data.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.P2POneDataDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.data.export.P2POneDataViewObject;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseDataServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneKeyword;

import javax.inject.Inject;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class P2POneExerciseDataService extends ExerciseDataServiceImpl
{
	private ExerciseDataDao specificDataDao;

	public P2POneExerciseDataService()
	{
	    super();
		specificDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( P2POneDataDao.class.getSimpleName() );
	}

	@Override
	public String persistExerciseData( ExerciseDataImpl obj )
	{
		return super.persist( obj );
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseID( String exerciseID )
	{
		return specificDataDao.findByExerciseID( exerciseID );
	}

	@Override
	public List< ExerciseDataImpl > findByExerciseIDs( List< String > exerciseIDs )
	{
		return specificDataDao.findByExerciseIDs( exerciseIDs );
	}

	@Override
	public ExerciseDataImpl findExerciseDataByID( String id )
	{
		return specificDataDao.findDataByID( id );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< P2POneDataViewObject > getExportableDataByExerciseID( ExerciseImpl exercise )
	{
		List< P2POneDataViewObject > export = new ArrayList< P2POneDataViewObject >();

		List< ExerciseDataImpl > data = this.findByExerciseID( exercise.getID() );
		for ( ExerciseDataImpl d : data )
		{
			P2POneData obj = (P2POneData)d;
			for ( P2POneKeyword keyword : obj.getKeywords() )
			{
				export.add( new P2POneDataViewObject( obj.getID(), exercise.getWorkshop().getID(), exercise.getWorkshop().getName(), exercise.getID(), exercise.getName(), exercise.getQuestion(), obj
					.getOwner()
					.getID(), ( (UserImpl)obj.getOwner() ).getLoginName(), keyword.getID(), keyword.getKeyword() ) );
			}
		}

		return export;
	}
}
