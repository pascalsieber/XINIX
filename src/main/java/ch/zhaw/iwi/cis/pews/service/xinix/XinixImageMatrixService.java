package ch.zhaw.iwi.cis.pews.service.xinix;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pews.model.xinix.dto.XinixImageMatrixSimpleView;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;

public interface XinixImageMatrixService extends WorkshopObjectService
{
	public XinixImageMatrix findXinixImageMatrixByID( String id );

	/**
	 * returns simplified view of all XinixImageMatrices. needed in order to avoid simplification through object reference mechanism in JSON
	 * 
	 * @return List of simplified view objects
	 */
	public List< XinixImageMatrixSimpleView > findAllXinixImageMatrices();

	public String persistImageMatrix( XinixImageMatrix obj );
}
