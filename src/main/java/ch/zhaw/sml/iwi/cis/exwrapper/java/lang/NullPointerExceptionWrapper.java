package ch.zhaw.sml.iwi.cis.exwrapper.java.lang;

public class NullPointerExceptionWrapper extends RuntimeException
{
	private static final long	serialVersionUID	= 1L;

	public NullPointerExceptionWrapper( NullPointerException e )
	{
		super( e );
	}
}
