package ch.zhaw.iwi.cis.pews.model.user;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.IdentifiableObject;

@MappedSuperclass
public class CredentialImpl extends IdentifiableObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	public CredentialImpl()
	{
		super();
		// TODO Auto-generated constructor stub
	}

}
