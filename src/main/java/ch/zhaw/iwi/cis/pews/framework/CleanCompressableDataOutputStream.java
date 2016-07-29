package ch.zhaw.iwi.cis.pews.framework;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import ch.zhaw.iwi.cis.pews.model.Client;
import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;
import ch.zhaw.iwi.cis.pews.model.user.UserImpl;

public class CleanCompressableDataOutputStream extends ObjectOutputStream
{

	public CleanCompressableDataOutputStream( OutputStream out ) throws IOException
	{
		super( out );
		enableReplaceObject( true );
	}

	@Override
	protected Object replaceObject( Object sourceObject ) throws IOException
	{
		if ( sourceObject instanceof UserImpl )
		{
			return null;
		}
		else if ( sourceObject instanceof Client )
		{
			return null;
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

}
