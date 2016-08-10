<!DOCTYPE html>
<html>

<head>
	<meta charset="ISO-8859-1">
	<meta name="google-signin-client_id" content="999739768442-0rctm8u0igcqtr96dppr2ftur64jhp18.apps.googleusercontent.com">
	<title>Google Sign In Example</title>
	<!-- <link rel="stylesheet" type="text/css" href="css/index.css" />
 -->
	<script src="https://apis.google.com/js/platform.js" async defer></script>
	<script type="text/javascript">
	function onSignIn(googleUser) {
		var profile = googleUser.getBasicProfile();
		var id_token = googleUser.getAuthResponse().id_token;
		console.log(id_token);
		console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
		console.log('Name: ' + profile.getName());
		console.log('Image URL: ' + profile.getImageUrl());
		console.log('Email: ' + profile.getEmail());
		console.log('family name: '+ profile.getFamilyName());
		xmlRequest(id_token);
	}

	function xmlRequest(id_token) {
		var xhr = new XMLHttpRequest();
		xhr.open('POST',
				'https://www.googleapis.com/oauth2/v3/tokeninfo?id_token='
						+ id_token);
		xhr.setRequestHeader('Content-Type',
				'application/x-www-form-urlencoded');
		xhr.onload = function() {
			console.log('Signed in as: ' + xhr.responseText);
		};
		xhr.send();
	}

	function signOut() {
		var auth2 = gapi.auth2.getAuthInstance();
		auth2.signOut().then(function() {
			console.log('User signed out.');
		});
	}

	function getNewXmlHttp() {
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xmlhttp;
	}

	function login() {
		var emailId = document.getElementById("emailId").value;
		var password = document.getElementById("password").value;
		var xmlhttp = getNewXmlHttp();
		console.log("In login")

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var text = xmlhttp.responseText;
				console.log("hello");
				alert(text);
			}
		}

		xmlhttp.open("POST","http://localhost:8081/libraryManagement/api/Login?emailId="
								+ emailId+"&password="+password, true);

		xmlhttp.setRequestHeader("Content-type", "text/plain");

		xmlhttp.send();
	}
</script>

</head>

<body>

	<div id="login">
		<div id="normal">
			<p>Log In or Create an Account</p>

			<p>Email</p>
			<input type="email" name="email" placeholder="Email" class="box" id="emailId" required />
			<p>Password</p>
			<input type="password" name="password" class="box" placeholder="Password" id="password" required />
			<button value="Log In" onclick="login();">LogIn</button>
		</div>
		Sign In Using Google
		<div id="google">
			<div class="g-signin2" data-onsuccess="onSignIn"></div>
		</div>
		<a href="register.html">Register</a>

	</div>

	<a href="#" onclick="signOut();">Sign out</a>
</body>

</html>