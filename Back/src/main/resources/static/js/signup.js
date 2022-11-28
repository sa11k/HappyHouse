$(function() {
  $(document).on("click", "#sign-btn", function(){
    let id = $("#id").val();
    let pw = $("#pw").val();
    let name = $("#name").val();
    let address = $("#address").val();
    let phone = $("#email").val();

    console.log("id: " + id);
    console.log("pw: " + pw);
    console.log("name: " + name);
    console.log("address: " + address);
    console.log("email: " + email);
  });

});