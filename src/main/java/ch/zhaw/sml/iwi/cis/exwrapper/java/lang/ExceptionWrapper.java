package ch.zhaw.sml.iwi.cis.exwrapper.java.lang;

public class ExceptionWrapper extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public ExceptionWrapper( Exception e )
	{
		super( e );
	}
}
