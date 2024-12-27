package com.insurence.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer planCategoryId;

	private String categoryName;

	private String activeSwitch;

	private String createdBy;

	private String updatedBy;

	@Column(name = "CREATEDDATE", updatable = false)
	@CreationTimestamp
	private LocalDate createdDate;

	@Column(name = "UPDATEDDATE", insertable = false)
	@CreationTimestamp
	private LocalDate updatedDate;

	public Integer getPlanCategoryId() {
		return planCategoryId;
	}

	public void setPlanCategoryId(Integer planCategoryId) {
		this.planCategoryId = planCategoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getActiveSwitch() {
		return activeSwitch;
	}

	public void setActiveSwitch(String activeSwitch) {
		this.activeSwitch = activeSwitch;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	



}
