package ch.zhaw.iwi.cis.pews.service.xinix.impl;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.dao.xinix.XinixImageMatrixDao;
import ch.zhaw.iwi.cis.pews.dao.xinix.impl.XinixImageMatrixDaoImpl;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Scope;
import ch.zhaw.iwi.cis.pews.framework.ManagedObject.Transactionality;
import ch.zhaw.iwi.cis.pews.framework.ZhawEngine;
import ch.zhaw.iwi.cis.pews.model.media.MediaObject;
import ch.zhaw.iwi.cis.pews.model.media.dto.MediaObjectSimpleView;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pews.model.xinix.dto.XinixImageMatrixSimpleView;
import ch.zhaw.iwi.cis.pews.service.MediaService;
import ch.zhaw.iwi.cis.pews.service.impl.MediaServiceImpl;
import ch.zhaw.iwi.cis.pews.service.impl.WorkshopObjectServiceImpl;
import ch.zhaw.iwi.cis.pews.service.xinix.XinixImageMatrixService;

@ManagedObject( scope = Scope.THREAD, entityManager = "pews", transactionality = Transactionality.TRANSACTIONAL )
public class XinixImageMatrixServiceImpl extends WorkshopObjectServiceImpl implements XinixImageMatrixService
{
	private XinixImageMatrixDao xinixImageMatrixDao;
	private MediaService mediaService;

	public XinixImageMatrixServiceImpl()
	{
		xinixImageMatrixDao = ZhawEngine.getManagedObjectRegistry().getManagedObject( XinixImageMatrixDaoImpl.class.getSimpleName() );
		mediaService = ZhawEngine.getManagedObjectRegistry().getManagedObject( MediaServiceImpl.class.getSimpleName() );
	}

	@Override
	protected WorkshopObjectDao getWorkshopObjectDao()
	{
		return xinixImageMatrixDao;
	}

	@Override
	public XinixImageMatrix findXinixImageMatrixByID( String id )
	{
		return xinixImageMatrixDao.findXinixImageMatrixByID( id );
	}

	@Override
	public List<XinixImageMatrixSimpleView> findAllXinixImageMatrices()
	{
		List< XinixImageMatrixSimpleView > results = new ArrayList< XinixImageMatrixSimpleView >();
		for ( XinixImageMatrix matrix : xinixImageMatrixDao.findAllXinixImageMatrices() )
		{
			List< MediaObjectSimpleView > images = new ArrayList< MediaObjectSimpleView >();
			for ( MediaObject mediaObject : matrix.getXinixImages() )
			{
				images.add( new MediaObjectSimpleView( mediaObject.getID(), mediaObject.getMediaObjectType(), mediaObject.getUrl() ) );
			}
			results.add( new XinixImageMatrixSimpleView( matrix.getID(), images ));
		}
		
		return results;
	}

	@Override
	public String persistImageMatrix( XinixImageMatrix obj )
	{
		List< MediaObject > fetchedImages = new ArrayList< MediaObject >();
		for ( MediaObject image : obj.getXinixImages() )
		{
			fetchedImages.add( (MediaObject)mediaService.findByID( image.getID() ) );
		}

		obj.setXinixImages( fetchedImages );

		return persist( obj );
	}

}
