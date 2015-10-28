package ch.zhaw.iwi.cis.pews.util.comparator;

import java.util.Comparator;

import ch.zhaw.iwi.cis.pews.model.template.ExerciseTemplate;

public class ExerciseTemplateComparator implements Comparator< ExerciseTemplate >
{

	@Override
	public int compare( ExerciseTemplate e1, ExerciseTemplate e2 )
	{
		return ( e1.getOrderInWorkshopTemplate() < e2.getOrderInWorkshopTemplate() ? -1 : ( e1.getOrderInWorkshopTemplate() == e2.getOrderInWorkshopTemplate() ? 0 : 1 ) );
	}

}
