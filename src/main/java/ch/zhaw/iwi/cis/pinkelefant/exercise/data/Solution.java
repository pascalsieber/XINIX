package ch.zhaw.iwi.cis.pinkelefant.exercise.data;

import javax.persistence.Entity;
import javax.persistence.Transient;

import ch.zhaw.iwi.cis.pews.model.OwnableObject;

@Entity
public class Solution extends OwnableObject
{
	@Transient
	private static final long serialVersionUID = 1L;
}
