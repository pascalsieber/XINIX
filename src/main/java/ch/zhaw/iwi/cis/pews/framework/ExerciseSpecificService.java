package ch.zhaw.iwi.cis.pews.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExerciseSpecificService
{
	public Class< ? extends ExerciseDefinitionImpl> exerciseDefinition();
}
