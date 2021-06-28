

aa();
function aa(){
    let cm = document.getElementById("cm");
    let zh = document.getElementById("zh");
    let submit = document.getElementById("submit");

    //绑定事件
    submit.onclick = dd;
    cm.onclick = bb;
    zh.onclick = cc;
    cm.onmouseover = over;
    cm.onmouseout = out;
    zh.onmouseover = over;
    zh.onmouseout = out;
}
//显示二维码
function bb() {
    let cm = document.getElementById("cm");
    cm.style.textDecoration = 'none';
    let zh = document.getElementById("zh");
    let lm = document.getElementById("login-model");
    let ewm = document.getElementById("erweima");
    lm.setAttribute('hidden','');
    ewm.removeAttribute('hidden');
    document.getElementById("submit").setAttribute('hidden','');
}
//显示登录框
function cc() {
    let zh = document.getElementById("zh");
    zh.style.textDecoration = 'none';
    let cm = document.getElementById("cm");
    let lm = document.getElementById("login-model");
    let ewm = document.getElementById("erweima");
    ewm.setAttribute('hidden','');
    lm.removeAttribute('hidden');
    document.getElementById("submit").removeAttribute('hidden');
}
//提交表单
function dd(){
    let user = {};
    user.username= document.getElementById("loginusername").value ;
    user.password = document.getElementById("loginpassword").value ;
    var data = JSON.stringify(user);
    $.ajax({
        type:"post",
        url:"/ssm/user/login",
        contentType:"application/json;charset=utf-8",
        dataType:"json",
        data:data,
        success:function (result) {
            alert(result.code+result.message);
            if(result.code===1){
                location.href="../page/a.html";
            }
        },
        error:function (error) {
            alert("登录失败");
        }
    })
    return false
}
//鼠标移动到元素上面
function over(){
    if(this.style.color !== 'orange'){
        this.style.color = 'orange';
    }
}
//鼠标移除
function out(){
    if(this.style.color === 'orange'){
        this.style.color = 'black';
    }
}
//表单提交
function aaa(){

    return false;
}

