package ch.zhaw.iwi.cis.pews.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import ch.zhaw.iwi.cis.pews.framework.CustomObjectIDResolver;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonTypeInfo( use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "class" )
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", resolver = CustomObjectIDResolver.class )
@MappedSuperclass
public abstract class IdentifiableObject implements Serializable
{
	// TODO replace 1L with singleton constant.
	private static final long serialVersionUID = 1L;

	// TODO replace int with special UID class.
	@Id
	private String id;

	public IdentifiableObject()
	{
		this.id = UUID.randomUUID().toString();
	}

	public String getID()
	{
		return id;
	}

	public void setID( String id )
	{
		this.id = id;
	}

	@Override
	public final int hashCode()
	{
		return id.hashCode();
	}

	@Override
	public final boolean equals( Object obj )
	{
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		IdentifiableObject other = (IdentifiableObject)obj;
		if ( id != other.id )
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "IdentifiableObject [id=" + id + "]";
	}
}
