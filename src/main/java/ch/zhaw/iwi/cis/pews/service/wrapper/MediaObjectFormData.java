package ch.zhaw.iwi.cis.pews.service.wrapper;

import java.io.File;

/**
 * Created by fueg on 08.09.2016.
 */
public class MediaObjectFormData
{
	private String type;
	private File   file;

	public MediaObjectFormData()
	{
	}

	public MediaObjectFormData( String type, File file )
	{
		this.type = type;
		this.file = file;
	}

	public String getType()
	{
		return type;
	}

	public void setType( String type )
	{
		this.type = type;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile( File file )
	{
		this.file = file;
	}
}
