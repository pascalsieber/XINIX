package ch.zhaw.sml.iwi.cis.exwrapper.java.lang;

public class ClassNotFoundExceptionWrapper extends RuntimeException
{
	private static final long	serialVersionUID	= 1L;

	public ClassNotFoundExceptionWrapper( ClassNotFoundException e )
	{
		super( e );
	}
}
