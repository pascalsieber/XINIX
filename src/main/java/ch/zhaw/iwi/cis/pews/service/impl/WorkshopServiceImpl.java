package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import ch.zhaw.iwi.cis.pews.dao.WorkshopDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.instance.SessionImpl;
import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.SessionService;
import ch.zhaw.iwi.cis.pews.service.WorkshopService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class WorkshopServiceImpl extends WorkflowElementServiceImpl implements WorkshopService
{
	private WorkshopDao workshopDao;
	private ExerciseDataService exerciseDataService;
	private SessionService sessionService;

	public WorkshopServiceImpl()
	{
		workshopDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopDaoImpl.class.getSimpleName() );
		exerciseDataService = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDataServiceImpl.class.getSimpleName() );
		sessionService = ZhawEngine.getManagedObjectRegistry().getManagedObject( SessionServiceImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return workshopDao;
	}

	// TODO: find more elegant way to remove duplicates caused by sql query than putting through HashMap
	@SuppressWarnings( "unchecked" )
	@Override
	public List< WorkshopImpl > findAllWorkshopsSimple()
	{
		List< WorkshopImpl > workshops = workshopDao.findByAllSimple( UserContext.getCurrentUser().getClient().getID() );

		( (EntityManager)ZhawEngine.getManagedObjectRegistry().getManagedObject( "pews" ) ).clear();
		for ( WorkshopImpl ws : workshops )
		{
			ws.setExercises( new ArrayList< ExerciseImpl >( new HashSet< ExerciseImpl >( ws.getExercises() ) ) );
		}

		return (List< WorkshopImpl >)simplifyOwnerInObjectGraph( workshops );
	}

	// TODO: find more elegant way to remove duplicates caused by sql query than putting through HashMap
	@Override
	public WorkshopImpl findWorkshopByID( String id )
	{
		WorkshopImpl ws = workshopDao.findWorkshopByID( id );

		( (EntityManager)ZhawEngine.getManagedObjectRegistry().getManagedObject( "pews" ) ).clear();
		ws.setExercises( new ArrayList< ExerciseImpl >( new HashSet< ExerciseImpl >( ws.getExercises() ) ) );

		return (WorkshopImpl)simplifyOwnerInObjectGraph( ws );
	}

	@Override
	public void reset( String workshopID )
	{
		WorkshopImpl ws = workshopDao.findById( workshopID );
		List< ExerciseImpl > exercises = ws.getExercises();
		Collections.reverse( exercises );
		for ( ExerciseImpl ex : exercises )
		{
			for ( ExerciseDataImpl data : exerciseDataService.findByExerciseID( ex.getID() ) )
			{
				exerciseDataService.remove( data );
			}
		}

		for ( SessionImpl session : ws.getSessions() )
		{
			session.setCurrentExercise( ws.getExercises().get( 0 ) );
			sessionService.setCurrentExercise( session );
		}
	}
}
