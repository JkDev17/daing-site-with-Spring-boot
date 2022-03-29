/*function dataValidation()
{
	if(true)
	{
		
	  let email= $("#email").val();
	  let password= $("#password").val();
		$.ajax({
			url :  'http://localhost:8080/customAuthentication', 
			type : 'GET',
			async: false,
			data : {
				'email' : email,
				'password' : password,
			},
			success : function(data) { 
					if(data==="Email does not exist, please check the spelling")
					{
						$("#email").css({"border": "2px solid #c31432", "box-shadow": "3px"});  //when left empty giving a border to indicate an error
						$("#email").val("");
						$("#email").attr("placeholder","Email does not exist, check the spelling.");   //when left empty giving the placeholder a text that will indicate that the user has to enter data
						$("#email").click(function() { $("#email").css({"border": "none", "box-shadow": "none"})});
						return false;
					}
					else if(data==="passwords do not match")
					{
						$("#email").css({"border": "2px solid #c31432", "box-shadow": "3px"});  //when left empty giving a border to indicate an error
						$("#email").val("");
						$("#email").attr("placeholder","Email and password do not match.");   //when left empty giving the placeholder a text that will indicate that the user has to enter data
						$("#email").click(function() { $("#email").css({"border": "none", "box-shadow": "none"})});
						return false;
					}
					else if(data==="passwords match")
					{
						alert("YEY");
					}
					
					else
					{
						alert("Ajax request failed");
						return false;
					}
				}, 
				error : function(request,error)
				{
					alert("bad");
				} 
			}
			);
		} 
	event.preventDefault();
  }*/