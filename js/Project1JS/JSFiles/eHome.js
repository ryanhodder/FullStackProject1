function eOnShow(firstName){;
    document.getElementById("welcomeTag").innerText = "Welcome " + firstName;
}

function mOnShow(firstName){;
    document.getElementById("mWelcomeTag").innerText = "Welcome " + firstName;
}

function createReimbursement(){
    document.getElementById("eHome").classList.add("hide");
    document.getElementById("eHome").classList.remove("show");
    document.getElementById("reimForm").classList.remove("hide");
    document.getElementById("reimForm").classList.add("show");
}

function submitReimbursement(){
    let amount = document.getElementById("reimAmount").value;
    let userid = localStorage.getItem("LOGGED_IN_USER_ID");
    console.log(amount);

    if(amount < 1 || amount > 1000000){
        alert("Only amounts greater than 0 and less than 1,000,000 will be considered.");
    }
    else{
        var urlFC = "http://localhost:8090/Project1webapp2/submitReimbursement.do";
        var formData = "amount=" + amount + "&userID=" + userid;

        ajaxGET(urlFC, formData).then(function (response){
            alert("Reimbursement submitted");
            //at this point nothing is really returned to say whether a reimbursement has been submitted
            //would change so that atleast an ID is returned
        }, function(error){
            console.error("Error with AJAX call: ", error);
        });
    }    

    document.getElementById("reimForm").classList.add("hide");
    document.getElementById("reimForm").classList.remove("show");
    document.getElementById("eHome").classList.remove("hide");
    document.getElementById("eHome").classList.add("show");
}

function backToHome(thisDiv, homeDiv){
    document.getElementById(thisDiv).classList.add("hide");
    document.getElementById(thisDiv).classList.remove("show");
    document.getElementById(homeDiv).classList.remove("hide");
    document.getElementById(homeDiv).classList.add("show");
}

function logout(thisDiv){
    //remove from localStorage
    localStorage.removeItem("LOGGED_IN_USER");
    localStorage.removeItem("LOGGED_IN_USER_ID");
    document.getElementById(thisDiv).classList.add("hide");
    document.getElementById(thisDiv).classList.remove("show");
    document.getElementById("login").classList.remove("hide");
    document.getElementById("login").classList.add("show");
    document.getElementById("username").value = "";
    document.getElementById("password").value = "";
    document.getElementById("username").focus;
}

function viewDetails(){
    document.getElementById("eHome").classList.add("hide");
    document.getElementById("eHome").classList.remove("show");
    document.getElementById("eDetails").classList.remove("hide");
    document.getElementById("eDetails").classList.add("show");

    var loggedUser = localStorage.getItem("LOGGED_IN_USER");
    var urlFC = "http://localhost:8090/Project1webapp2/getEmployeeInfo.do";
    var formData = "username=" + loggedUser;

    ajaxGET(urlFC, formData).then(function (response) {
        var userDetails = JSON.parse(response);

        document.getElementById("idDetail").innerHTML = "User ID: " + userDetails.id;
        document.getElementById("fnameDetail").innerHTML = "First name: " + userDetails.firstname;
        document.getElementById("lnameDetail").innerHTML = "Last name: " + userDetails.lastname;
        document.getElementById("unameDetail").innerHTML = "Username: " + userDetails.username;

      }, function(error) {
        console.error("Error with AJAX call: ", error);      
    });
}

function viewReimbursements(){
    document.getElementById("eHome").classList.add("hide");
    document.getElementById("eHome").classList.remove("show");
    document.getElementById("reimList").classList.remove("hide");
    document.getElementById("reimList").classList.add("show");

    var loggedUser = localStorage.getItem("LOGGED_IN_USER");
    var urlFC = "http://localhost:8090/Project1webapp2/getReimbursements.do";
    var formData = "username=" + loggedUser;

    ajaxGET(urlFC, formData).then(function(response){
        var reims = JSON.parse(response);
        var tbody = "";

        for(let rObj of reims){
            tbody +="<tr>";
            tbody +="<td>" + rObj.reimbursementID + "</td>";
            tbody +="<td>" + rObj.amount + "</td>";
            tbody +="<td>" + rObj.pending + "</td>";
            tbody +="</tr>";
        }

        document.querySelector("#reimTable>tbody").innerHTML  = tbody;
    }, function(error){
        console.log("Error retrieving reimbursements.");
    });
}

function createNewEmployee(){
    document.getElementById("mHome").classList.add("hide");
    document.getElementById("mHome").classList.remove("show");
    document.getElementById("newEmployeeForm").classList.remove("hide");
    document.getElementById("newEmployeeForm").classList.add("show");
}

function registerEmployee(){
    var newF = document.getElementById("newF").value;
    var newL = document.getElementById("newL").value;
    var newU = document.getElementById("newU").value;
    var newP = document.getElementById("newP").value;
    var urlFC = "http://localhost:8090/Project1webapp2/registerEmployee.do";
    var formData = "firstname=" + newF + "&lastname=" + newL + "&username=" + newU + "&password=" + newP;

    ajaxGET(urlFC, formData).then(function(response){
        var user = JSON.parse(response);
        alert("New employee successfully added.");
    },function(error){
        alert("Error creating new employee.\nPlease try again.");
    });
}

function viewAllEmployee(){
    document.getElementById("mHome").classList.add("hide");
    document.getElementById("mHome").classList.remove("show");
    document.getElementById("empList").classList.remove("hide");
    document.getElementById("empList").classList.add("show");

    var urlFC = "http://localhost:8090/Project1webapp2/viewAllEmployee.do";

    ajaxGETNoForm(urlFC).then(function(response){
        var e = JSON.parse(response);
        var tbody = "";

        for(let eObj of e){
            tbody +="<tr>";
            tbody +="<td>" + eObj.id + "</td>";
            tbody +="<td>" + eObj.firstname + "</td>";
            tbody +="<td>" + eObj.lastname + "</td>";
            tbody +="<td>" + eObj.username + "</td>";
            tbody +="</tr>";
        }

        document.querySelector("#empTable>tbody").innerHTML  = tbody;

    }, function(error){
        console.log("Error retrieving list of employees")
    });
}