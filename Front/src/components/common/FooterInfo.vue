<template>
  <b-row class="footer" style="margin-top: 80px">
    <div class="container-fluid justify-content-center" style="margin: 0">
      <hr class="mt-0 mb-1" />
      <footer>
        <div class="row justify-content-around mb-0 pb-0">
          <div class="col-11">
            <div class="row justify-content-center">
              <div class="col-md-3 font-italic align-items-center">
                <div class="findElementsImg">
                  <img src="../../assets/img/logo.png" alt="" class="ssafy" />
                </div>
              </div>
              <div class="col-md-3 my-sm-0">
                <div class="findElements">
                  <div class="findtitle">Firstor</div>
                  <div class="findadd"><img src="../../assets/img/user.png" alt="" class="contactimg" /> 송다경 & 이주희</div>
                  <div class="find"><img src="../../assets/img/map.png" alt="" class="contactimg" /> (SSAFY) 대전광역시 유성구 덕명동 124</div>
                  <div class="find"><img src="../../assets/img/phone.png" alt="" class="contactimg" /> 1544-9001</div>
                  <div class="find"><img src="../../assets/img/email.png" alt="" class="contactimg" /> ssafy@ssafy.com</div>
                </div>
              </div>
              <div class="col-md-3 my-sm-0">
                <div class="findElements" style="text-align: center">
                  <div class="findtitle">Menu</div>
                  <div class="find" style="margin-top: 10px"><a href="/notice">공지사항</a></div>
                  <div class="find"><a href="/board">자유게시판</a></div>
                  <div class="find"><a href="/interest/interestList">관심 지역 둘러보기</a></div>
                  <div class="find"><a href="/interesthouse/interesthouseList">찜한 매물</a></div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <p class="copyright">220518 관통프로젝트 송다경 & 이주희</p>
      </footer>
    </div>
  </b-row>
</template>

<script>
export default {};
</script>

<style scoped>
/* footer */
.find-us {
  margin-top: 30px;
  position: relative;
  bottom: 0;
  background-color: rgb(248, 248, 248);
  color: gray;
  width: 100%;
  height: 150px;
  line-height: 150px;
}

.findElementsImg {
  display: inline-block;
  margin-left: 20px;
  width: 160px;
  height: 145px;
  line-height: 145px;
  margin-bottom: 25px;
}

.ssafy {
  width: 170px;
  height: 90px;
  margin: 0;
}

.findElements {
  display: inline-block;
  margin-left: 10px;
}

.footernav {
  display: inline-block;
  margin-left: 20%;
  color: gray;
}

.findtitle {
  font-size: 20px;
  text-decoration: dashed;
  margin-top: 0px;
}

.findadd {
  font-size: 13px;
  margin-top: 10px;
}

.find {
  margin-top: 5px;
  font-size: 13px;
}

.find a {
  text-decoration: none;
  color: gray;
}

.copyright {
  position: relative;
  bottom: 0;
  background-color: rgb(224, 223, 223);
  color: gray;
  width: 100%;
  height: 50px;
  line-height: 50px;
}

.copyright {
  font-size: 12px;
  text-align: center;
}

p.copyright {
  margin: 0;
}

body {
  height: 100% !important;
  background-color: #fff !important;
  padding: 0 !important;
  margin: 0 !important;
}

.container-fluid {
  margin-top: 101px;
  color: #000 !important;
}

li {
  margin-bottom: 2px;
  font-size: 13px;
}

li:first-child {
  font-size: 15px !important;
  font-weight: bold;
  margin-bottom: 7px !important;
  color: #757575 !important;
}

footer {
  color: #757575 !important;
}

.cursor-pointer {
  cursor: pointer !important;
  color: rgb(47, 48, 47);
  font-weight: bold;
}
</style>
