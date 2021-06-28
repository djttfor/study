
$(()=>{
    let ip1 = $('#d11 input:nth-child(1)');
    let ip2 = $('#d11 input:nth-child(2)');
    let ip3 = $('#d11 input:nth-child(3)');
    ip1.click(()=>{
        $('#u11 li input').prop('checked',true);
    })
    ip2.click(()=>{
        $('#u11 li input').prop('checked',false);
    });
    ip3.click(()=>{
        let a = $('#u11>li>input');
        a.each(function () {
           $(this).prop('checked',!$(this).is(':checked'))
        })
    })

    a2();
    a3();
    cpld();
    function a2(){
    $('#cm').click(function (){
        $(this).css("color","yellow")
            .css("textDecoration","none");
        $('#zh').css("color","black")
        $('#erweima').show(500);
        $('#login-model').hide(500);
    });
}
    function a3(){
        $('#zh').click(function (){
            $(this).css("color","yellow")
                .css("textDecoration","none");
            $('#cm').css("color","black")
            $('#erweima').hide(500);
            $('#login-model').show(500);
        });
    }
    function cpld(){
        let pvs = ['北京','上海','广东','广西'];
        let citys = [
            ['天安门','中南海','王府井','中关村'],
            ['浦东','s1','s2','s3'],
            ['广州','深圳','湛江','中山','佛山'],
            ['南宁','河池','马山','柳州','桂林']
        ];
        let provinces = $('#provinces');
        let city = $('#city');
        for(let i=0;i<pvs.length;i++){
            provinces
                .append('<option value='+i+'>'+pvs[i]+'</option>');
        }
        citys[0].forEach((v,i)=>{
            city
                .append('<option value='+i+'>'+v+'</option>');
        });
        provinces.change(()=>{
            city.empty();
            citys[provinces.val()].forEach((v,i)=>{
                city
                    .append('<option value='+i+'>'+v+'</option>');
            });
        });

    }
})

