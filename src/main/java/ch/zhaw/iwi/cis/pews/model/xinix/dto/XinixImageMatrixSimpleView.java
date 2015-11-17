package ch.zhaw.iwi.cis.pews.model.xinix.dto;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.dto.IdentifiableObjectSimpleView;
import ch.zhaw.iwi.cis.pews.model.media.dto.MediaObjectSimpleView;

public class XinixImageMatrixSimpleView extends IdentifiableObjectSimpleView
{
	private List< MediaObjectSimpleView > xinixImages;

	public XinixImageMatrixSimpleView()
	{
		super();
	}

	public XinixImageMatrixSimpleView( String id, List< MediaObjectSimpleView > xinixImages )
	{
		super( id );
		this.xinixImages = xinixImages;
	}

	public List< MediaObjectSimpleView > getXinixImages()
	{
		return xinixImages;
	}

	public void setXinixImages( List< MediaObjectSimpleView > xinixImages )
	{
		this.xinixImages = xinixImages;
	}
}
