package com.ssafy.hibernate.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.hibernate.dto.ListArchiveDto;
import com.ssafy.hibernate.dto.UserArchiveDto;
import com.ssafy.hibernate.dto.UserTaskDto;
import com.ssafy.hibernate.dto.UserTitleDTO;
import com.ssafy.hibernate.entity.ListArchive;
import com.ssafy.hibernate.entity.ListTitle;
import com.ssafy.hibernate.entity.User;
import com.ssafy.hibernate.entity.UserArchive;
import com.ssafy.hibernate.entity.UserBlock;
import com.ssafy.hibernate.entity.UserTitle;
import com.ssafy.hibernate.service.JwtService;
import com.ssafy.hibernate.service.ListArchiveService;
import com.ssafy.hibernate.service.ListTitleService;
import com.ssafy.hibernate.service.UserArchiveService;
import com.ssafy.hibernate.service.UserBlockService;
import com.ssafy.hibernate.service.UserService;
import com.ssafy.hibernate.service.UserTitleService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	JwtService jwtService;

	@Autowired
	UserTitleService userTitleService;

	@Autowired
	ListTitleService listTitleService;
	
	@Autowired
	ListArchiveService listArchiveService;
	
	@Autowired
	UserArchiveService userArchiveService;
	
	@Autowired
	UserBlockService userBlockService;
	
	@Autowired
	EntityManager em;

	@GetMapping("")
	public List<User> getUsers() {
		return userService.findAll();
	}

	// http://아이피/api/user/users/(유저uid)
	// DB에 있는 '유저uid'에 해당하는 결과를 조회. get 방식으로 보냄.(없는 uid검색하면 에러페이지 뜸)
	@GetMapping("/{uid}")
	public User getUser(@PathVariable("uid") String uid) {
		Optional<User> user = userService.findById(uid);
		if (user.isPresent())
			return user.get();
		else
			return null;
	}

	// http://아이피/api/user/users
	// requestbody에 있는 json기반으로 유저를 추가함. post방식으로 보내야함.(uid, 이메일 중복되면 안됨. 모든 정보 다
	// 적혀있어야함)
	@PostMapping("/social/signup")
	public ResponseEntity<Map<String, Object>> addUser(@RequestBody User user) {
		Map<String,Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		userService.save(user);
		em.clear();
		UserTitle userTitle = new UserTitle();
		userTitle.setUser(user);
		userTitle.setListTitle(listTitleService.findById(Long.valueOf("1")).get());
		userArchiveService.setArchives(user.getUserUid());
		em.clear();
		user.setTitleSeq(Long.valueOf("1"));
		userService.save(user);
		em.clear();
		resultMap.put("uid", user.getUserUid());
		resultMap.put("accessToken", jwtService.createJwt(user.getUserUid()));
		resultMap.put("message", "signup success!!");
		status = HttpStatus.ACCEPTED;

		return new ResponseEntity<>(resultMap, status);
	}

	// http://아이피/api/user/users
	// requestbody에 있는 json기반으로 유저정보를 업데이트함. put방식으로 보내야함.
	//RequestBody에 User라고 선언했기때문에, json으로 날아온게 user로 매핑된다.
	@PutMapping("")
	public User updateUser(@RequestBody User user) {

		System.out.println("\n\n\n\n\n\n"+user+"\n\n\n\n\n\n");
		//업데이트 전 원래 값을 알아야 하기때문에 findbyid로 original을 불러온다.
		User original = userService.findById(user.getUserUid()).get();
		if(user.getUserEmail()==null)
			user.setUserEmail(original.getUserEmail());
		if(user.getUserLogintype()=='\0')
			user.setUserLogintype(original.getUserLogintype());
		if(user.getUserNickname()==null)
			user.setUserNickname(original.getUserNickname());
		if(user.getUserImg()==null)
			user.setUserImg(original.getUserImg());
		if(user.getTitleSeq()==null)
			user.setTitleSeq(original.getTitleSeq());
		if(user.getUserPoint()==null)
			user.setUserPoint(original.getUserPoint());
		if(user.getUserCpoint()==null)
			user.setUserCpoint(original.getUserCpoint());
		if(user.getSkinSeq()==null)
			user.setSkinSeq(original.getSkinSeq());
		if(user.getUserWin()==null)
			user.setUserWin(original.getUserWin());
		if(user.getUserLose()==null)
			user.setUserLose(original.getUserLose());
		if(user.getUserDraw()==null)
			user.setUserDraw(original.getUserDraw());
		if(user.getUserWinwin()==null)
			user.setUserWinwin(original.getUserWinwin());
		if(user.getUserWinstreak()==null)
			user.setUserWinstreak(original.getUserWinstreak());
		if(user.getUserOnmatch()==null)
			user.setUserOnmatch(original.getUserOnmatch());
		if(user.getUserAttend()==null)
			user.setUserAttend(original.getUserAttend());
		if(user.getUserAttendstreak()==null)
			user.setUserAttendstreak(original.getUserAttendstreak());
		if(user.getUserAttendtoday()==null)
			user.setUserAttendtoday(original.getUserAttendtoday());
//		if(user.getUserLevel()==null)
//			user.setUserLevel(original.getUserLevel());
		if(user.getUserExp()==null)
			user.setUserExp(original.getUserExp());
		if(user.getUserTttask()==null)
			user.setUserTttask(original.getUserTttask());
		if(user.getUserTdtask()==null)
			user.setUserTdtask(original.getUserTdtask());
		if(user.getUserDltask()==null)
			user.setUserDltask(original.getUserDltask());
		if(user.getUserDdtask()==null)
			user.setUserDdtask(original.getUserDdtask());
		if(user.getUserComment()==null)
			user.setUserComment(original.getUserComment());

		userService.updateById(user.getUserUid(), user);
///////	중간에 후처리하거나 해야될때 이런식으로.. 아니면 바로 return해줘도 된다.

		return user;
	}

	@GetMapping("/email/{email}")
	public User findUser(@PathVariable("email") String email) {
		Optional<User> user = userService.findByUserEmail(email);
		return user.get();
	}

	// http://아이피/api/user/users/(유저uid)
	// DB에 uid에 해당하는 유저를 삭제함. delete 방식으로 보내야 함.
	@DeleteMapping("/{uid}")
	public void deleteUser(@PathVariable("uid") String uid) {
		userService.deleteById(uid);
	}

	//특정 유저의 태스크 갯수를 조회
	@GetMapping("/tasks/{uid}")
	public UserTaskDto getTask(@PathVariable("uid") String uid) {
		Optional<User> u = userService.findById(uid);
		if(u.isPresent()) {
			UserTaskDto userTaskDto = new UserTaskDto(u.get());
			return userTaskDto;
		}else {
			return null;
		}
	}

	//모든 유저의 칭호 보유 현황을 조회
	@GetMapping("/titles")
	public List<UserTitleDTO> titleList() {
		return userTitleService.findAll();
	}

	//특정 유저의 칭호 보유 현황을 조회
	@GetMapping("/titles/{uid}")
	public List<UserTitleDTO> titleListByUserUid(@PathVariable("uid") String uid){
		return userTitleService.findByUser_UserUid(uid);
	}

	//유저가 소유중인 칭호 리스트에 칭호 추가
	@PostMapping("/titles")
	public ResponseEntity<Map<String, Object>> titleListGet(@RequestBody Map<String, Object> vo){
		Map<String,Object> resultMap = new HashMap<>();

		String uid = vo.get("userUid").toString();
		Long titleSeq = Long.valueOf(vo.get("titleSeq").toString());

		//requestbody로 보낸 uid와 titleSeq값이 userTitleList에 존재하는지 확인한다. 결과가 있으면  유저가 보유중인것이고, 아니면 보유중이지 않은 칭호
		UserTitleDTO userTitleDTO = userTitleService.findByUser_UserUidAndListTitle_TitleSeq(uid, titleSeq);
		if(userTitleDTO != null) {
			//이미 유저가 해당 칭호를 보유중이기 때문에 오류를 출력한다.
			resultMap.put("message", "이미 보유중인 칭호입니다.");
		}else {
			//유저가 칭호를 보유중이지 않기 때문에 userTitle에 정보를 넣어서 저장한다.
			User user = userService.findById(uid).get();
			ListTitle listTitle = listTitleService.findById(titleSeq).get();

			UserTitle userTitle = new UserTitle();
			userTitle.setUser(user);
			userTitle.setListTitle(listTitle);

			userTitleService.save(userTitle);

			resultMap.put("message", "획득 성공");
		}

		return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
	}

	//유저가 사용중인 타이틀 변경.
	@PutMapping("/titles")
	public ResponseEntity<Map<String, Object>> titleListByUserUidAndTitleSeq(@RequestBody Map<String, Object> vo) {
		Map<String,Object> resultMap = new HashMap<>();
		String uid = vo.get("userUid").toString();
		Long titleSeq = Long.valueOf(vo.get("titleSeq").toString());

		//requestbody로 보낸 uid와 titleSeq값이 userTitleList에 존재하는지 확인한다. 결과가 있으면 실제로 유저가 보유중인것이고, 아니면 보유중이지 않은 칭호
		UserTitleDTO userTitleDTO = userTitleService.findByUser_UserUidAndListTitle_TitleSeq(uid, titleSeq);
		if(userTitleDTO == null) {
			//find한 결과가 null이기 때문에 오류메세지를 적는다.
			resultMap.put("message", "보유하고 있지 않은 칭호입니다.");
		}else {
			//유저가 해당 칭호를 보유중이기 때문에 userService를 호출해서 titleSeq값을 업데이트 해준다.
			User original = userService.findById(uid).get();
			original.setTitleSeq(titleSeq);
			userService.updateById(original.getUserUid(), original);
			resultMap.put("message", "변경 완료");
		}

		//resultMap 값 반환.
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PostMapping("/social")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> vo) {
		Map<String,Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String access_token = vo.get("access_token");
		String login_type = vo.get("login_type");

		System.out.println(access_token);
		System.out.println(login_type);
		//카카오 로그인인 경우
		if (login_type.equals("K")) {
			String reqURL = "https://kapi.kakao.com/v2/user/me";

			try {
				URL url = new URL(reqURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				conn.setRequestProperty("Authorization", "Bearer " + access_token);

				int responseCode = conn.getResponseCode();
				System.out.println("responseCode : " + responseCode);

				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = "";
				String result = "";

				while ((line = br.readLine()) != null) {
					result += line;
				}
				System.out.println("response body : " + result);

				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(result);

				String id = Long.toString(element.getAsJsonObject().get("id").getAsLong());
				// String nickname =
				// element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

				System.out.println("id : " + id);

				Optional<User> user = userService.findById(id);
				// 이미 db에 존재하는 회원이라면
				if (user.isPresent()) {
					resultMap.put("uid", user.get().getUserUid());
					resultMap.put("accessToken", jwtService.createJwt(user.get().getUserUid()));
					resultMap.put("message", "success");
					status = HttpStatus.OK;
				}
				// db에 없는 경우 회원가입 하라고 보내야 한다.
				else {
					resultMap.put("loginType", "K");
					resultMap.put("uid", id);
					resultMap.put("message", "fail");
					status = HttpStatus.ACCEPTED;
				}

			} catch (IOException e) {
				e.printStackTrace();
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}


		else if(login_type.equals("N")) {
			String reqURL = "https://openapi.naver.com/v1/nid/me";
			try {
	            URL url = new URL(reqURL);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("POST");

	            //    요청에 필요한 Header에 포함될 내용
	            conn.setRequestProperty("Authorization", "Bearer " + access_token);

	            int responseCode = conn.getResponseCode();
	            System.out.println("responseCode : " + responseCode);

	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

	            String line = "";
	            String result = "";

	            while ((line = br.readLine()) != null) {
	                result += line;
	            }
	            System.out.println("response body : " + result);

	            JsonParser parser = new JsonParser();
	            JsonElement element = parser.parse(result);

	            String id = element.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString();
	            System.out.println("id : " + id);

	            Optional<User> user = userService.findById(id);
				// 이미 db에 존재하는 회원이라면
				if (user.isPresent()) {
					resultMap.put("uid", user.get().getUserUid());
					resultMap.put("accessToken", jwtService.createJwt(user.get().getUserUid()));
					resultMap.put("message", "success");
					status = HttpStatus.OK;
				}
				// db에 없는 경우 회원가입 하라고 보내야 한다.
				else {
					resultMap.put("loginType", "N");
					resultMap.put("uid", id);
					resultMap.put("message", "fail");
					status = HttpStatus.ACCEPTED;
				}
	        } catch (IOException e) {
	        	e.printStackTrace();
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
	        }
		}


		else if(login_type.equals("G")) {
			String reqURL = "https://www.googleapis.com/oauth2/v3/userinfo";

			try {
				URL url = new URL(reqURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				conn.setRequestMethod("GET");
				conn.setDoOutput(true);
				conn.setRequestProperty("Authorization", "Bearer " + access_token);

				int responseCode = conn.getResponseCode();
				System.out.println("responseCode : " + responseCode);

				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line = "";
				String result = "";

				while ((line = br.readLine()) != null) {
					result += line;
				}
				System.out.println("response body : " + result);

				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(result);

				String id = element.getAsJsonObject().get("sub").getAsString();
				// String nickname =
				// element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

				System.out.println("id : " + id);

				Optional<User> user = userService.findById(id);
				// 이미 db에 존재하는 회원이라면
				if (user.isPresent()) {
					resultMap.put("uid", user.get().getUserUid());
					resultMap.put("accessToken", jwtService.createJwt(user.get().getUserUid()));
					resultMap.put("message", "success");
					status = HttpStatus.OK;
				}
				// db에 없는 경우 회원가입 하라고 보내야 한다.
				else {
					resultMap.put("loginType", "G");
					resultMap.put("uid", id);
					resultMap.put("message", "fail");
					status = HttpStatus.ACCEPTED;
				}

			} catch (IOException e) {
				e.printStackTrace();
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}

		}
		return new ResponseEntity<>(resultMap, status);
	}
	
	
	//전체 리스트 목록을 출력해주는 getArchiveList
	@GetMapping("/archive")
	public List<ListArchiveDto> getArchiveList(){
		return listArchiveService.findAll();
	}
	
	//유저 업적달성 리스트에 데이터를 채워넣기 위한 메소드. 실제는 사용되어서는 안된다.
//	@GetMapping("/archive/size")
//	public void createUserArchiveList() {
//		userService.findAll().forEach(e->{
//			System.out.println(e.getUserUid());
//			userArchiveService.setArchives(e.getUserUid());
//		});
//	}
	
//	//특정 사용자의 업적 달성 현황을 가져오는 /archive/uid
	@GetMapping("/archive/{uid}")
	public List<UserArchiveDto> getUserArchiveList(@PathVariable("uid") String uid){
		try {
			String jwt = jwtService.getUserUid(jwtService.getJwt());
			if(!uid.equals(jwt)) {
				return userArchiveService.findByUserUid("");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return userArchiveService.findByUserUid("");
		}
		return userArchiveService.findByUserUid(uid);
	}
	
	//userArchive 번호로 업적 달성 보상을 받는 getArchiveReward
	@PutMapping("/archive")
	public ResponseEntity<Map<String, Object>> getArchiveReward(@RequestBody Map<String, Object> vo ){
		Map<String,Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		Long userArchiveSeq = Long.valueOf(vo.get("userArchiveSeq").toString());
		
		Optional<UserArchive> o = userArchiveService.findbyId(userArchiveSeq);
		if(o.isPresent()) {
			UserArchive userArchive = o.get();
			if(!userArchive.getUserArchiveIsdone() || userArchive.getUserArchiveIsreceived()) {
				System.out.println("중복해서 받거나, 완료하지 않은걸 받을 수 없습니다!");
				resultMap.put("message", "fail");
				status = HttpStatus.BAD_REQUEST;
			}else {
				User u = userArchive.getUser();
				ListArchive listArchive = userArchive.getListArchive();
				String param = listArchive.getArchiveRewardtype();
				if(param.equals("exp")) {
					u.setUserExp(u.getUserExp()+listArchive.getArchiveRewardseq().intValue());
					userService.updateById(u.getUserUid(), u);
					em.clear();
				}else if(param.equals("point")) {
					u.setUserPoint(u.getUserPoint()+listArchive.getArchiveRewardseq().intValue());
					userService.updateById(u.getUserUid(), u);
					em.clear();
				}else if(param.equals("title")) {
					ListTitle listTitle = listTitleService.findById(listArchive.getArchiveRewardseq()).get();
					UserTitle userTitle = new UserTitle();
					userTitle.setUser(u);
					userTitle.setListTitle(listTitle);
					
					userTitleService.save(userTitle);
					em.clear();
				}
				
				userArchive.setUserArchiveIsreceived(true);
				
				userArchiveService.save(userArchive);
				
				resultMap.put("message", "success");
				status = HttpStatus.OK;
			}
		}else {
			resultMap.put("message", "fail");
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<>(resultMap, status);
	}
	
	
	//특정유저의 차단리스트를 조회하는 getUserBLockList
	@GetMapping("/block/{uid}")
	public List<String> getUserBlockList(@PathVariable("uid")String uid){
		return userBlockService.findByUserUid(uid);
	}
	
	
	//jwt에 있는 유저의 차단리스트에 uid에 해당하는 유저를 차단하는 addUserBlockList
	@PostMapping("/block")
	public ResponseEntity<Map<String, Object>> addUserBlockList(@RequestBody Map<String, Object> vo){
		Map<String,Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String uid = vo.get("blockUid").toString();
			User user = userService.findById(jwtService.getUserUid(jwtService.getJwt())).get();
			User blockUser = userService.findById(uid).get();
			
			if(userBlockService.findByUser_UserUidAndBlockUser_UserUid(user.getUserUid(), uid).isPresent()) {
				resultMap.put("message", "fail");
				status = HttpStatus.BAD_REQUEST;
			}
			else {
				UserBlock userBlock = new UserBlock();
				userBlock.setUser(user);
				userBlock.setBlockUser(blockUser);
				userBlockService.save(userBlock);
				resultMap.put("message", "success");
				status = HttpStatus.OK;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("message", "fail");
			status = HttpStatus.BAD_REQUEST;
		}finally {
			return new ResponseEntity<>(resultMap, status);
		}
	}
	
	//jwt에 있는 유저의 차단리스트에 uid에 해당하는 유저를 삭제하는 deleteUserBlockList
	@DeleteMapping("/block")
	public ResponseEntity<Map<String, Object>> deleteUserBlockList(@RequestBody Map<String, Object> vo){
		Map<String,Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			String blockUid = vo.get("blockUid").toString();
			String uid = jwtService.getUserUid(jwtService.getJwt());
			
			if(userBlockService.findByUser_UserUidAndBlockUser_UserUid(uid, blockUid).isPresent()) {
				
				userBlockService.deleteByUser_UserUidAndBlockUser_UserUid(uid, blockUid);
				
				resultMap.put("message", "success");
				status = HttpStatus.OK;
			}
			else {
				resultMap.put("message", "fail");
				status = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultMap.put("message", "fail");
			status = HttpStatus.BAD_REQUEST;
		}finally {
			return new ResponseEntity<>(resultMap, status);
		}
	}
	
	@GetMapping("/time")
	public LocalTime getLocalTime() {
		LocalTime now = LocalTime.now();
		return now;
	}

}
