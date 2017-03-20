package ch.zhaw.iwi.cis.pews.service;

import java.util.List;

import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface WorkshopTemplateService extends WorkshopObjectService
{

	WorkshopTemplate findWorkshopTemplateByID( String id );

	List< WorkshopTemplate > findAllWorkshopTemplates();

	void updateOrderOfExerciseTemplates( WorkshopTemplate wrapper );

}
