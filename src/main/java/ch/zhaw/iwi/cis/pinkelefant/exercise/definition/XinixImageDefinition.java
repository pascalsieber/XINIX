package ch.zhaw.iwi.cis.pinkelefant.exercise.definition;

import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.definition.ExerciseDefinitionImpl;
import ch.zhaw.iwi.cis.pews.model.user.PrincipalImpl;

@Entity
public class XinixImageDefinition extends ExerciseDefinitionImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	public XinixImageDefinition()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public XinixImageDefinition( PrincipalImpl owner, TimeUnit timeUnit, int duration )
	{
		super( owner, timeUnit, duration );
		// TODO Auto-generated constructor stub
	}

}
