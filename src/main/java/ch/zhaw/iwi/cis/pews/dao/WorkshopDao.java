package ch.zhaw.iwi.cis.pews.dao;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.instance.WorkshopImpl;

public interface WorkshopDao extends WorkshopObjectDao
{

	public List< WorkshopImpl > findByAllSimple( String clientID );

	public WorkshopImpl findWorkshopByID( String id );

	public WorkshopImpl findWorkshopByIDForBasicUpdate( String id );

}
