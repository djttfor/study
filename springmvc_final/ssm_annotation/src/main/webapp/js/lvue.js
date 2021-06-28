Vue.component('custom-input', {
    props: ['value'],
    template: `
    <input 
      v-bind:value="value"
      v-on:input="$emit('input', $event.target.value)"
    >
  `
})
Vue.component('ass', {
    data()  {
        return {count : 0,hehe : 'lisi'}
    },
    computed:{
        r1(){
            return '今晚打老虎';
        }
    },
    methods:{
        m1(){
            this.count++;
            this.$emit('cl5');
        }
    },
    watch:{
        count(nv,ov){
            //alert(`旧值:${ov},新值:${nv}`)
        }
    },
    props:['title','myName','fuck'],
    template: '<button >{{count + r1 +title}}</button>'
});
var vue = new Vue({
    el: '#app',
    data: {
        message: "hello world",//aaa
        enable: false,
        user:
            {'name':'jimmy','password':'123',
                'accounts':['aaa','bbb']},
        pv: '',
        ci: '',
        cindex :0,
        use:true,
        t1:'p1',
        t2:'s1',
        vm1:true,
        c1:false,
        c2:false,
        c3:false,
        vm2:[],
        selected:[],
        meter:0,
        kmeter:0,
        b1:0,
        la1:'',
        em1:0,
        cui:'',
        demo1:{'name':'','sex':'女'},
        ms:'',
        pvs:['北京','上海','广东','广西'],
        cities:[
            ['天安门','中南海','王府井','中关村'],
            ['浦东','s1','s2','s3'],
            ['广州','深圳','湛江','中山','佛山'],
            ['南宁','河池','马山','柳州','桂林']
        ],
    },
    methods:{//方法
       pvss(){
           return {color:this.c1,fontSize:this.p1+'px'};
       },
        cl1:function (){
           this.b1++;
           alert('input被点击了')
        },
        cl2(){
           alert("div1被点击了")
        },
        cl3(){
            alert("div2被点击了")
        },
        cl4(){
            alert("div3被点击了")
        },
        cl5(){
            this.em1++;
        },
        c11(){
            for (let i = 0; i < 5; i++) {
                if(this.vm2.indexOf(i+1)===-1){
                    this.vm2[i]=i+1+'';
                }
            }
        },
        c31(){
            for (let i = 0; i < 5; i++) {
                let value = i+1+'';
                let index = this.vm2.indexOf(value);
                if(index===-1){//不存在
                    this.vm2.push(value);
                }else{//存在
                    this.vm2.splice(index,1);
                }
            }
        }

    },
    computed:{
        returnMessage1(){
            return this.message+'，computed';
        },
        m1:{
            set(nv){
                this.message = nv+'m1额外';
            },
            get(){
                return this.message;
            }
        },
        s2(){
            return {color:this.c1,fontSize:this.p1+'px'};
        }
    },
    watch :{
        kmeter(val) {
            this.kmeter = val;
            this.meter = val*1000;
        },
        meter(val) {
            this.meter = val;
            this.kmeter = val/1000;
        },
    },

});

//省市联动
vue.$watch('pv',function (newv) {
    let v = vue.$data;
    v.cindex = newv;
    v.ci='';
});
