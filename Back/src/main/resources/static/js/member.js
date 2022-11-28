$(function() {
  $(document).on("click", "#log-btn", function(){
    let inout = $("#log-btn").text();
    if(inout == "로그인"){
      console.log("login");
      console.log("id: " + $("#id").val());
      console.log("pw: " + $("#pw").val());
    } else if(inout == "로그아웃"){
      console.log("logout");
    }
  });

});