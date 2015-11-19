package ch.zhaw.iwi.cis.pews.service.xinix;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;
import ch.zhaw.iwi.cis.pews.service.WorkshopObjectService;

public interface XinixImageMatrixService extends WorkshopObjectService
{

	public XinixImageMatrix findXinixImageMatrixByID( String id );

	public List< XinixImageMatrix > findAllXinixImageMatrices();

	public String persistImageMatrix( XinixImageMatrix obj );

}
