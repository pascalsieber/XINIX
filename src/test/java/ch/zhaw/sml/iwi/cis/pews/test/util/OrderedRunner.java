package ch.zhaw.sml.iwi.cis.pews.test.util;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by fueg on 30.08.2016.
 */
public class OrderedRunner extends BlockJUnit4ClassRunner
{
	public OrderedRunner( Class<?> clazz ) throws InitializationError
	{
		super( clazz );
	}

	@Override protected List<FrameworkMethod> computeTestMethods()
	{
		List<FrameworkMethod> list = super.computeTestMethods();
		List<FrameworkMethod> copy = new ArrayList<FrameworkMethod>( list );
		Collections.sort( copy, new Comparator<FrameworkMethod>()
		{
			@Override public int compare( FrameworkMethod f1, FrameworkMethod f2 )
			{
				TestOrder o1 = f1.getAnnotation( TestOrder.class );
				TestOrder o2 = f2.getAnnotation( TestOrder.class );

				if ( o1 == null || o2 == null )
					return -1;

				return o1.order() - o2.order();
			}
		} );
		return copy;
	}
}
