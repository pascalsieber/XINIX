package ch.zhaw.iwi.cis.pews.model.instance;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;

@Entity
public class XinixImageMatrix extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ManyToMany
	private List< XinixImage > xinixImages;

	public XinixImageMatrix()
	{
		super();
		this.xinixImages = new ArrayList<>();
	}

	public XinixImageMatrix( List< XinixImage > xinixImages )
	{
		super();
		this.xinixImages = xinixImages;
	}

	public List< XinixImage > getXinixImages()
	{
		return xinixImages;
	}

	public void setXinixImages( List< XinixImage > xinixImages )
	{
		this.xinixImages = xinixImages;
	}

}
