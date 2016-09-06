package ch.zhaw.iwi.cis.pews.service;

import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MediaService extends WorkshopObjectService
{

	public List<MediaObject> findByType( MediaObjectType type );

	/**
	 * method used for testing
	 */
	public String persistJsonMediaObject( MediaObject mediaObject );

	public String persistMediaObject( HttpServletRequest request );
}
