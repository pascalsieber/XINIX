package ch.zhaw.sml.iwi.cis.exwrapper.java.net;

import java.net.UnknownHostException;

public class UnknownHostExceptionWrapper extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public UnknownHostExceptionWrapper( UnknownHostException e )
	{
		super( e );
	}
}
