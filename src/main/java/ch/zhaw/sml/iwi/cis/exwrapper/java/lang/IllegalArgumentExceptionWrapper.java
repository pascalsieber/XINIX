package ch.zhaw.sml.iwi.cis.exwrapper.java.lang;

public class IllegalArgumentExceptionWrapper extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public IllegalArgumentExceptionWrapper( IllegalArgumentException e )
	{
		super( e );
	}
}
