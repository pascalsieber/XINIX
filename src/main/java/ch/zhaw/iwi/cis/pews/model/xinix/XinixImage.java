package ch.zhaw.iwi.cis.pews.model.xinix;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;

@Entity
public class XinixImage extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String url;

	public XinixImage()
	{
		super();
	}

	public XinixImage( String url )
	{
		super();
		this.url = url;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl( String url )
	{
		this.url = url;
	}

}
