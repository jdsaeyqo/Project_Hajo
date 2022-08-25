package com.ssafy.hibernate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hibernate.Repository.ListArchiveRepository;
import com.ssafy.hibernate.Repository.ListTitleRepository;
import com.ssafy.hibernate.Repository.UserArchiveRepository;
import com.ssafy.hibernate.Repository.UserRepository;
import com.ssafy.hibernate.dto.UserArchiveDto;
import com.ssafy.hibernate.entity.User;
import com.ssafy.hibernate.entity.UserArchive;

@Service
public class UserArchiveServiceImpl implements UserArchiveService{
	
	@Autowired
	EntityManager em;

	@Autowired
	UserArchiveRepository userArchiveRepository;
	
	@Autowired
	ListArchiveRepository listArchiveRepository;
	
	@Autowired
	ListTitleRepository listTitleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	//uid로 리스트를 찾아오는과정에서 달성여부를 업데이트한다.
	@Override
	public List<UserArchiveDto> findByUserUid(String userUid) {
		List<UserArchiveDto> list = new ArrayList<>();
		userArchiveRepository.findByUser_UserUid(userUid).forEach(e->{
			String param = e.getListArchive().getArchiveCondparam();
			//이미 달성한 과제라면 넘어간다.
			if(e.getUserArchiveIsdone()) {
				
			}else {
				if(param.equals("exp")) {
					if(e.getUser().getUserExp()>=e.getListArchive().getCond()) {
						e.setUserArchiveIsdone(true);
						userArchiveRepository.save(e);
						em.clear();
					}
				}else if(param.equals("point")) {
					if(e.getUser().getUserPoint()>=e.getListArchive().getCond()) {
						e.setUserArchiveIsdone(true);
						userArchiveRepository.save(e);
						em.clear();
					}
				}else if(param.equals("win")) {
					if(e.getUser().getUserWin()>=e.getListArchive().getCond()) {
						e.setUserArchiveIsdone(true);
						userArchiveRepository.save(e);
						em.clear();
					}
				}else if(param.equals("winwin")) {
					if(e.getUser().getUserWinwin()>=e.getListArchive().getCond()) {
						e.setUserArchiveIsdone(true);
						userArchiveRepository.save(e);
						em.clear();
					}
				}
			}
			
			UserArchiveDto userArchiveDto = new UserArchiveDto(e);
			if(param.equals("exp")) {
				userArchiveDto.setUsercond(e.getUser().getUserExp());
			}else if(param.equals("point")) {
				userArchiveDto.setUsercond(e.getUser().getUserPoint());
			}else if(param.equals("win")) {
				userArchiveDto.setUsercond(e.getUser().getUserWin());
			}else if(param.equals("winwin")) {
				userArchiveDto.setUsercond(e.getUser().getUserWinwin());
			}
			
			if(e.getListArchive().getArchiveRewardtype().equals("title")) {
				userArchiveDto.setRewardName(listTitleRepository.findById(userArchiveDto.getArchiveRewardseq()).get().getTitleName());
			}
			list.add(userArchiveDto);
		});
		
		
		
		return list;
	}

	@Override
	public void setArchives(String userUid) {
		Long size = listArchiveRepository.count();
		System.out.println(size);
		User user = userRepository.findById(userUid).get();
		for(long i=1;i<=size;i++) {
			UserArchive u = new UserArchive();
			u.setUser(user);
			u.setListArchive(listArchiveRepository.findById(i).get());
			u.setUserArchiveIsdone(false);
			u.setUserArchiveIsreceived(false);
			userArchiveRepository.save(u);
			em.clear();
		}
	}

	@Override
	public Optional<UserArchive> findbyId(Long userArchiveSeq) {
		return userArchiveRepository.findById(userArchiveSeq);
	}

	@Override
	public UserArchive save(UserArchive userArchive) {
		return userArchiveRepository.save(userArchive);
	}
	
}
