package ch.zhaw.iwi.cis.pews.model.media;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.PewsConfig;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.service.rest.MediaRestService;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MediaObject extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;
	private String mimeType;

	@JsonIgnore
	private byte[] blob;

	@Enumerated( EnumType.STRING )
	private MediaObjectType mediaObjectType;

	public MediaObject()
	{
		super();
	}

	public MediaObject( String mimeType, byte[] blob, MediaObjectType mediaObjectType )
	{
		super();
		this.mimeType = mimeType;
		this.blob = blob;
		this.mediaObjectType = mediaObjectType;
	}

	public String getMimeType()
	{
		return mimeType;
	}

	public void setMimeType( String mimeType )
	{
		this.mimeType = mimeType;
	}

	public byte[] getBlob()
	{
		return blob;
	}

	public void setBlob( byte[] blob )
	{
		this.blob = blob;
	}

	public MediaObjectType getMediaObjectType()
	{
		return mediaObjectType;
	}

	public void setMediaObjectType( MediaObjectType mediaObjectType )
	{
		this.mediaObjectType = mediaObjectType;
	}

	/**
	 * provides dynamic url to service endpoint providing object's blob
	 * 
	 * @return url to retrieve object's blob
	 */
	public String getUrl()
	{
		return PewsConfig.getServiceUrl() + MediaRestService.BASE + MediaRestService.GET_CONTENT_BY_ID + "/" + this.getID() + "." + mimeType.substring( mimeType.indexOf( "/" ) + 1 );
	}
}
