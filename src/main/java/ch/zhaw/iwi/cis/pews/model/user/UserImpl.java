package ch.zhaw.iwi.cis.pews.model.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Base64;

import ch.zhaw.iwi.cis.pews.PewsConfig;
import ch.zhaw.iwi.cis.pews.model.instance.Participant;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserImpl extends PrincipalImpl
{
	@Transient
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String loginName;

	@ManyToMany
	private Set< GroupImpl > groups;

	public UserImpl()
	{
		super();
		this.groups = new HashSet< GroupImpl >();
	}

	public UserImpl( PasswordCredentialImpl credential, RoleImpl role, Participant participation, String firstName, String lastName, String loginName )
	{
		super( credential, role, participation );
		this.firstName = firstName;
		this.lastName = lastName;
		this.loginName = loginName;
		this.groups = new HashSet< GroupImpl >();
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName( String firstName )
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName( String lastName )
	{
		this.lastName = lastName;
	}

	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName( String loginName )
	{
		this.loginName = loginName;
	}

	// Note that the login name is assumed to be in the format: client/email,
	// i.e., zhaw/john.brush@zhaw.ch
	@JsonIgnore
	public String getLoginNameEmailPart()
	{
		return loginName.substring( loginName.indexOf( "/" ) + 1 );
	}

	@JsonIgnore
	public String getLoginNameClientPart()
	{
		int idx = loginName.indexOf( "/" );
		if ( idx == -1 )
		{
			idx = loginName.length();
		}
		return loginName.substring( 0, idx );
	}

	/**
	 * generates URL for accessing web-client, including parameters for authentication
	 * 
	 * @return URL to web-client
	 */
	@JsonIgnore
	public String getAuthenticationUrl()
	{
		String encodedLoginName = new String( Base64.encodeBase64( loginName.getBytes() ) );
		String encodedPassword = new String( Base64.encodeBase64( this.getCredential().getPassword().getBytes() ) );

		return PewsConfig.getWebClientAuthenticationUrl() + PewsConfig.getWebClientAuthenticationUserParam() + encodedLoginName + PewsConfig.getWebClientAuthenticationPasswordParam()
				+ encodedPassword + PewsConfig.getWebClientAuthenticationProfileTarget();
	}

	public Set< GroupImpl > getGroups()
	{
		return groups;
	}

	public void setGroups( Set< GroupImpl > groups )
	{
		this.groups = groups;
	}

}
