package ch.zhaw.iwi.cis.pews.model.media;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.WorkshopObject;

@Entity
public class MediaObject extends WorkshopObject
{
	@Transient
	private static final long serialVersionUID = 1L;
	private Date date;
	private String url;
	private String fileName;
	private String filePath;

	@Enumerated( EnumType.STRING )
	private MediaObjectType type;

	public MediaObject()
	{
		super();
	}

	public MediaObject( Date date, String url, String fileName, String filePath, MediaObjectType type )
	{
		super();
		this.date = date;
		this.url = url;
		this.fileName = fileName;
		this.filePath = filePath;
		this.type = type;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate( Date date )
	{
		this.date = date;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl( String url )
	{
		this.url = url;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName( String fileName )
	{
		this.fileName = fileName;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath( String filePath )
	{
		this.filePath = filePath;
	}

	public MediaObjectType getType()
	{
		return type;
	}

	public void setType( MediaObjectType type )
	{
		this.type = type;
	}

}
