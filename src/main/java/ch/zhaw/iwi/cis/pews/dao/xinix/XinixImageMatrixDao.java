package ch.zhaw.iwi.cis.pews.dao.xinix;

import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;

public interface XinixImageMatrixDao extends WorkshopObjectDao
{
	public XinixImageMatrix findXinixImageMatrixByID( String id );
}
