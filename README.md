# 드론및 고객 문의 관리 웹
**(주)두시텍 인턴 프로젝트 #2**
<br>
제작기간 : 2023.09.18~2023.12.28

## <u>👨‍🔧Producer
 #### 기획 및 제작

강홍규


	

## </u> 🧐Project Background
<h3>프로젝트 배경</h3>
드론의 옵션에 따라 시리얼 번호 자동 생성 기능 및 생성된 시리얼 번호 별로 고객 문의사항을 <br> 통합적으로 관리할 수 있는 프로그램의 필요성이 생겨 보다 체계적으로 제품 관리를 수행하고자 제작

  <br>
  <br>
  
## 💻System Design

<h3> Tech Stack </h3>
<div align="left">
<img src="https://img.shields.io/badge/Java-007396?style=flat&logo=Conda-Forge&logoColor=white" />
<img src="https://img.shields.io/badge/Spring-6DB33F?style=flat&logo=Spring&logoColor=white" />
	<img src="https://img.shields.io/badge/Gradle-02303A?style=flat&logo=gradle&logoColor=white" />
  <img src="https://img.shields.io/badge/Intellij%20IDE-000000?style=flat&logo=intellijidea&logoColor=white" />
	<img src="https://img.shields.io/badge/Tomcat-F8DC75?style=flat&logo=ApacheTomcat&logoColor=white" />
	<img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=white" />
  <img src="https://img.shields.io/badge/html5-E34F26?style=flat&logo=html5&logoColor=white" />
		<img src="https://img.shields.io/badge/css3-1572B6?style=flat&logo=css3&logoColor=white" />
		<img src="https://img.shields.io/badge/javascript-F7DF1E?style=flat&logo=javascript&logoColor=white" />
</div>
<br>

### System Requirements

![1](https://github.com/hk1776/manage/assets/77769783/f70816d8-2a2c-45e3-95c6-7de1e9ae80a4)


## 📲Result

- #### (주)두시텍에 해당 프로그램을 인계 하였고 기능 추가 및 수정 후 배포될 예정이다.
<br>

   ### 시스템 구성도
![2](https://github.com/hk1776/manage/assets/77769783/acbc98d2-ac73-43a6-9413-8c8cfd2451fa)


![3](https://github.com/hk1776/manage/assets/77769783/cf4808c9-6d88-435a-a448-e6f2987038a8)


  ### 주요기능
   - #### 고객 문의 
      - 드론 관련 고객 문의 사항을 등록하고 관리하는 페이지이며 문의 게시글의 종류에 따라 각각 다른 처리 방법을 제공한다.<br>
      

https://github.com/hk1776/manage/assets/77769783/c8d6e920-81e0-4fe9-ab76-b0c17a767501



<br><br>
 - #### 드론 납품
      - 드론 납품에대한 문의 게시물에서 '드론 납품하기' 버튼을 누를 시 드론 생성 페이지로 이동
      - 드론 생성페이지에서 드론의 옵션과 부품의 버전에 따라 새로운 시리얼 번호 생성<br>


https://github.com/hk1776/manage/assets/77769783/cc5b8827-e8c8-4e0f-87cf-27f0d4c7e36d



<br><br>
 - #### 부품 납품
      - 부품 납품에 대한 문의 게시물에서 '부품 납품하기' 버튼을 누를 시 부품 관리 페이지로 이동
      - 이미 납품된 드론의 부품을 관리하는 것이기 때문에 납품된 드론의 시리얼 번호가 필요함
      - 부품별로 시리얼 번호가 특정 규칙에 의해 부여되어 있으며 부품 관리 페이지에서는 새로운 부품 시리얼 번호 생성 및 <br> 기존 부품의 시리얼 번호 삭제 가능  <br>
      


https://github.com/hk1776/manage/assets/77769783/29a02917-f4e7-40f5-aeb3-dd5836f495ec




<br><br>
  - #### A/S & 납품 일정관리
      - FullCalendar 라이브러리를 사용해 DB에 저장된 A/S & 납품 일정 데이터를 가져와 일정 관리 기능을 제공한다.
      - 납품 및 A/S관련 문의 게시물에서 해당 문의를 처리할 시 이동하는 페이지이다.
      - 매일 해당 문의사항에 대한 작업이 얼마나 진행됐는지 작성하여 스케중을 관리할 수 있다.
      - 출고가 돼면 작업 진행 업데이트가 불가능하다.
      - 예상 기간보다 출고가 일찍되면 기간을 나타내는 바가 출고된 날짜에 맞춰 줄어든다.
      - 출고가 지연이 되면 붉은색의 기간을 나타내는 바와 함께 지연 사유 입력글이 나온다.

<br>
<h4>예상 기간 내 드론 일정 </h4>

https://github.com/hk1776/manage/assets/77769783/8121dcc5-3a46-4cd5-a146-82e3719349a5

<br>
<h4> 출고 지연 드론 일정 </h4>


https://github.com/hk1776/manage/assets/77769783/1aab51f1-1aae-4f3e-a12e-2ca214b74411



<br><br>
  - #### 비행 일정관리
      - FullCalendar 라이브러리를 사용해 드론 비행 테스트 일정에 대한 저장 수정 삭제 등의 관리 기능을 제공한다.
      - 비행 장소, 인원 등을 설정하고 일정을 생성하여 원하는 시간에 일정을 끌어다 배치할 수 있다. <br> 또한 기존에 배치되어 있던 일정을 마우스 드래그를 통해 수정할 수 있다.
      - 저장 버튼을 누르면 DB에 해당 일정이 저장되며 만약 저장을 하지 않고 다른 페이지로 이동하거나 새로고침을 한다면 <br> '변경사항이 저장되지 않을 수 있습니다.'라는 경고 문구가 뜬다.
      - 일정을 클릭 시 해당 일정에 대한 정보가 오른쪽에 뜨고 정보 창에 붉은색 버튼을 누를 시 해당 일정을 제거된다.<br>


https://github.com/hk1776/manage/assets/77769783/df7f580a-734e-4ade-a9da-a7037a116e87



<br><br>
 - #### 드론 검색
      - 드론 시리얼 번호와 납품한 기업 이름을 통해 드론을 검색할 수 있다.
      - 검색된 드론에 대한 상세 정보 및 연관된 문의사항, A/S일정, 납품 일정 게시물에 대한 URL을 제공한다.<br>
      

https://github.com/hk1776/manage/assets/77769783/2aa651a0-2bc3-4437-a9d9-8cb63f3c571f



<br><br>
 - #### 드론 관리
      - 드론 시리얼 생성과 드론의 시리얼 번호를 생성할 시 사용되는 여러 부품들의 버전정보를 관리하는 페이지이다.  <br>
      

https://github.com/hk1776/manage/assets/77769783/ade4b42a-442d-451a-8256-7125fa928cb4


<br><br>
 - #### 관리자 페이지 & My 페이지
      - 사용자 등급은 guest, normal, admin 세 단계이며 guest 등급은 게시물 관리를 할 수 없다.
      - 관리자 페이지는 admin 등급만 접근이 가능하며 회원들의 정보와 등급 등을 관리할 수 있다.
      - My 페이지 에서는 개인 정보와 자신이 담당하는 문의 사항 등을 확인할 수 있다.  <br>

https://github.com/hk1776/manage/assets/77769783/33250fa9-4523-40ff-94e3-bdfaee6815e5


<br><br>

  ## 🎓Conclusion
   웹 제작을 하면서 기본적인 틀을 만들어 회의를 통해 시연과 동시에 직원분들의 요구사항을 수집하고 이를 웹에 반영하면서 수시로 담당자 분께 의견을 물으며 제작하였다.
   이 과정에서 프로토타입과 애자일에 해당하는 개발 프로세스를 직접 체험해 볼 수 있었고 효율적이고 사용자의 요구사항에 적합한 결과물을 도출하기 위해서는 소프트웨어 개발프로세스가 중요하다는 것을 배웠다.
  
<br><br>
