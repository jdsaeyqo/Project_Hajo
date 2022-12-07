![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/cd2f601e-ed84-4c52-865e-fdfcaaffa6da/Untitled.png)

# 📆 하조

### **나의 가치를 만들어 가는 플래너 앱**

# 💁‍♂️ 프로젝트 소개

어떤 일을 하려고 마음 먹었을 때 계획을 어떻게 세워야 할 지, 계획을 세워도 실천하기 어려운 사람들이 많습니다.

계획을 실천함에 있어 동기부여가 필요하고 플랜을 세세하게 계획할 수 있는 앱이 필요하다고 생각했습니다.

저희는 이러한 문제들을 해결하기 위해 플랜관리, 게시판, 경쟁전 등을 통해 동기부여를 얻고 계획을 실천할 수 있는 서비스를 제공하는 앱을 제작하였습니다

# 🤚🏻 Part

- 안드로이드 ( 자랑/공유 게시판, 홈 화면 담당 )

# 🤔 Learned

- MVVM 패턴을 사용하였습니다.
- SharedPreference를 사용해 메모 기능을 구현하였습니다.
- FloatingActionButton에 애니메이션을 적용하였습니다.
- 양방향 데이터 바인딩을 사용하여 게시글 등록 기능을 구현하였습니다.
- MotionLayout 기능으로 홈 화면을 구성하였습니다.

# 🌟 주요 기능

### 1️⃣ 플랜

- 홈 화면에서 오늘의 해야 할 플랜들을 확인할 수 있습니다
- 플랜 생성 버튼을 통해 플랜을 생성할 수 있습니다

![KakaoTalk_20221121_160417652_04.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c09332b9-27a1-4911-a3a9-a31d5fab6e06/KakaoTalk_20221121_160417652_04.png)

![tutorial1.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/98993f46-16ee-4f70-bd4f-d11c3bbca144/tutorial1.png)

### 2️⃣ 자랑 / 공유 게시판

- 내가 했던 플랜들을 자랑 게시판을 통해 사람들에게 공유하고 인증할 수 있습니다.
- 공유 게시판을 통해 내가 계획한 플랜들을 공유하거나 다른 사람들의 플랜을 확인하며 참고할 수 있습니다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/982b4489-560c-4e1f-a1c7-106e7d2f09b5/Untitled.png)

![KakaoTalk_20221121_160417652_02.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/250bf045-bcbb-45b4-94e8-b119597e6b86/KakaoTalk_20221121_160417652_02.png)

### 3️⃣ 경쟁전

- 다른 유저들과 경쟁전을 통해 플랜을 달성하며 동기부여를 높일 수 있습니다.
- 현재 경쟁전 진행상황과 신청현황을 볼 수 있습니다
- 경쟁전에서 승리 시 룰렛 보상으로 포인트를 얻을 수 있습니다.
    
    ![KakaoTalk_20221121_160417652.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f525a30c-45d1-4372-832a-969fe3243f61/KakaoTalk_20221121_160417652.png)
    

### 4️⃣ 마이페이지

- 현재 내 플랜들의 태스크들을 달성하면 별이 채워집니다.
- 플랜별로 꾸준히 실천했는지 확인할 수 있습니다
    
    ![KakaoTalk_20221121_160417652_01.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/20d61b82-c7de-4bcf-933b-36feea71285d/KakaoTalk_20221121_160417652_01.png)
    

### 5️⃣ 보상 시스템

- 업적을 달성하면 칭호를 얻을 수 있습니다.
- 얻은 포인트로 다이어리 테마를 구매할 수 있습니다.
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/fb6ab3de-5517-441f-8fd5-ef5cf5dde3f8/Untitled.png)
    

### 6️⃣ 다이어리

- 다이어리를 통해 자신의 계획 실천내용들을 이미지와 텍스트를 통해 확인할 수 있습니다.
    
    ![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/ab1e4cd4-2324-4e21-bdae-13e22aa97727/Untitled.png)
    

# 🛠️ 프로젝트 설계

### ✨ 전체 기술 스택

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/7a100921-327e-4c31-8d68-3e662ba34b94/Untitled.png)

### 📚 사용한 라이브러리

| Name | Description |
| --- | --- |
| Coroutine | 안드로이드 비동기 처리 라이브러리 |
| Retrofit2 | 안드로이드 통신 라이브러리 |
| Glide | 이미지 처리 라이브러리 |
| FireBase Auth | 파이어베이스 로그인 라이브러리 |
| FireBaseFireStore | 파이어베이스 데이터 저장, 읽기 라이브러리 |
| FireBaseStorage | 파이어베이스 파일 업로드, 다운로드 라이브러리 |
| Google Auth | 구글 로그인 라이브러리 |
| KakaoSdk | 카카오 로그인 라이브러리 |
| NaverSdk | 네이버 로그인 라이브러리 |
