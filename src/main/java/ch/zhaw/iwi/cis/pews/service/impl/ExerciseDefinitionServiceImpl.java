package ch.zhaw.iwi.cis.pews.service.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDefinitionDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopDefinitionDao;
import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.impl.ExerciseDefinitionDaoImpl;
import ch.zhaw.iwi.cis.pews.dao.impl.WorkshopDefinitionDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;
import ch.zhaw.iwi.cis.pews.service.ExerciseDefinitionService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class ExerciseDefinitionServiceImpl extends WorkshopObjectServiceImpl implements ExerciseDefinitionService
{
	private ExerciseDefinitionDao exerciseDefinitionDao;
	private WorkshopDefinitionDao workshopDefinitionDao;

	public ExerciseDefinitionServiceImpl()
	{
		exerciseDefinitionDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( ExerciseDefinitionDaoImpl.class.getSimpleName() );
		workshopDefinitionDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( WorkshopDefinitionDaoImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return exerciseDefinitionDao;
	}

	@Override
	public ExerciseDefinitionImpl findExerciseDefinitionByID( String id )
	{
		return (ExerciseDefinitionImpl)simplifyOwnerInObjectGraph( findByID( id ) );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< ExerciseDefinitionImpl > findAllExerciseDefinitions()
	{
		return (List< ExerciseDefinitionImpl >)simplifyOwnerInObjectGraph( findAll() );
	}

	@Override
	public void removeExerciseDefinition( ExerciseDefinitionImpl obj )
	{
		ExerciseDefinitionImpl exDef = exerciseDefinitionDao.findById( obj.getID() );
		WorkshopDefinitionImpl wsDef = workshopDefinitionDao.findById( exDef.getWorkshopDefinition().getID() );

		wsDef.getExerciseDefinitions().remove( exDef );

		workshopDefinitionDao.persist( wsDef );
		exerciseDefinitionDao.remove( exDef );

	}
}
