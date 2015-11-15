package ch.zhaw.iwi.cis.pews.model.xinix;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;

@Entity
public class XinixImageMatrix extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;

	@ManyToMany
	private List< MediaObject > xinixImages;

	public XinixImageMatrix()
	{
		super();
		this.xinixImages = new ArrayList<>();
	}

	public XinixImageMatrix( List< MediaObject > xinixImages )
	{
		super();
		this.xinixImages = xinixImages;
	}

	public List< MediaObject > getXinixImages()
	{
		return xinixImages;
	}

	public void setXinixImages( List< MediaObject > xinixImages )
	{
		this.xinixImages = xinixImages;
	}
}
