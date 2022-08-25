package com.ssafy.hibernate.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.ListTitleRepository;
import com.ssafy.hibernate.Repository.PlanGrandRepository;
import com.ssafy.hibernate.Repository.ShrplanBmkRepository;
import com.ssafy.hibernate.Repository.ShrplanLikeRepository;
import com.ssafy.hibernate.Repository.ShrplanRepository;
import com.ssafy.hibernate.Repository.UserRepository;
import com.ssafy.hibernate.dto.ShrplanDto;
import com.ssafy.hibernate.dto.ShrplanListDto;
import com.ssafy.hibernate.dto.ShrplanListSubDto;
import com.ssafy.hibernate.entity.Boast;
import com.ssafy.hibernate.entity.BoastLike;
import com.ssafy.hibernate.entity.PlanGrand;
import com.ssafy.hibernate.entity.Shrplan;
import com.ssafy.hibernate.entity.ShrplanBmk;
import com.ssafy.hibernate.entity.ShrplanLike;
import com.ssafy.hibernate.entity.User;

@Service
public class ShrplanServiceImpl implements ShrplanService{

	@Autowired
	private ShrplanRepository sR;

	@Autowired
	private ShrplanLikeRepository slR;

	@Autowired
	private ShrplanBmkRepository sbR;

	@Autowired
	private UserRepository uR;
	
	@Autowired
	private PlanGrandRepository pGR;

	@Autowired
	private ListTitleRepository ltR;
	
	@Autowired
	private UserBlockService ubS;

	// 탑파이브 4개 묶음으로 정리하기
	@Override
	public ShrplanListDto getHomeShrplan(String uid) {
		ShrplanListDto shrplanlistdto = new ShrplanListDto();
		List<String> blocklist = ubS.findByUserUid(uid);
		
		if(blocklist.isEmpty()) {
			shrplanlistdto.setPopularList(getSubDto(sR.findTop5ByOrderByShrplanLikecountDesc()));
			shrplanlistdto.setRecentList(getSubDto(sR.findTop5ByOrderByShrplanWrttimeDesc()));
			shrplanlistdto.setMyList(getSubDto(sR.findTop5ByUser_UserUidOrderByShrplanWrttimeDesc(uid)));
			shrplanlistdto.setLikeList(getLikeSubDto(slR.findTop5ByUser_UserUidOrderByShrplanlikeTimeDesc(uid)));
		} else {
			shrplanlistdto.setPopularList(getSubDto(sR.findTop5ByUser_UserUidNotInOrderByShrplanLikecountDesc(blocklist)));
			shrplanlistdto.setRecentList(getSubDto(sR.findTop5ByUser_UserUidNotInOrderByShrplanWrttimeDesc(blocklist)));
			shrplanlistdto.setMyList(getSubDto(sR.findTop5ByUser_UserUidOrderByShrplanWrttimeDesc(uid)));
			shrplanlistdto.setLikeList(getLikeSubDto(slR.findTop5ByUser_UserUidAndShrplan_User_UserUidNotInOrderByShrplanlikeTimeDesc(uid, blocklist)));
		}
		
		return shrplanlistdto;
	}
	
	// 타입에 따라서 뭘 부를지 골라서 부르고 List에 채워넣기
	// 전체보기 리스트 정리하기
	@Override
	public List<ShrplanListSubDto> getTypeShrplan(Map<String, String> vo) {
		String uid = vo.get("uid");
		String type = vo.get("type");
		List<String> blocklist = ubS.findByUserUid(uid);
		System.out.println("블락리스트 : "+blocklist);
		System.out.println("uid : "+uid);
		
		if(blocklist.isEmpty()) {
			switch(type) {
			case "popular":
				return getSubDto(sR.findAllByOrderByShrplanLikecountDesc());
			case "recent":
				return getSubDto(sR.findAllByOrderByShrplanWrttimeDesc());
			case "my":
				return getSubDto(sR.findAllByUser_UserUidOrderByShrplanWrttimeDesc(uid));
			case "like":
				return getLikeSubDto(slR.findAllByUser_UserUidOrderByShrplanlikeTimeDesc(uid));
			case "bmk":
				return getBmkSubDto(sbR.findAllByUser_UserUidOrderByShrplanbmkTimeDesc(uid));
			default:
				return null;
			}
		} else {
			switch(type) {
			case "popular":
				return getSubDto(sR.findAllByUser_UserUidNotInOrderByShrplanLikecountDesc(blocklist));
			case "recent":
				return getSubDto(sR.findAllByUser_UserUidNotInOrderByShrplanWrttimeDesc(blocklist));
			case "my":
				return getSubDto(sR.findAllByUser_UserUidOrderByShrplanWrttimeDesc(uid));
			case "like":
				return getLikeSubDto(slR.findAllByUser_UserUidAndShrplan_User_UserUidNotInOrderByShrplanlikeTimeDesc(uid, blocklist));
			case "bmk":
				return getBmkSubDto(sbR.findAllByUser_UserUidAndShrplan_User_UserUidNotInOrderByShrplanbmkTimeDesc(uid, blocklist));
			default:
				return null;
			}
		}
		
	}

	// 인기글, 최신순, 내가 쓴 글 썸네일 dto로 변환하기
	@Override
	public List<ShrplanListSubDto> getSubDto(List<Shrplan> list) {
		List<ShrplanListSubDto> listdto = list.stream().map(
				m->{
						ShrplanListSubDto subdto = new ShrplanListSubDto();
						User wrt = m.getUser();
						PlanGrand grand = m.getPlangrand();
						
						subdto.setShrplanSeq(m.getShrplanSeq());
						
						subdto.setWrtUid(wrt.getUserUid());
						subdto.setWrtNickname(wrt.getUserNickname());
						subdto.setWrtLevel(wrt.getUserExp()/100);
						subdto.setWrtTitle(ltR.findById(wrt.getTitleSeq()).get().getTitleName());

						subdto.setGrandplanSeq(grand.getGrandplanSeq());
						subdto.setGrandplanTitle(grand.getGrandplanTitle());
						
						subdto.setShrplanTitle(m.getShrplanTitle());
						subdto.setShrplanCategory(m.getShrplanCategory());
						subdto.setShrplanLikecount(m.getShrplanLikecount());
						subdto.setShrplanBmkcount(m.getShrplanBmkcount());
						
						return subdto;
					}
				).collect(Collectors.toList());

		return listdto;
	}

	// 좋아요 누른 글 썸네일 dto로 변환하기
	@Override
	public List<ShrplanListSubDto> getLikeSubDto(List<ShrplanLike> list) {
		if(list.isEmpty()) {
			List<ShrplanListSubDto> listdto = new ArrayList<>();
			return listdto;
		}
		
		List<ShrplanListSubDto> listdto = list.stream().map(
				m->{
					ShrplanListSubDto subdto = new ShrplanListSubDto();
					Shrplan shr = m.getShrplan();
					User wrt = m.getShrplan().getUser();
					PlanGrand grand = shr.getPlangrand();
					
					subdto.setShrplanSeq(shr.getShrplanSeq());
					
					subdto.setWrtUid(wrt.getUserUid());
					subdto.setWrtNickname(wrt.getUserNickname());
					subdto.setWrtLevel(wrt.getUserExp()/100);
					subdto.setWrtTitle(ltR.findById(wrt.getTitleSeq()).get().getTitleName());

					subdto.setGrandplanSeq(grand.getGrandplanSeq());
					subdto.setGrandplanTitle(grand.getGrandplanTitle());
					
					subdto.setShrplanTitle(shr.getShrplanTitle());
					subdto.setShrplanCategory(shr.getShrplanCategory());
					subdto.setShrplanLikecount(shr.getShrplanLikecount());
					subdto.setShrplanBmkcount(shr.getShrplanBmkcount());

						return subdto;
					}
				).collect(Collectors.toList());

		return listdto;
	}
	
	// 북마크 누른 글 썸네일 dto로 변환하기
	@Override
	public List<ShrplanListSubDto> getBmkSubDto(List<ShrplanBmk> list) {
		if(list.isEmpty()) {
			List<ShrplanListSubDto> listdto = new ArrayList<>();
			return listdto;
		}
		
		List<ShrplanListSubDto> listdto = list.stream().map(
				m->{
					ShrplanListSubDto subdto = new ShrplanListSubDto();
					Shrplan shr = m.getShrplan();
					User wrt = m.getShrplan().getUser();
					PlanGrand grand = shr.getPlangrand();
					
					subdto.setShrplanSeq(shr.getShrplanSeq());
					
					subdto.setWrtUid(wrt.getUserUid());
					subdto.setWrtNickname(wrt.getUserNickname());
					subdto.setWrtLevel(wrt.getUserExp()/100);
					subdto.setWrtTitle(ltR.findById(wrt.getTitleSeq()).get().getTitleName());

					subdto.setGrandplanSeq(grand.getGrandplanSeq());
					subdto.setGrandplanTitle(grand.getGrandplanTitle());
					
					subdto.setShrplanTitle(shr.getShrplanTitle());
					subdto.setShrplanCategory(shr.getShrplanCategory());
					subdto.setShrplanLikecount(shr.getShrplanLikecount());
					subdto.setShrplanBmkcount(shr.getShrplanBmkcount());

					return subdto;
				}
			).collect(Collectors.toList());


		return listdto;
	}


//	// 전체글 상세보기 dto로 변환하기
//	@Override
//	public List<ShrplanDto> getShrplanDto(List<Shrplan> list, String uid) {
//		List<ShrplanDto> listdto = list.stream().map(
//				m->{
//						ShrplanDto subdto = new ShrplanDto();
//						User wrt = m.getUser();
//						PlanGrand grand = m.getPlangrand();
//
//						subdto.setShrplanSeq(m.getShrplanSeq());
//
//						subdto.setWrtUid(wrt.getUserUid());
//						subdto.setWrtNickname(wrt.getUserNickname());
//						subdto.setWrtLevel(wrt.getUserExp()/100);
//						subdto.setWrtTitle(ltR.findById(wrt.getTitleSeq()).get().getTitleName());
//						subdto.setWrtImg(wrt.getUserImg());
//
//						subdto.setUserIslike(slR.existsByUser_UserUidAndShrplan_ShrplanSeq(uid, m.getShrplanSeq()));
//						subdto.setUserIsbmk(sbR.existsByUser_UserUidAndShrplan_ShrplanSeq(uid, m.getShrplanSeq()));
//
//						subdto.setShrplanTitle(m.getShrplanTitle());
//						subdto.setGrandplanTitle(grand.getGrandplanTitle());
//						subdto.setShrplanContent(m.getShrplanContent());
//						subdto.setShrplanCategory(m.getShrplanCategory());
//						subdto.setShrplanPeriod(m.getShrplanPeriod());
//						subdto.setShrplanSummary(m.getShrplanSummary());
//
//						subdto.setShrplanLikecount(m.getShrplanLikecount());
//						subdto.setShrplanBmkcount(m.getShrplanBmkcount());
//						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
//						subdto.setShrplanWrttime(sdf.format(m.getShrplanWrttime().getTimeInMillis()));
//
//						return subdto;
//					}
//				).collect(Collectors.toList());
//
//		return listdto;
//	}
//
//	// 좋아요 누른 글 상세보기 dto로 변환하기
//	@Override
//	public List<ShrplanDto> getLikeShrplanDto(List<ShrplanLike> list) {
//		List<ShrplanDto> listdto = list.stream().map(
//				m->{
//					ShrplanDto subdto = new ShrplanDto();
//					User wrt = m.getUser();
//					Shrplan subshr = m.getShrplan();
//					PlanGrand grand = m.getShrplan().getPlangrand();
//
//					subdto.setShrplanSeq(subshr.getShrplanSeq());
//
//					subdto.setWrtUid(wrt.getUserUid());
//					subdto.setWrtNickname(wrt.getUserNickname());
//					subdto.setWrtLevel(wrt.getUserExp()/100);
//					subdto.setWrtTitle(ltR.findById(wrt.getTitleSeq()).get().getTitleName());
//					subdto.setWrtImg(wrt.getUserImg());
//
//					subdto.setUserIslike(true);
//					subdto.setUserIsbmk(sbR.existsByUser_UserUidAndShrplan_ShrplanSeq(wrt.getUserUid(), subshr.getShrplanSeq()));
//
//					subdto.setShrplanTitle(subshr.getShrplanTitle());
//					subdto.setGrandplanTitle(grand.getGrandplanTitle());
//					subdto.setShrplanContent(subshr.getShrplanContent());
//					subdto.setShrplanCategory(subshr.getShrplanCategory());
//					subdto.setShrplanPeriod(subshr.getShrplanPeriod());
//					subdto.setShrplanSummary(subshr.getShrplanSummary());
//
//					subdto.setShrplanLikecount(subshr.getShrplanLikecount());
//					subdto.setShrplanBmkcount(subshr.getShrplanBmkcount());
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
//					subdto.setShrplanWrttime(sdf.format(subshr.getShrplanWrttime().getTimeInMillis()));
//
//					return subdto;
//					}
//				).collect(Collectors.toList());
//
//		return listdto;
//	}
//
//	// 북마크 누른 글 상세보기 dto로 변환하기
//	@Override
//	public List<ShrplanDto> getBmkShrplanDto(List<ShrplanBmk> list) {
//		List<ShrplanDto> listdto = list.stream().map(
//				m->{
//					ShrplanDto subdto = new ShrplanDto();
//					User wrt = m.getUser();
//					Shrplan subshr = m.getShrplan();
//					PlanGrand grand = m.getShrplan().getPlangrand();
//
//					subdto.setShrplanSeq(subshr.getShrplanSeq());
//
//					subdto.setWrtUid(wrt.getUserUid());
//					subdto.setWrtNickname(wrt.getUserNickname());
//					subdto.setWrtLevel(wrt.getUserExp()/100);
//					subdto.setWrtTitle(ltR.findById(wrt.getTitleSeq()).get().getTitleName());
//					subdto.setWrtImg(wrt.getUserImg());
//
//					subdto.setUserIslike(slR.existsByUser_UserUidAndShrplan_ShrplanSeq(wrt.getUserUid(), subshr.getShrplanSeq()));
//					subdto.setUserIsbmk(true);
//
//					subdto.setShrplanTitle(subshr.getShrplanTitle());
//					subdto.setGrandplanTitle(grand.getGrandplanTitle());
//					subdto.setShrplanContent(subshr.getShrplanContent());
//					subdto.setShrplanCategory(subshr.getShrplanCategory());
//					subdto.setShrplanPeriod(subshr.getShrplanPeriod());
//					subdto.setShrplanSummary(subshr.getShrplanSummary());
//
//					subdto.setShrplanLikecount(subshr.getShrplanLikecount());
//					subdto.setShrplanBmkcount(subshr.getShrplanBmkcount());
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
//					subdto.setShrplanWrttime(sdf.format(subshr.getShrplanWrttime().getTimeInMillis()));
//
//					return subdto;
//					}
//				).collect(Collectors.toList());
//
//		return listdto;
//	}

	@Override
	public ShrplanDto getDetailShrplan(Map<String, String> vo) {
		String uid = vo.get("uid");
		Long seq = Long.valueOf(vo.get("seq"));

		Shrplan subshr = sR.findById(seq).get();
		User wrt = subshr.getUser();
		PlanGrand grand = subshr.getPlangrand();

		ShrplanDto subdto = new ShrplanDto();

		subdto.setShrplanSeq(subshr.getShrplanSeq());

		subdto.setWrtUid(wrt.getUserUid());
		subdto.setWrtNickname(wrt.getUserNickname());
		subdto.setWrtLevel(wrt.getUserExp()/100);
		subdto.setWrtTitle(ltR.findById(wrt.getTitleSeq()).get().getTitleName());
		subdto.setWrtImg(wrt.getUserImg());

		subdto.setUserIslike(slR.existsByUser_UserUidAndShrplan_ShrplanSeq(uid, subshr.getShrplanSeq()));
		subdto.setUserIsbmk(sbR.existsByUser_UserUidAndShrplan_ShrplanSeq(uid, subshr.getShrplanSeq()));

		subdto.setGrandplanSeq(grand.getGrandplanSeq());
		subdto.setGrandplanTitle(grand.getGrandplanTitle());
		
		subdto.setShrplanTitle(subshr.getShrplanTitle());
		subdto.setShrplanContent(subshr.getShrplanContent());
		subdto.setShrplanCategory(subshr.getShrplanCategory());
		subdto.setShrplanPeriod(subshr.getShrplanPeriod());
		subdto.setShrplanSummary(subshr.getShrplanSummary());

		subdto.setShrplanLikecount(subshr.getShrplanLikecount());
		subdto.setShrplanBmkcount(subshr.getShrplanBmkcount());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		subdto.setShrplanWrttime(sdf.format(subshr.getShrplanWrttime().getTimeInMillis()));

		return subdto;
	}



	@Override
	public Map<String, Boolean> saveShrplan(ShrplanDto shrplandto) {
		Shrplan shr = new Shrplan();
		
		// 공유플랜 시퀀스 비우고
		shr.setUser(uR.findById(shrplandto.getWrtUid()).get());
		shr.setPlangrand(pGR.findById(shrplandto.getGrandplanSeq()).get());
		
		shr.setShrplanTitle(shrplandto.getShrplanTitle());
		shr.setShrplanContent(shrplandto.getShrplanContent());
		shr.setShrplanCategory(shrplandto.getShrplanCategory());
		shr.setShrplanPeriod(shrplandto.getShrplanPeriod());
		shr.setShrplanSummary(shrplandto.getShrplanSummary());
		
		shr.setShrplanLikecount(0);
		shr.setShrplanBmkcount(0);
		shr.setShrplanRptcount(0);
		
		shr.setShrplanWrttime(Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA));
		
		sR.save(shr);
		
		Map<String, Boolean> vo = new HashMap<>();
		vo.put("result", true);
		return vo;
	}

	@Override
	public Map<String, Boolean> updateShrplan(ShrplanDto shrplandto) {
		
		// 결과 출력용 맵
		Map<String, Boolean> result = new HashMap<>();
		
		// 작성하려는 사람과 작성자의 uid
		String user = shrplandto.getWrtUid();
		String writer = sR.findById(shrplandto.getShrplanSeq()).get().getUser().getUserUid();

		// 같은 사람이 수정하는건지 확인하기
		if(user.equals(writer)) {
			// 작성하려는 사람과 작성자가 일치한다
			
			Shrplan shr = sR.findById(shrplandto.getShrplanSeq()).get();

			if(shrplandto.getGrandplanSeq()!=0) {
				shr.setPlangrand(pGR.findById(shrplandto.getGrandplanSeq()).get());
			}
			if(shrplandto.getShrplanTitle()!=null) {
				shr.setShrplanTitle(shrplandto.getShrplanTitle());
			}
			if(shrplandto.getShrplanContent()!=null) {
				shr.setShrplanContent(shrplandto.getShrplanContent());
			}
			if(shrplandto.getShrplanCategory()!=null){
				shr.setShrplanCategory(shrplandto.getShrplanCategory());
			}
			if(shrplandto.getShrplanPeriod()!=0) {
				shr.setShrplanPeriod(shrplandto.getShrplanPeriod());
			}
			if(shrplandto.getShrplanSummary()!=null) {
				shr.setShrplanSummary(shrplandto.getShrplanSummary());
			}
			
		sR.save(shr);
		result.put("result", true);
		
		} else {
			result.put("result", false);
		}
		
		return result;
	}

	@Override
	public Map<String, Boolean> deleteShrplan(Long seq) {
		// 결과 출력용 맵
		Map<String, Boolean> result = new HashMap<>();
		
		sR.deleteById(seq);
		
		result.put("result", true);
		return result;
	}


	
	// 좋아요 누르기
	@Override
	public Map<String, Boolean> updateShrplanLike(Map<String, String> vo) {
		
		// 유저 유아이디랑 공유플랜 시퀀스를 받는다
		String uid = vo.get("userUid");
		Long seq = Long.valueOf(vo.get("sharePlanSeq"));
		
		// 그거랑 시간대랑 합쳐서 이래저래해서 테이블에 채워넣고
		ShrplanLike like = new ShrplanLike();
		Shrplan shr = sR.findById(seq).get();
		
		like.setUser(uR.findById(uid).get());
		like.setShrplan(shr);
		like.setShrplanlikeTime(Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA));
		slR.save(like);
		
		// 그 자랑글에 좋아요 +1 해주기
		shr.setShrplanLikecount(shr.getShrplanLikecount()+1);
		sR.save(shr);

		Map<String, Boolean> result = new HashMap<>();
		result.put("result", true);
		return result;
	}

	// 좋아요 빼기
	@Override
	public Map<String, Boolean> deleteShrplanLike(Map<String, String> vo) {

		// 유저 유아이디랑 공유플랜 시퀀스를 받는다
		String uid = vo.get("userUid");
		Long seq = Long.valueOf(vo.get("sharePlanSeq"));
		
		// 테이블에서 시퀀스랑 시간대로 찾아서 그거 삭제
		ShrplanLike like = slR.findByUser_UserUidAndShrplan_ShrplanSeq(uid, seq).get();
		Shrplan shr = like.getShrplan();
		
		slR.deleteById(like.getShrplanlikeSeq());
		
		// 그 공유플랜에 좋아요 -1 해주기
		shr.setShrplanLikecount(shr.getShrplanLikecount()-1);
		sR.save(shr);
		
		Map<String, Boolean> result = new HashMap<>();
		result.put("result", true);
		return result;
	}



	
	// 북마크 누르기
	@Override
	public Map<String, Boolean> updateShrplanBmk(Map<String, String> vo) {
		// 유저 유아이디랑 공유플랜 시퀀스를 받는다
		String uid = vo.get("userUid");
		Long seq = Long.valueOf(vo.get("sharePlanSeq"));
		
		// 그거랑 시간대랑 합쳐서 이래저래해서 테이블에 채워넣고
		ShrplanBmk bmk = new ShrplanBmk();
		Shrplan shr = sR.findById(seq).get();
		
		bmk.setUser(uR.findById(uid).get());
		bmk.setShrplan(shr);
		bmk.setShrplanbmkTime(Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"), Locale.KOREA));
		sbR.save(bmk);
		
		// 그 자랑글에 좋아요 +1 해주기
		shr.setShrplanBmkcount(shr.getShrplanBmkcount()+1);
		sR.save(shr);

		Map<String, Boolean> result = new HashMap<>();
		result.put("result", true);
		return result;
	}
	
	// 북마크 빼기
	@Override
	public Map<String, Boolean> deleteShrplanBmk(Map<String, String> vo) {
		
		// 유저 유아이디랑 공유플랜 시퀀스를 받는다
		String uid = vo.get("userUid");
		Long seq = Long.valueOf(vo.get("sharePlanSeq"));
		
		// 테이블에서 시퀀스랑 시간대로 찾아서 그거 삭제
		ShrplanBmk bmk = sbR.findByUser_UserUidAndShrplan_ShrplanSeq(uid, seq).get();
		Shrplan shr = bmk.getShrplan();
		
		sbR.deleteById(bmk.getShrplanbmkSeq());
		
		// 그 공유플랜에 좋아요 -1 해주기
		shr.setShrplanBmkcount(shr.getShrplanBmkcount()-1);
		sR.save(shr);
		
		Map<String, Boolean> result = new HashMap<>();
		result.put("result", true);
		return result;
	}



	// 신고하기
	@Override
	public Map<String, String> reportShrplan(Map<String, String> vo) {
		
		Map<String, String> result = new HashMap<>();
		
		// 유저 유아이디랑 공유플랜 시퀀스를 받는다
//		String uid = vo.get("userUid");
		Long seq = Long.valueOf(vo.get("sharePlanSeq"));
		
		// 그 공유플랜에 신고수 +1 해주기
		Shrplan shr = sR.findById(seq).get();
		int count = shr.getShrplanRptcount()+1;
		shr.setShrplanRptcount(count);
		sR.save(shr);
		
		result.put("result", "신고 완료");
		
		// 만약에 신고수가 얼마 이상이 되면 삭제 처리한다
		if (count>=10) {
			sR.deleteById(seq);
			result.put("result", "신고 및 삭제 완료");
		}
		
		return result;
	}

}
