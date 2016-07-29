package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;

public interface WorkshopTemplateService extends WorkshopObjectService
{

	WorkshopTemplate findWorkshopTemplateByID( String id );

	List< WorkshopTemplate > findAllWorkshopTemplates();

	void updateOrderOfExerciseTemplates( WorkshopTemplate wrapper );

}
