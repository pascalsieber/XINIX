package ch.zhaw.iwi.cis.pews.util.comparator;

import java.util.Comparator;

import ch.zhaw.iwi.cis.pews.model.instance.ExerciseImpl;

public class ExerciseImplComparator implements Comparator< ExerciseImpl >
{
	@Override
	public int compare( ExerciseImpl e1, ExerciseImpl e2 )
	{
		return ( e1.getOrderInWorkshop() < e2.getOrderInWorkshop() ? -1 : ( e1.getOrderInWorkshop() == e2.getOrderInWorkshop() ? 0 : 1 ) );
	}
}
