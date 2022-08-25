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
@Table(name = "PLAN_MID_TB")
@Data
public class PlanMid {

   //pk, 중플랜 seq
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="MIDPLAN_SEQ")
   private Long midplanSeq;

   @ManyToOne
   @JoinColumn(name="GRANDPLAN_SEQ", referencedColumnName="GRANDPLAN_SEQ")
   private PlanGrand plangrand;

   //중플랜 제목
   @Column(name="MIDPLAN_TITLE")
   private String midplanTitle;

   //중플랜 설명
   @Column(name="MIDPLAN_DESC")
   private String midplanDesc;

   //중플랜 시작일
   @Column(name="MIDPLAN_STARTDATE")
   @Temporal(TemporalType.DATE)
   private Calendar midplanStartdate;

   //중플랜 종료일
   @Column(name="MIDPLAN_ENDDATE")
   @Temporal(TemporalType.DATE)
   private Calendar midplanEnddate;

   //중플랜 달성여부
   @Column(name="MIDPLAN_ISDONE")
   private Boolean midplanIsdone;

   //중플랜 안의 소플랜 수
   @Column(name="MIDPLAN_TTSPLAN")
   private Integer midplanTtsplan;

   //중플랜 안의 달성된 소플랜 수
   @Column(name="MIDPLAN_TDSPLAN")
   private Integer midplanTdsplan;

   //중플랜 테마컬러
   @Column(name="MIDPLAN_COLOR")
   private String midplanColor;

}