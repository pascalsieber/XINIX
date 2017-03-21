package ch.zhaw.iwi.cis.pews.dao.impl;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.MediaDao;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.model.WorkshopObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.MediaObjectType;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class MediaDaoImpl extends WorkshopObjectDaoImpl implements MediaDao
{

	@Override
	protected Class< ? extends WorkshopObject > getWorkshopObjectClass()
	{
		return MediaObject.class;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public List< MediaObject > findByType( MediaObjectType type )
	{
		return em.createQuery( "from MediaObject m where m.mediaObjectType = :_type" ).setParameter( "_type", type ).getResultList();
	}

}
