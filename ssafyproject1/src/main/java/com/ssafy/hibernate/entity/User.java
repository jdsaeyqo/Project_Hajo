package com.ssafy.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.ssafy.hibernate.dto.UserRankDto;

import lombok.Data;

@DynamicUpdate
@Entity
@Table(name = "USER_TB")
@Data
@NamedNativeQuery(
		name="user_rank",
		query="select USER_UID, USER_NICKNAME, USER_CPOINT, rank() over(order by USER_CPOINT desc) as ranking from USER_TB",
		resultSetMapping="user_rank_dto")
@SqlResultSetMapping(
		name="user_rank_dto",
		classes = @ConstructorResult(
				targetClass = UserRankDto.class,
				columns= {
					@ColumnResult(name="USER_UID", type=String.class),
					@ColumnResult(name="USER_NICKNAME", type=String.class),
					@ColumnResult(name="USER_CPOINT", type=Integer.class),
					@ColumnResult(name="ranking", type=Integer.class)
				}
		)
)
public class User {

	 //pk, 유저 uid
	   @Id
	   @Column(name="USER_UID")
	   //@Notnull어노테이션으로처리가 가능하다.... 업데이트부분이...
	   private String userUid;

	   //유저 email
	   @Column(name="USER_EMAIL")
	   private String userEmail;

	   //소셜로그인타입
	   @Column(name="USER_LOGINTYPE")
	   private char userLogintype;

	   //유저 닉네임
	   @Column(name="USER_NICKNAME")
	   private String userNickname;

	   //유저 프로필이미지
	   @Column(name="USER_IMG")
	   private String userImg;

	   //유저 장착중인 칭호 아이디
	   @Column(name="TITLE_SEQ")
	   private Long titleSeq;

	   //유저 포인트
	   @Column(name="USER_POINT")
	   private Integer userPoint;

	   //유저 이달의 경쟁전포인트
	   @Column(name="USER_CPOINT")
	   private Integer userCpoint;

	   //유저 스킨 아이디
	   @Column(name="SKIN_SEQ")
	   private Long skinSeq;

	   //유저 승리수
	   @Column(name="USER_WIN")
	   private Integer userWin;

	   //유저 패배수
	   @Column(name="USER_LOSE")
	   private Integer userLose;

	   //유저 무승부수
	   @Column(name="USER_DRAW")
	   private Integer userDraw;

	   //유저 윈윈수
	   @Column(name="USER_WINWIN")
	   private Integer userWinwin;

	   //유저 연승수
	   @Column(name="USER_WINSTREAK")
	   private Integer userWinstreak;

	   //유저가 경쟁전에 참여한 상태인지 (매칭중 x 검색 풀에 등록 o)
	   @Column(name="USER_ONMATCH")
	   private Boolean userOnmatch;

	   //총접속일수
	   @Column(name="USER_ATTEND")
	   private Integer userAttend;

	   //연속접속일수
	   @Column(name="USER_ATTENDSTREAK")
	   private Integer userAttendstreak;

	   //금일접속여부
	   @Column(name="USER_ATTENDTODAY")
	   private Boolean userAttendtoday;

//	   //유저레벨
//	   @Column(name="USER_LEVEL")
//	   private Integer userLevel;

	   //유저경험치
	   @Column(name="USER_EXP")
	   private Integer userExp;

	   //유저 총 태스크수
	   @Column(name="USER_TTTASK")
	   private Integer userTttask;

	   //유저 총 태스크달성수
	   @Column(name="USER_TDTASK")
	   private Integer userTdtask;

	   //유저 일일 태스크수
	   @Column(name="USER_DLTASK")
	   private Integer userDltask;

	   //유저 일일 태스크달성수
	   @Column(name="USER_DDTASK")
	   private Integer userDdtask;

	   //유저 코멘트
	   @Column(name="USER_COMMENT")
	   private String userComment;
}
