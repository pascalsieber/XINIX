package ch.zhaw.sml.iwi.cis.exwrapper.java.lang;

public class IllegalAccessExceptionWrapper extends RuntimeException
{
	private static final long	serialVersionUID	= 1L;

	public IllegalAccessExceptionWrapper( IllegalAccessException e )
	{
		super( e );
	}
}
