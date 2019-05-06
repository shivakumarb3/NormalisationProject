<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
    <%@ page import="service.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Highest Normalisation</title>
</head>
<body>
<%
String left=request.getParameter("finalvalue");
String right=request.getParameter("finalvalue2");

String relation=request.getParameter("attributes");


HashSet<String>h=new HashSet<String>();
StringTokenizer st=new StringTokenizer(relation,",");
 while(st.hasMoreTokens())
 {
	 h.add(st.nextToken());
 }
 
 st=new StringTokenizer(left,"-");
 StringTokenizer vt=new StringTokenizer(right,"-");
 out.println("<br>"+"relational attributes");
 out.println("<br>"+h);
 HashMap<ArrayList<String>,ArrayList<String>>rel=new HashMap<ArrayList<String>,ArrayList<String>>();
 
int flag=0;
 while(st.hasMoreTokens())
 {
	 
	 ArrayList<String>l=new ArrayList<String>();
	 ArrayList<String>r=new ArrayList<String>();
	 StringTokenizer pt=new StringTokenizer(st.nextToken(),",");
	 StringTokenizer rt=new StringTokenizer(vt.nextToken(),",");
	 
	 
	 while(pt.hasMoreTokens())
	 {
		 
		String v=pt.nextToken();
		
		if(!h.contains(v)){
			out.println("please enter the functional dependencies that matches with the relational attributes. If you enter relational attributes and attributes in functional dependencies different, then we cant process the result. ");
			flag=1;
			break;
		}
		else
			l.add(v);
	 }
	 if(flag!=1){
	 while(rt.hasMoreTokens())
	 {
		 
		String v=rt.nextToken();
		
		if(!h.contains(v)){
			out.println("please enter the functional dependencies that matches with the relational attributes");
			flag=1;
			break;
		}
		else
			r.add(v);
	 }
	 }
	 
	 if(flag==1)
		 break;
	 else{
		 if(rel.get(l)==null)
	 rel.put(l,r);
		 else{
			 ArrayList<String> s=new ArrayList<String>();
			 s=rel.get(l);
			 s.addAll(r);
			 rel.put(l,s);
		 }
	 
	 }
	 
	 
 }
 if(flag!=1){
out.println("<br>"+"functional dependencies");
out.println(rel);
Nfcheck c=new Nfcheck();
String res=c.nfc(h,rel);
out.println("<br>"+res);
 }
%>
<jsp:include page="index.html" />
</body>
</html>