package ch.zhaw.iwi.cis.pews.dao;

import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;

public interface WorkshopTemplateDao extends WorkshopObjectDao
{

	WorkshopTemplate findByIDWithExerciseTemplates( String id );

}
