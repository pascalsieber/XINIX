package ch.zhaw.iwi.cis.pews.model.media.dto;

import ch.zhaw.iwi.cis.pews.model.dto.IdentifiableObjectSimpleView;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;

public class MediaObjectSimpleView extends IdentifiableObjectSimpleView
{
	private MediaObjectType mediaObjectType;
	private String url;

	public MediaObjectSimpleView()
	{
		super();
	}

	public MediaObjectSimpleView( String id, MediaObjectType mediaObjectType, String url )
	{
		super( id );
		this.mediaObjectType = mediaObjectType;
		this.url = url;
	}

	public MediaObjectType getMediaObjectType()
	{
		return mediaObjectType;
	}

	public void setMediaObjectType( MediaObjectType mediaObjectType )
	{
		this.mediaObjectType = mediaObjectType;
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
