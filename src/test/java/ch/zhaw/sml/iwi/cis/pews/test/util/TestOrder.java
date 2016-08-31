package ch.zhaw.sml.iwi.cis.pews.test.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fueg on 30.08.2016.
 */
@Retention( RetentionPolicy.RUNTIME ) @Target( { ElementType.METHOD } ) public @interface TestOrder
{
	public int order();
}
