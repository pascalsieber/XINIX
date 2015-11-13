package ch.zhaw.iwi.cis.pews.service.exercise.impl;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import ch.zhaw.iwi.cis.pews.framework.ExerciseSpecificService;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.UserContext;
import ch.zhaw.iwi.cis.pews.model.input.Input;
import ch.zhaw.iwi.cis.pews.model.input.SimplyPrototypingInput;
import ch.zhaw.iwi.cis.pews.model.instance.WorkflowElementImpl;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;
import ch.zhaw.iwi.cis.pews.model.output.SimplePrototypingOutput;
import ch.zhaw.iwi.cis.pews.service.impl.ExerciseServiceImpl;
import ch.zhaw.iwi.cis.pinkelefant.exercise.data.SimplePrototypingData;
import ch.zhaw.iwi.cis.pinkelefant.exercise.instance.SimplyPrototypingExercise;
import ch.zhaw.iwi.cis.pinkelefant.exercise.template.SimplyPrototypingTemplate;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
@ExerciseSpecificService( exerciseTemplate = SimplyPrototypingTemplate.class )
public class SimplePrototypingExerciseService extends ExerciseServiceImpl
{

	public SimplePrototypingExerciseService()
	{
		super();
	}

	@Override
	public Input getInput()
	{
		return this.getInputByExerciseID( UserContext.getCurrentUser().getSession().getCurrentExercise().getID() );
	}

	@Override
	public Input getInputByExerciseID( String exerciseID )
	{
		SimplyPrototypingExercise ex = findByID( exerciseID );
		return new SimplyPrototypingInput( ex, ex.getQuestion(), ex.getMimeType() );
	}

	@Override
	public void setOutput( String output )
	{
		try
		{
			SimplePrototypingOutput finalOutput = getObjectMapper().readValue( output, SimplePrototypingOutput.class );

			getExerciseDataDao()
				.persist(
					new SimplePrototypingData( UserContext.getCurrentUser(), UserContext.getCurrentUser().getSession().getCurrentExercise(), generateMediaObjectFromBase64( finalOutput
						.getBase64String() ) ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + SimplePrototypingOutput.class.getSimpleName() );
		}
	}

	@Override
	public void setOuputByExerciseID( String outputRequestString )
	{
		try
		{
			SimplePrototypingOutput finalOutput = getObjectMapper().readValue( outputRequestString, SimplePrototypingOutput.class );
			getExerciseDataDao()
				.persist(
					new SimplePrototypingData(
						UserContext.getCurrentUser(),
						(WorkflowElementImpl)findByID( finalOutput.getExerciseID() ),
						generateMediaObjectFromBase64( finalOutput.getBase64String() ) ) );
		}
		catch ( IOException e )
		{
			throw new UnsupportedOperationException( "malformed json. Output for this exercise is of type " + SimplePrototypingOutput.class.getSimpleName() );
		}
	}

	private MediaObject generateMediaObjectFromBase64( String base64String )
	{
		// extract contentType and image information from base64String
		String contentType = base64String.substring( base64String.indexOf( "data:" ) + 5, base64String.indexOf( ";base64" ) );
		String imageBytes = base64String.substring( base64String.indexOf( "," ) + 1 );

		MediaObject mediaObject = new MediaObject( contentType, Base64.decodeBase64( imageBytes ), MediaObjectType.SIMPLYPROTOTYPING );
		mediaObject.setClient( UserContext.getCurrentUser().getClient() );
		return mediaObject;
	}

}
