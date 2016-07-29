package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;

public interface MediaDao extends WorkshopObjectDao
{

	public List< MediaObject > findByType( MediaObjectType type );

}
