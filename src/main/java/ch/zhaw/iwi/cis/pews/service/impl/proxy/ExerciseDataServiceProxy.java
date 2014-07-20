package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import ch.zhaw.iwi.cis.pews.service.ExerciseDataService;
import ch.zhaw.iwi.cis.pews.service.rest.ExerciseDataRestService;

public class ExerciseDataServiceProxy extends IdentifiableObjectServiceProxy implements ExerciseDataService
{

	protected ExerciseDataServiceProxy( String hostName, int port, String userName, String password )
	{
		super( hostName, port, userName, password, ExerciseDataRestService.BASE );
	}
	

}
