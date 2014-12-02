package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.zhaw.iwi.cis.pews.dao.ExerciseDataDao;
import ch.zhaw.iwi.cis.pews.dao.P2POneKeywordDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.P2POneDataDao;
import ch.zhaw.iwi.cis.pews.dao.data.impl.P2POneKeywordDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.data.ExerciseDataImpl;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.P2PTwoInput;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.output.P2PTwoOutput;
import ch.zhaw.iwi.cis.pews.model.wrappers.OutputRequest;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2POneKeyword;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.P2PTwoData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.definition.P2PTwoDefinition;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseDefinition = P2PTwoDefinition.class )
public class P2PTwoExerciseService extends ExerciseServiceImpl
{

	private ExerciseDataDao p2pOneDataDao;
	private P2POneKeywordDao p2pOneKeywordDao;
	
	public P2PTwoExerciseService()
	{
		super();
		this.p2pOneDataDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( P2POneDataDao.class.getSimpleName() );
		this.p2pOneKeywordDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( P2POneKeywordDaoImpl.class.getSimpleName() );
	}

	@Override
	public Input getInput()
	{
		P2PTwoDefinition definition = (P2PTwoDefinition)UserContext.getCurrentUser().getSession().getCurrentExercise().getDefinition();
		List< String > keywords = new ArrayList<>();

		List< ExerciseDataImpl > cascadeOneData = p2pOneDataDao.findByWorkshopAndExerciseDataClass( P2POneData.class );

		for ( ExerciseDataImpl data : cascadeOneData )
		{
			for ( P2POneKeyword keywordObject : ( (P2POneData)data ).getKeywords() )
			{
				keywords.add( keywordObject.getKeyword() );
			}
		}

		return new P2PTwoInput( definition.getQuestion(), keywords );
	}
	
	

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		P2PTwoDefinition definition = (P2PTwoDefinition)( (WorkflowElementImpl)findByID( exerciseID ) ).getDefinition();
		List< String > keywords = new ArrayList<>();

		List< ExerciseDataImpl > cascadeOneData = p2pOneDataDao.findByWorkshopAndExerciseDataClass( P2POneData.class );

		for ( ExerciseDataImpl data : cascadeOneData )
		{
			for ( P2POneKeyword keywordObject : ( (P2POneData)data ).getKeywords() )
			{
				keywords.add( keywordObject.getKeyword() );
			}
		}

		return new P2PTwoInput( definition.getQuestion(), keywords );
	}

	@Override
	public void setOutput( String output )
	{
		try
		{
			P2PTwoOutput finalOutput = getObjectMapper().readValue( output, P2PTwoOutput.class );

			Set< P2POneKeyword > chosenP2POneKeywords = new HashSet<>();

			for ( String keyword : finalOutput.getChosenKeywords() )
			{
				chosenP2POneKeywords.add( (P2POneKeyword)p2pOneKeywordDao.findByKeywordString( keyword ) );
			}

			getExerciseDataDao()
				.persist( new P2PTwoData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), finalOutput.getAnswers(), chosenP2POneKeywords ) );

		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + P2PTwoOutput.class.getSimpleName() );
		}
	}

	@Override
	public void setOuputByExerciseID( OutputRequest outputRequest )
	{
		try
		{
			P2PTwoOutput finalOutput = getObjectMapper().readValue( outputRequest.getOutput(), P2PTwoOutput.class );

			Set< P2POneKeyword > chosenP2POneKeywords = new HashSet<>();

			for ( String keyword : finalOutput.getChosenKeywords() )
			{
				chosenP2POneKeywords.add( (P2POneKeyword)p2pOneKeywordDao.findByKeywordString( keyword ) );
			}

			getExerciseDataDao()
				.persist( new P2PTwoData( UserContext.getCurrentUser(), (WorkflowElementImpl)findByID( outputRequest.getExerciseID() ), finalOutput.getAnswers(), chosenP2POneKeywords ) );

		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + P2PTwoOutput.class.getSimpleName() );
		}
	}
}
