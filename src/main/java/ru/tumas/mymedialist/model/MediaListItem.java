/* 
 * Copyright (C) 2014 Maxim Tumas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ru.tumas.mymedialist.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Items")
@NamedQueries({
	@NamedQuery(name = "MediaListItem.getAll", query = "SELECT i FROM MediaListItem i"),
	@NamedQuery(name = "MediaListItem.getByStatus", query = "SELECT i FROM MediaListItem i WHERE i.status = :status"),
	@NamedQuery(name = "MediaListItem.getByType", query = "SELECT i FROM MediaListItem i WHERE i.type = :type"),
	@NamedQuery(name = "MediaListItem.getByTypeAndStatus", query = "SELECT i FROM MediaListItem i WHERE i.type = :type"
			+ " AND i.status = :status")
})
public class MediaListItem implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	private String nameEng;
	private String nameRus;
	private String country;
	private int episodes;
	private int progress;
	private int creationYear;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	private MediaType type;
	private MediaStatus status;

	public String getId() {
		return id;
	}

	public String getNameEng() {
		return nameEng;
	}

	public void setNameEng(String nameEng) {
		this.nameEng = nameEng;
	}

	public String getNameRus() {
		return nameRus;
	}

	public void setNameRus(String nameRus) {
		this.nameRus = nameRus;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getEpisodes() {
		return episodes;
	}

	public void setEpisodes(int episodes) {
		this.episodes = episodes;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getCreationYear() {
		return creationYear;
	}

	public void setCreationYear(int creationYear) {
		this.creationYear = creationYear;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public MediaType getType() {
		return type;
	}

	public void setType(MediaType type) {
		this.type = type;
	}

	public MediaStatus getStatus() {
		return status;
	}

	public void setStatus(MediaStatus status) {
		this.status = status;
	}
	
	

//	@Override
//	public int hashCode() {
//		int hash = 7;
//		hash = 31 * hash + Objects.hashCode(this.id);
//		return hash;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (obj == null) {
//			return false;
//		}
//		if (getClass() != obj.getClass()) {
//			return false;
//		}
//		final MediaListItem other = (MediaListItem) obj;
//		if (!Objects.equals(this.id, other.id)) {
//			return false;
//		}
//		return true;
//	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 19 * hash + Objects.hashCode(this.id);
		hash = 19 * hash + Objects.hashCode(this.nameEng);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MediaListItem other = (MediaListItem) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		if (!Objects.equals(this.nameEng, other.nameEng)) {
			return false;
		}
		return true;
	}
}
