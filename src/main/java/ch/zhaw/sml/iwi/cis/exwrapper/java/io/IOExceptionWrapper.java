package ch.zhaw.sml.iwi.cis.exwrapper.java.io;

import java.io.IOException;

public class IOExceptionWrapper extends RuntimeException
{
	private static final long	serialVersionUID	= 1L;

	public IOExceptionWrapper( IOException e )
	{
		super( e );
	}
}
