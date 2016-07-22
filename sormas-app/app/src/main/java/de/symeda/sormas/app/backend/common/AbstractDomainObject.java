package de.symeda.sormas.app.backend.common;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import de.symeda.sormas.api.utils.DataHelper;

/**
 * TODO: Übersetzung und UUID anpassen
 * 
 * Neue Objekte sollen automatisch eine UUID erhalten. Diese soll schon vor dem Speichern über getUuid() zurückgegeben werden
 * können. Die Erzeugung von UUIDs ist relativ zeitaufwändig. Die meisten Objekte werden aus der Datenbank geladen. Daher
 * sollte für diese Objekte keine UUIDs erzeugt werden. Außerdem verträgt sich das nicht mit lazy instance loading: Die UUIDs
 * werden später nicht überschrieben. Lösung: getUuid() erstellt ggf. eine UUID & der ADO-Interceptor ruft vor dem Speichern
 * getUuid() auf. Dann kann das auch gleich mit getDate() gemacht werden.
 * 
 * @author reise
 */
@MappedSuperclass
public abstract class AbstractDomainObject implements Serializable, Cloneable, DomainObject {

	private static final long serialVersionUID = 3957437214306161226L;

	public static final String ID = "id";
	public static final String CREATION_DATE = "creationDate";
	public static final String CHANGE_DATE = "changeDate";

	@Id
	@GeneratedValue
	private Long id;

	@DatabaseField(uniqueCombo=true)
	private boolean modified;

	@DatabaseField(uniqueCombo=true, canBeNull = false, width = 29)
	private String uuid;

	@DatabaseField(dataType = DataType.DATE_LONG, canBeNull = false)
	private Date creationDate;

	@DatabaseField(dataType = DataType.DATE_LONG, canBeNull = false, version = true)
	private Date changeDate;

	@Override
	public AbstractDomainObject clone() {
		try {
			return (AbstractDomainObject) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	@Override
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public Date getCreation() {
		return getCreationDate();
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	@Override
	public Date getVersion() {
		return getChangeDate();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}

		if (o instanceof DomainObject) {
			// this works, because we are using UUIDs
			DomainObject ado = (DomainObject) o;
			return getUuid().equals(ado.getUuid());

		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return getUuid().hashCode();
	}
}
