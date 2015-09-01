package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;

public interface WorkshopDefinitionService extends WorkshopObjectService
{

	WorkshopTemplate findWorkshopDefinitionByID( String id );

	List< WorkshopTemplate > findAllWorkshopDefinitions();

}
