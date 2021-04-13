const l1_uri = '/user/l1/';
function toL1(str) {
    if(!str){
        alert("不能跳转空界面")
        return;
    }
    $.ajax({
            type:"get",
            url:`${l1_uri+str}`,
            //dataType:"json",
            success: function (result) {
                alert(Object.values(result))
            },
            error:function (error) {
                alert(error)
            }
        }
    )
}
