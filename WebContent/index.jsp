<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>
	function jump(url){
		window.location.href=url;
	}
</script>
	<center>
		<input type="button" value="语音合成" onclick=javascript:jump("tts.jsp") />	<br><br>
		<input type="button" value="语音识别" onclick=javascript:jump("asr.jsp") />	<br><br>
		<input type="button" value="手写识别" onclick=javascript:jump("hwr.jsp") />	<br><br>
		<input type="button" value="身份证图片识别" onclick=javascript:jump("idcard.jsp") />	<br><br>
		<input type="button" value="银行卡图片识别" onclick=javascript:jump("bankcard.jsp") />	<br><br>
		<input type="button" value="名片图片识别" onclick=javascript:jump("bizcard.jsp") />	<br><br>
		<input type="button" value="文本图片识别" onclick=javascript:jump("text.jsp") />	<br><br>
		<input type="button" value="人脸图片识别" onclick=javascript:jump("face.jsp") />	<br><br>
		<input type="button" value="声纹识别" onclick=javascript:jump("voiceprint.jsp") />
	</center>
</body>
</html>