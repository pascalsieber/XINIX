package ch.zhaw.iwi.cis.pinkelefant.exercise.definition;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class CompressionDefinition extends ExerciseDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	public CompressionDefinition()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public CompressionDefinition( PrincipalImpl owner, TimeUnit timeUnit, int duration )
	{
		super( owner, timeUnit, duration );
		// TODO Auto-generated constructor stub
	}
	
}
