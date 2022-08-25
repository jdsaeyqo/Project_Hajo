package com.ssafy.hibernate.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@DynamicUpdate
@Table(name = "PLAN_SMALL_TB")
@Data
public class PlanSmall {

   //pk, 소플랜 seq
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="SMALLPLAN_SEQ")
   private Long smallplanSeq;

   @ManyToOne
   @JoinColumn(name="MIDPLAN_SEQ", referencedColumnName="MIDPLAN_SEQ")
   private PlanMid planmid;

   //소플랜 날짜
   @Column(name="SMALLPLAN_DATE")
   @Temporal(TemporalType.DATE)
   private Calendar smallplanDate;

   //소플랜 달성여부
   @Column(name="SMALLPLAN_ISDONE")
   private Boolean smallplanIsdone;

   //소플랜 안의 태스크 수
   @Column(name="SMALLPLAN_TTTASK")
   private Integer smallplanTttask;

   //중플랜 안의 달성된 소플랜 수
   @Column(name="SMALLPLAN_TDTASK")
   private Integer smallplanTdtask;

}