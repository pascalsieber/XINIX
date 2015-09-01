package ch.zhaw.iwi.cis.pews.dao;

import ch.zhaw.iwi.cis.pews.model.template.WorkshopTemplate;

public interface WorkshopDefinitionDao extends WorkshopObjectDao
{

	WorkshopTemplate findByIDWithExerciseDefinitions( String id );

}
