<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.net.URLEncoder"%> 
<%@ page language="java" contentType="application/x-msdownload; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% 
	response.setContentType("application/x-download"); 
	String filedownload = (String) request.getAttribute("filePath");
	String filedisplay = "synth.pcm";  
	filedisplay = URLEncoder.encode(filedisplay,"UTF-8");  
	response.addHeader("Content-Disposition","attachment;filename=" + filedisplay); 
	OutputStream outp = null;  
	FileInputStream in = null;
	try{
		outp = response.getOutputStream();  
		in = new FileInputStream(filedownload);  
		byte[] b = new byte[1024];  
		int i = 0; 
		while((i = in.read(b)) > 0)  {  
			outp.write(b, 0, i);  
		}
		outp.flush(); 
		out.clear();  
		out = pageContext.pushBody(); 
	}catch(Exception e){
		System.out.println("Error!");  
		e.printStackTrace(); 
	}finally{
		if(in != null){
			in.close();
			in = null;
		}
	}

	%>
</body>
</html>