package ch.zhaw.sml.iwi.cis.exwrapper.java.lang;

public class ExceptionInInitializerErrorWrapper extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public ExceptionInInitializerErrorWrapper( ExceptionInInitializerError e )
	{
		super( e );
	}
}
