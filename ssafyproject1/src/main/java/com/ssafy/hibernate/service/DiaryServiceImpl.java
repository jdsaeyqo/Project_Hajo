package com.ssafy.hibernate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.DiaryObjRepository;
import com.ssafy.hibernate.Repository.DiaryObjTextRepository;
import com.ssafy.hibernate.Repository.DiaryPageRepository;
import com.ssafy.hibernate.Repository.DiaryRepository;
import com.ssafy.hibernate.Repository.UserRepository;
import com.ssafy.hibernate.dto.DiaryDto;
import com.ssafy.hibernate.dto.DiaryHomeDto;
import com.ssafy.hibernate.dto.DiaryObjDto;
import com.ssafy.hibernate.dto.DiaryObjSaveDto;
import com.ssafy.hibernate.dto.DiaryPageDto;
import com.ssafy.hibernate.dto.DiaryTextDto;
import com.ssafy.hibernate.dto.DiaryTextSaveDto;
import com.ssafy.hibernate.entity.Diary;
import com.ssafy.hibernate.entity.DiaryObj;
import com.ssafy.hibernate.entity.DiaryObjText;
import com.ssafy.hibernate.entity.DiaryPage;
import com.ssafy.hibernate.entity.User;

@Service
public class DiaryServiceImpl implements DiaryService {

	@Autowired
	private DiaryRepository dR;

	@Autowired
	private DiaryPageRepository dpR;

	@Autowired
	private DiaryObjRepository doR;

	@Autowired
	private DiaryObjTextRepository dotR;
	
	
	@Autowired
	private UserRepository uR;

	@Override
	public List<DiaryHomeDto> diaryHome(String uid) {
		List<DiaryHomeDto> diaryhome = dR.findByUser_UserUid(uid).stream().map(m -> {
			DiaryHomeDto dto = new DiaryHomeDto(m);
			return dto;
		}).collect(Collectors.toList());
		return diaryhome;
	}

	@Override
	public DiaryDto getDiary(Long seq) {

		// 시퀀스로 다이어리를 찾아
		// 그걸 dto로 돌려
		// 그럼 지금 다이어리 안의 다이어리페이지리스트가 비어있음
		DiaryDto diarydto = new DiaryDto(dR.findById(seq).get());

		// 다이어리 시퀀스를 가지고 다이어리 페이지를 조회해옴
		// 그걸 dto로 돌려
		// 그럼 지금 오브젝트 목록이 비어있음
		List<DiaryPageDto> pages = dpR.findByDiary_DiarySeq(seq).stream().map(

				m -> {
					DiaryPageDto page = new DiaryPageDto(m);

					// 다이어리 페이지 시퀀스를 가지고 오브젝트 목록을 받아옴
					// 그걸 dto로 돌려
					// 그럼 지금 글정보 비어있음
					List<DiaryObjDto> objs = doR.findByDiarypage_DiarypageSeq(page.getDiarypageSeq()).stream()
							.map(n -> {
								DiaryObjDto obj = new DiaryObjDto(n);
								// 글정보 채움
								if (n.getObjtextSeq()!=null) {
									DiaryTextDto text = new DiaryTextDto(dotR.findById(n.getObjtextSeq()).get());
									obj.setObjtext(text);
								}
								return obj;
							}

							).collect(Collectors.toList());
					// 오브젝트 목록 채움
					page.setDirayObjs(objs);

					return page;
				}

		).collect(Collectors.toList());

		// 다이어리 페이지 리스트 채워넣음
		diarydto.setDiaryPages(pages);

		return diarydto;

	}
	
	
	
	

	@Override
	public int addObj(DiaryObjSaveDto objdto) {

		try {
			// 일단 오브젝트 만들고
			try {
				DiaryObj obj = new DiaryObj();
				obj.setDiarypage(dpR.findById(objdto.getDiarypageSeq()).get());
				obj.setDiaryobjType(objdto.getDiaryobjType());
				obj.setDiaryobjXloc(objdto.getDiaryobjXloc());
				obj.setDiaryobjYloc(objdto.getDiaryobjYloc());

				// 이걸 미리 등록해놔야 이따가 텍스트 등록할 수 있어...
				doR.save(obj);

				try {

					if (objdto.getDiaryobjType().equals("pic")) {
						obj.setObjpicImg(objdto.getObjpicImg());
					} else if (objdto.getDiaryobjType().equals("text")) {
						// 텍스트 관련 정보가 있으면 텍스트 채워넣기
						DiaryTextSaveDto textdto = objdto.getObjtext();
						DiaryObjText text = new DiaryObjText();
						text.setDiaryobj(obj);
						text.setObjtextContent(textdto.getObjtextContent());
						text.setObjtextFont(textdto.getObjtextFont());
						text.setObjtextSize(textdto.getObjtextSize());
						text.setObjtextColor(textdto.getObjtextColor());
						text.setObjtextIsbold(textdto.getObjtextIsbold());
						text.setObjtextIsitalic(textdto.getObjtextIsitalic());
						dotR.save(text);
						obj.setObjtextSeq(text.getObjtextSeq());
					} else {
						// 둘다 없으면 잘못 들어온거지
						// 아까 등록된걸 삭제해줘야함
						doR.delete(obj);
						return 400;
					}

					// 오브젝트 여기서 업데이트 되겠지
					doR.save(obj);
					return 200;
					
				} catch (Exception e) {
					return 400;
				}

			} catch (Exception e) {
				return 400;
			}

		} catch (Exception e) {
			return 400;
		}

	}

	@Override
	public int deleteObj(Long seq) {
		try {
			doR.deleteById(seq);
			return 200;
		} catch (Exception e) {
			return 400;
		}
	}

	@Override
	public int moveObj(List<Map<String, Long>> list) {
		
		try {
			List<DiaryObj> objs = new ArrayList<>();

			for (Map<String, Long> m : list) {
				Long seq = m.get("diaryobjSeq");
				DiaryObj obj = doR.findById(seq).get();
				obj.setDiaryobjXloc(Math.toIntExact(m.get("diaryobjXloc")));
				obj.setDiaryobjYloc(Math.toIntExact(m.get("diaryobjYloc")));
				objs.add(obj);
			}

			doR.saveAll(objs);
			return 200;
			
		} catch (Exception e) {
			return 400;
		}
		
	}
	
	@Override
	public int modifyPic(Map<String, String> vo) {
		try {
			Long seq = Long.valueOf(vo.get("diaryobjSeq"));
			DiaryObj obj = doR.findById(seq).get();
			obj.setObjpicImg(vo.get("objpicImg"));
			doR.save(obj);
			return 200;
		} catch (Exception e) {
			return 400;
		}
	}

	@Override
	public int modifyText(DiaryTextSaveDto textdto) {
		try {
			DiaryObjText text = dotR.findByDiaryobj_DiaryobjSeq(textdto.getObjSeq()).get();
		
			// 일단 이대로 저장해야 하는 상태라고 가정함
			// 컨텐츠, 폰트이름, 폰트크기, 폰트색, 볼드, 이탤릭
			text.setObjtextContent(textdto.getObjtextContent());
			text.setObjtextFont(textdto.getObjtextFont());
			text.setObjtextSize(textdto.getObjtextSize());
			text.setObjtextColor(textdto.getObjtextColor());
			text.setObjtextIsbold(textdto.getObjtextIsbold());
			text.setObjtextIsitalic(textdto.getObjtextIsitalic());
			
			dotR.save(text);
			return 200;
		} catch(Exception e) {
			return 400;
		}

	}
	
	
	
	

	@Override
	public int addDiary(Map<String, String> vo) {
		String uid = vo.get("uid");
		String type = vo.get("diaryType");
		String title = vo.get("diaryTitle");
		int price = Integer.parseInt(vo.get("price"));

		try {
			// 사용자에게 다이어리 생성
			Diary diary = new Diary();
			diary.setUser(uR.findById(uid).get());
			diary.setDiaryTitle(title);
			diary.setDiaryType(type);
			dR.save(diary);

			try {
				List<DiaryPage> pages = new ArrayList<>();

				// 방금 생성한 다이어리 시퀀스 가지고 다이어리 페이지 1~10 만들어주기
				for (int i = 1; i < 11; i++) {
					DiaryPage page = new DiaryPage();
					page.setDiary(diary);
					page.setDiarypageNum(i);
					pages.add(page);
				}

				dpR.saveAll(pages);
				
				// 생성 다 성공하고 나면
				try {
					User user = uR.findById(uid).get();
					user.setUserPoint(user.getUserPoint()-price);
					uR.save(user);
				} catch (Exception e) {
					return 400;
				}
				
				return 200;

			} catch (Exception e) {
				return 400;
			}

		} catch (Exception e) {
			return 400;
		}
	}

	@Override
	public int deleteDiary(Long seq) {
		try {
			dR.deleteById(seq);
			return 200;
		} catch (Exception e) {
			return 400;
		}
	}

}
