package com.vdi.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="ticket_exception")
public class TicketException {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id", updatable=false,nullable=false)
	private Long id;
	
	private String ref;
	private String type;
	
	@Column(length=4000)
	private String reason;
	
	@Column(name="created_dt")
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name="updated_dt")
	@UpdateTimestamp
	private LocalDateTime updatedDate;
	
	@Version
	private int version;

	public String getRef() {
		return ref;
	}

	public String getType() {
		return type;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
