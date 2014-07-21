package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import ch.zhaw.iwi.cis.pews.service.ExerciseDefinitionService;
import ch.zhaw.iwi.cis.pews.service.rest.ExerciseDefinitionRestService;

public class ExerciseDefinitionServiceProxy extends IdentifiableObjectServiceProxy implements ExerciseDefinitionService
{

	protected ExerciseDefinitionServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, ExerciseDefinitionRestService.BASE );
	}

}
