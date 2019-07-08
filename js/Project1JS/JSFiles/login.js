function login1(){
    event.preventDefault();

    console.log("JS Block");

    let username = document.getElementById("username").value;
    console.log(`Taken from textbox: ${username}`);
    let password = document.getElementById("password").value;
    console.log(`Taken from textbox: ${password}`);

    var urlFC = "http://localhost:8090/Project1webapp2/login.do";
    var urlFC1 = "http://localhost:8090/Project1webapp2/login.do?" + "username=" + username + "&password=" + password;
    var fData = "username=" + username + "&password=" + password;


      ajaxGET(urlFC, fData).then(function (response) {
        console.log("AJAX response: " + response);
        var user = JSON.parse(response);

        if(user == null){
          alert("Invalid login credentials");
        }
        else{
          console.log(user.firstname + " " + user.lastname + " : " + user.username);
        localStorage.setItem("LOGGED_IN_USER", user.username);
        localStorage.setItem("LOGGED_IN_USER_ID", user.id);

        if(user.isManager){
          mOnShow(user.firstname);

          document.getElementById("login").classList.add("hide");
          document.getElementById("login").classList.remove("show");
          document.getElementById("mHome").classList.remove("hide");
          document.getElementById("mHome").classList.add("show");
        }
        else{
          eOnShow(user.firstname);

          document.getElementById("login").classList.add("hide");
          document.getElementById("login").classList.remove("show");
          document.getElementById("eHome").classList.remove("hide");
          document.getElementById("eHome").classList.add("show");
        }
        
        
        }
      }, function(error) {
        console.error("Error with AJAX call: ", error);  
        alert("Error finding user in database");
    });

}