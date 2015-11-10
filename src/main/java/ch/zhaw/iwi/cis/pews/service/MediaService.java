package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;

public interface MediaService extends WorkshopObjectService
{

	public List< MediaObject > findByType( MediaObjectType type );

	public String persistMediaObject( HttpServletRequest request );
	
}
