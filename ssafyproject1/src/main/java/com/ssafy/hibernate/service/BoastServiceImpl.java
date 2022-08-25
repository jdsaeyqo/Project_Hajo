package com.ssafy.hibernate.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.BoastLikeRepository;
import com.ssafy.hibernate.Repository.BoastRepository;
import com.ssafy.hibernate.Repository.ListTitleRepository;
import com.ssafy.hibernate.Repository.UserBlockRepository;
import com.ssafy.hibernate.Repository.UserRepository;
import com.ssafy.hibernate.dto.BoastDto;
import com.ssafy.hibernate.dto.BoastListDto;
import com.ssafy.hibernate.dto.BoastListSubDto;
import com.ssafy.hibernate.entity.Boast;
import com.ssafy.hibernate.entity.BoastLike;
import com.ssafy.hibernate.entity.User;

@Service
public class BoastServiceImpl implements BoastService{

	@Autowired
	private BoastRepository bR;

	@Autowired
	private BoastLikeRepository blR;

	@Autowired
	private UserRepository uR;

	@Autowired
	private ListTitleRepository ltR;
	
	@Autowired
	private UserBlockService ubS;

	// 탑파이브 4개 묶음으로 정리하기
	@Override
	public BoastListDto getHomeBoast(String uid) {
		BoastListDto boastlistdto = new BoastListDto();
		List<String> blocklist = ubS.findByUserUid(uid);
		
		if(blocklist.isEmpty()) {
			boastlistdto.setPopularList(getSubDto(bR.findTop5ByOrderByBoastLikecountDesc()));
			boastlistdto.setRecentList(getSubDto(bR.findTop5ByOrderByBoastWrttimeDesc()));
			boastlistdto.setMyList(getSubDto(bR.findTop5ByUser_UserUidOrderByBoastWrttimeDesc(uid)));
			boastlistdto.setLikeList(getLikeSubDto(blR.findTop5ByUser_UserUidOrderByBoastlikeTimeDesc(uid)));
			return boastlistdto;
		} else {
			boastlistdto.setPopularList(getSubDto(bR.findTop5ByUser_UserUidNotInOrderByBoastLikecountDesc(blocklist)));
			boastlistdto.setRecentList(getSubDto(bR.findTop5ByUser_UserUidNotInOrderByBoastWrttimeDesc(blocklist)));
			boastlistdto.setMyList(getSubDto(bR.findTop5ByUser_UserUidOrderByBoastWrttimeDesc(uid)));
			boastlistdto.setLikeList(getLikeSubDto(blR.findTop5ByUser_UserUidAndBoast_User_UserUidNotInOrderByBoastlikeTimeDesc(uid, blocklist)));
			return boastlistdto;
		}
		
	}

	// 탑파이브 (인기글, 최신순, 내가 쓴 글) 썸네일 dto로 변환하기
	@Override
	public List<BoastListSubDto> getSubDto(List<Boast> list) {
		List<BoastListSubDto> listdto = list.stream().map(
				m->{
						BoastListSubDto subdto = new BoastListSubDto();
						subdto.setBoastSeq(m.getBoastSeq());
						subdto.setBoastTitle(m.getBoastTitle());
						subdto.setBoastImg(m.getBoastImg());
						return subdto;
					}
				).collect(Collectors.toList());

		return listdto;
	}

	// 탑파이브 (좋아요 누른 글) 썸네일 dto로 변환하기
	@Override
	public List<BoastListSubDto> getLikeSubDto(List<BoastLike> list) {
		List<BoastListSubDto> listdto = list.stream().map(
				m->{
						BoastListSubDto subdto = new BoastListSubDto();
						subdto.setBoastSeq(m.getBoast().getBoastSeq());
						subdto.setBoastTitle(m.getBoast().getBoastTitle());
						subdto.setBoastImg(m.getBoast().getBoastImg());
						return subdto;
					}
				).collect(Collectors.toList());

		return listdto;
	}

	// 타입에 따라서 뭘 부를지 골라서 부르고 List에 채워넣기
	// 전체보기 리스트 정리하기
	@Override
	public List<BoastDto> getTypeBoast(Map<String, String> vo) {
		String uid = vo.get("uid");
		String type = vo.get("type");
		List<String> blocklist = ubS.findByUserUid(uid);
		
		if(blocklist.isEmpty()) {
			switch(type) {
			case "popular":
				return getBoastDto(bR.findAllByOrderByBoastLikecountDesc(), uid);
			case "recent":
				return getBoastDto(bR.findAllByOrderByBoastWrttimeDesc(), uid);
			case "my":
				return getBoastDto(bR.findAllByUser_UserUidOrderByBoastWrttimeDesc(uid), uid);
			case "like":
				return getLikeBoastDto(blR.findAllByUser_UserUidOrderByBoastlikeTimeDesc(uid));
			default:
				return null;
			}
			
		} else {
			switch(type) {
			case "popular":
				return getBoastDto(bR.findAllByUser_UserUidNotInOrderByBoastLikecountDesc(blocklist), uid);
			case "recent":
				return getBoastDto(bR.findAllByUser_UserUidNotInOrderByBoastWrttimeDesc(blocklist), uid);
			case "my":
				return getBoastDto(bR.findAllByUser_UserUidOrderByBoastWrttimeDesc(uid), uid);
			case "like":
				return getLikeBoastDto(blR.findAllByUser_UserUidAndBoast_User_UserUidNotInOrderByBoastlikeTimeDesc(uid, blocklist));
			default:
				return null;
			}
		}

	}

	// 전체글 상세보기 dto로 변환하기
	@Override
	public List<BoastDto> getBoastDto(List<Boast> list, String uid) {

		List<BoastDto> listdto = list.stream().map(
				m->{
						BoastDto subdto = new BoastDto();
						User wrt = m.getUser();

						subdto.setBoastSeq(m.getBoastSeq());

						subdto.setWrtUid(wrt.getUserUid());
						subdto.setWrtNickname(wrt.getUserNickname());
						subdto.setWrtLevel(wrt.getUserExp()/100);
						subdto.setWrtTitle(ltR.findById(wrt.getTitleSeq()).get().getTitleName());
						subdto.setWrtImg(wrt.getUserImg());

						subdto.setUserIslike(blR.existsByUser_UserUidAndBoast_BoastSeq(uid, m.getBoastSeq()));

						subdto.setBoastTitle(m.getBoastTitle());
						subdto.setBoastContent(m.getBoastContent());
						subdto.setBoastImg(m.getBoastImg());
						subdto.setBoastLikecount(m.getBoastLikecount());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
						subdto.setBoastWrttime(sdf.format(m.getBoastWrttime().getTimeInMillis()));

						return subdto;
					}
				).collect(Collectors.toList());

		return listdto;
	}

	// 좋아요 한 글 상세보기 dto로 변환하기
	@Override
	public List<BoastDto> getLikeBoastDto(List<BoastLike> list) {

		List<BoastDto> listdto = list.stream().map(
				m->{
						BoastDto subdto = new BoastDto();
						User wrt = m.getUser();
						Boast subboast = m.getBoast();

						subdto.setBoastSeq(m.getBoast().getBoastSeq());

						subdto.setWrtNickname(wrt.getUserNickname());
						subdto.setWrtLevel(wrt.getUserExp()/100);
						subdto.setWrtTitle(ltR.findById(wrt.getTitleSeq()).get().getTitleName());
						subdto.setWrtImg(wrt.getUserImg());

						subdto.setUserIslike(true);

						subdto.setBoastTitle(subboast.getBoastTitle());
						subdto.setBoastContent(subboast.getBoastContent());
						subdto.setBoastImg(subboast.getBoastImg());
						subdto.setBoastLikecount(subboast.getBoastLikecount());
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
						subdto.setBoastWrttime(sdf.format(m.getBoastlikeTime().getTimeInMillis()));

						return subdto;
					}
				).collect(Collectors.toList());

		return listdto;
	}

	//하나의 글 상세보기 dto로 변환하기
	@Override
	public BoastDto getDetailBoast(Map<String, String> vo) {
		String uid = vo.get("uid");
		Long seq = Long.valueOf(vo.get("seq"));

		Boast boast = bR.findById(seq).get();
		User wrt = boast.getUser();

		BoastDto subdto = new BoastDto();

		subdto.setBoastSeq(boast.getBoastSeq());

		subdto.setWrtUid(wrt.getUserUid());
		subdto.setWrtNickname(wrt.getUserNickname());
		subdto.setWrtLevel(wrt.getUserExp()/100);
		subdto.setWrtTitle(ltR.findById(wrt.getTitleSeq()).get().getTitleName());
		subdto.setWrtImg(wrt.getUserImg());

		subdto.setUserIslike(blR.existsByUser_UserUidAndBoast_BoastSeq(uid, boast.getBoastSeq()));

		subdto.setBoastTitle(boast.getBoastTitle());
		subdto.setBoastContent(boast.getBoastContent());
		subdto.setBoastImg(boast.getBoastImg());
		subdto.setBoastLikecount(boast.getBoastLikecount());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		subdto.setBoastWrttime(sdf.format(boast.getBoastWrttime().getTimeInMillis()));
		return subdto;
	}

	// 자랑글 작성
	@Override
	public Map<String, Boolean> saveBoast(BoastDto boastdto) {
		Boast boast = new Boast();
		boast.setUser(uR.findById(boastdto.getWrtUid()).get());
		boast.setBoastTitle(boastdto.getBoastTitle());
		boast.setBoastContent(boastdto.getBoastContent());
		boast.setBoastImg(boastdto.getBoastImg());
		boast.setBoastLikecount(0);
		boast.setBoastAgrcount(0);
		boast.setBoastRptcount(0);
		boast.setBoastWrttime(Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA));

		bR.save(boast);

		Map<String, Boolean> vo = new HashMap<>();
		vo.put("result", true);
		return vo;
	}

	// 자랑글 수정
	@Override
	public Map<String, Boolean> updateBoast(BoastDto boastdto) {

		// 결과 출력용 맵
		Map<String, Boolean> result = new HashMap<>();

		// 작성하려는 사람과 작성자의 uid
		String user = boastdto.getWrtUid();
		String writer = bR.findById(boastdto.getBoastSeq()).get().getUser().getUserUid();

		// 같은 사람이 수정하는건지 확인하기
		if(user.equals(writer)) {
			// 작성하려는 사람과 작성자가 일치한다

			Boast boast = bR.findById(boastdto.getBoastSeq()).get();

			if(boastdto.getBoastTitle()!=null) {
				boast.setBoastTitle(boastdto.getBoastTitle());
			}
			if(boastdto.getBoastContent()!=null) {
				boast.setBoastContent(boastdto.getBoastContent());
			}
			if(boastdto.getBoastImg()!=null) {
				boast.setBoastImg(boastdto.getBoastImg());
			}
//			boast.setBoastWrttime(Calendar.getInstance());

			bR.save(boast);
			result.put("result", true);

		} else {
			result.put("result", false);
		}

		return result;
	}

	// 자랑글 삭제
	@Override
	public Map<String, Boolean> deleteBoast(Long seq) {

		// 결과 출력용 맵
		Map<String, Boolean> result = new HashMap<>();
		
		bR.deleteById(seq);
		
		result.put("result", true);
		return result;
	}
	
	
	
	
	

	// 좋아요 누르기
	@Override
	public Map<String, Boolean> updateBoastLike(Map<String, String> vo) {
		
		// 유저 유아이디랑 자랑글 시퀀스를 받는다
		String uid = vo.get("userUid");
		Long seq = Long.valueOf(vo.get("boastSeq"));
		
		// 그거랑 시간대랑 합쳐서 이래저래해서 테이블에 채워넣고
		BoastLike like = new BoastLike();
		Boast boast = bR.findById(seq).get();
		
		like.setUser(uR.findById(uid).get());
		like.setBoast(boast);
		like.setBoastlikeTime(Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA));
		blR.save(like);
		
		// 그 자랑글에 좋아요 +1 해주기
		boast.setBoastLikecount(boast.getBoastLikecount()+1);
		bR.save(boast);

		Map<String, Boolean> result = new HashMap<>();
		result.put("result", true);
		return result;
	}

	// 좋아요 빼기
	@Override
	public Map<String, Boolean> deleteBoastLike(Map<String, String> vo) {
		
		// 유저 유아이디랑 자랑글 시퀀스를 받는다
		String uid = vo.get("userUid");
		Long seq = Long.valueOf(vo.get("boastSeq"));
		
		// 테이블에서 시퀀스랑 시간대로 찾아서 그거 삭제
		BoastLike like = blR.findByUser_UserUidAndBoast_BoastSeq(uid, seq).get();
		Boast boast = like.getBoast();
		
		blR.deleteById(like.getBoastlikeSeq());
		
		// 그 자랑글에 좋아요 -1 해주기
		boast.setBoastLikecount(boast.getBoastLikecount()-1);
		bR.save(boast);
		
		Map<String, Boolean> result = new HashMap<>();
		result.put("result", true);
		return result;
	}
	
	
	
	

	// 신고하기
		@Override
		public Map<String, String> reportBoast(Map<String, String> vo){
			
			Map<String, String> result = new HashMap<>();
			
			// 유저 유아이디랑 자랑글 시퀀스를 받는다
//			String uid = vo.get("userUid");
			Long seq = Long.valueOf(vo.get("boastSeq"));
			
			// 그 자랑글에 신고수 +1 해주기
			Boast boast = bR.findById(seq).get();
			int count = boast.getBoastRptcount()+1;
			boast.setBoastRptcount(count);
			bR.save(boast);
			
			result.put("result", "신고 완료");
			
			// 만약에 신고수가 얼마 이상이 되면 삭제 처리한다
			if (count>=10) {
				bR.deleteById(seq);
				result.put("result", "신고 및 삭제 완료");
			}
			
			return result;
		}

}
