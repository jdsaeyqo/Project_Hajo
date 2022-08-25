package com.ssafy.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@DynamicUpdate
@Table(name = "PLAN_TASK_TB")
@Data
public class PlanTask {

   //pk, 태스트 seq
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="TASK_SEQ")
   private Long taskSeq;

   @ManyToOne
   @JoinColumn(name="SMALLPLAN_SEQ", referencedColumnName="SMALLPLAN_SEQ")
   private PlanSmall plansmall;

   //태스크 제목
   @Column(name="TASK_TITLE")
   private String taskTitle;

   //태스크 달성여부
   @Column(name="TASK_ISDONE")
   private Boolean taskIsdone;

   //메모
   @Column(name="TASK_MEMO")
   private String taskMemo;

   //이미지
   @Column(name="TASK_IMG")
   private String taskImg;

   //알람 시간
   @Column(name="TASK_ALARMTIME")
   private String taskAlarmtime;

}