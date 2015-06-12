package ch.zhaw.iwi.cis.pews.dao;

import ch.zhaw.iwi.cis.pews.model.definition.WorkshopDefinitionImpl;

public interface WorkshopDefinitionDao extends WorkshopObjectDao
{

	WorkshopDefinitionImpl findByIDWithExerciseDefinitions( String id );

}
