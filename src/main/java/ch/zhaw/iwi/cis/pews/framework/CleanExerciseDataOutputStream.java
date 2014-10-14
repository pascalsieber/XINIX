package ch.zhaw.iwi.cis.pews.framework;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;

public class CleanExerciseDataOutputStream extends ObjectOutputStream
{
	public CleanExerciseDataOutputStream( OutputStream out ) throws IOException
	{
		super( out );
		enableReplaceObject( true );
	}

	@Override
	protected Object replaceObject( Object sourceObject ) throws IOException
	{
		if ( sourceObject instanceof UserImpl )
		{
			return replaceOwner( (UserImpl)sourceObject );
		}
		else if ( sourceObject instanceof ExerciseImpl )
		{
			return null;
		}
		else
		{
			return sourceObject;
		}
	}

	private UserImpl replaceOwner( UserImpl user )
	{
		UserImpl newUser = new UserImpl( null, null, null, user.getFirstName(), user.getLastName(), user.getLoginName() );
		newUser.setID( user.getID() );
		newUser.setClient( user.getClient() );

		return newUser;
	}

}
