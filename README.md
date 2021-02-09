# CD-Timer-v2 - 리그오브레전드 챔피언 궁극기 및 소환사주문 재사용대기시간을 확인하는 어플
![preview](https://true-pine.github.io/Android_App_CD-Timer-v2/preview.webp)
### 1. 개발배경  
언제쿨도냐 어플을 조금 더 편리하게 사용할 수 있도록 RiotAPI를 접목시키기 위해서 개발
### 2. 주요기능  
- 게임중인 소환사 닉네임 입력시 자동으로 상대편의 궁극기와 소환사 주문 이미지 표시
- 소환사를 원하는 배치로 지정 가능
### 3. 핵심기술  
- 리그오브레전드 게임사에서 제공하는 Open API를 이용하여 상대방 정보를 불러와 적용
### 4. 배운 점  
- OpenAPI를 위해 별도의 thread와 thread 완료 후 작업할 handler를 구현
- OpenAPI로 전달받은 Json 데이터를 파싱하여 필요한 데이터를 추출하여 저장
- Toolbar의 optionMenu 추가 및 기능 구현
- 오픈소스를 프로젝트로 가져와 필요한 기능을 추가
