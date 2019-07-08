function ajaxGET(url, formData){
    var promiseObj = new Promise(function(resolve, reject){
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function(){
            if(this.readyState == 4 && this.status == 200){
                var response = this.responseText;
                resolve(response);
            }
            else if(this.readyState == 4 && this.status != 200){ 
                reject(this.responseText);}
            }
        xhr.open("GET", url + "?" + formData, true);
        xhr.send();
        });
        return promiseObj;
    }

function ajaxGETNoForm(url){
    var promiseObj = new Promise(function(resolve, reject){
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function(){
            if(this.readyState == 4 && this.status == 200){
                var response = this.responseText;
                resolve(response);
            }
            else if(this.readyState == 4 && this.status != 200){ 
                reject(this.responseText);}
            }
        xhr.open("GET", url, true);
        xhr.send();
        });
        return promiseObj;
    }