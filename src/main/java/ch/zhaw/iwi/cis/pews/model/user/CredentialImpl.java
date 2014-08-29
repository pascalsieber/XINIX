package ch.zhaw.iwi.cis.pews.model.user;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;

@MappedSuperclass
public class CredentialImpl extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	public CredentialImpl()
	{
		super();
	}

}
