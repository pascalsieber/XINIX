package ch.zhaw.iwi.cis.pews.dao.xinix;

import java.util.List;

import ch.zhaw.iwi.cis.pews.dao.WorkshopObjectDao;
import ch.zhaw.iwi.cis.pews.model.xinix.XinixImageMatrix;

public interface XinixImageMatrixDao extends WorkshopObjectDao
{
	public XinixImageMatrix findXinixImageMatrixByID( String id );

	public List< XinixImageMatrix > findAllXinixImageMatrices();
}
