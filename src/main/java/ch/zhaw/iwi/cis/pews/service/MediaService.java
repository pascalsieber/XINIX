package ch.zhaw.iwi.cis.pews.service;

import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

public interface MediaService extends WorkshopObjectService
{

	public List<MediaObject> findByType( MediaObjectType type );

	/**
	 * method used for testing
	 */
	public String persistMediaObjectFormData( File file, MediaObjectType mediaObjectType, String username,
			String password );

	public String persistMediaObject( HttpServletRequest request );
}
