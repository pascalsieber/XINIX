package ch.zhaw.iwi.cis.pews.service.impl.proxy;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import ch.zhaw.iwi.cis.pews.service.rest.IdentifiableObjectRestService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;

public abstract class ServiceProxy
{
	private WebTarget serviceTarget;
	private ObjectMapper mapper;

	@SuppressWarnings( "static-access" )
	protected ServiceProxy( String hostName, int port, String userName, String password, String servicePath )
	{
		super();

		HttpAuthenticationFeature basicAuth = HttpAuthenticationFeature.basic( userName, password );
		Client client = ClientBuilder.newBuilder().newClient().register( basicAuth );
		WebTarget baseTarget = client.target( "http://" + hostName + ":" + port + IdentifiableObjectRestService.SERVICES_BASE );
		serviceTarget = baseTarget.path( servicePath );
		mapper = new ObjectMapper();
		mapper.enableDefaultTyping();
		mapper.registerModule( new GuavaModule() );
	}

	protected WebTarget getServiceTarget()
	{
		return serviceTarget;
	}

	public ObjectMapper getMapper()
	{
		return mapper;
	}

	public void setMapper( ObjectMapper mapper )
	{
		this.mapper = mapper;
	}

}
