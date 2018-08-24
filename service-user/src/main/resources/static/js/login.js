var vue = new Vue({
	el:"#content",
	data:{
		name:'',
		password:''
	},
	methods:{
		transformRequest: function (data) {
		      var ret = ''
		      for (var it in data) {
		          ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
		      }
		      return ret
		},
		login:function(){
			var url = "/login";
			var param = {
				"telphone":this.name,
				"userPassword":this.password
			};
			axios.post(url,param)
			.then(function(data){
				if(data == null || data.data.resCode == '00100001'){
					alert("用户名或密码错误");
				}else{
					window.location.href = "/main";
				}
			}).catch(function(data){
				alert("登录失败");
			})
		}
	}
})