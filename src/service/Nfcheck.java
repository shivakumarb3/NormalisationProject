package service;
import java.util.*;
public class Nfcheck
{
   public static String nfc(HashSet<String> relation, HashMap<ArrayList<String>,ArrayList<String>> h)
  {
   // Scanner as=new Scanner(System.in);
	//System.out.println("enter number of attributes in a relation");
	//int r=as.nextInt();
    //HashSet<String> relation=new HashSet<String>();
	//System.out.println("enter relation attributes");
	//for(int i=0;i<r;i++)
		//relation.add(as.next());
	//System.out.println("enter relation attributes");
	//System.out.println("enter number of dependencies");
	//int n=as.nextInt();
    //System.out.println("enter left hand side and right hand side");
	
	//HashMap<ArrayList<String>,ArrayList<String>>h=new HashMap<ArrayList<String>,ArrayList<String>>();
	//System.out.println("enter dot before entering right hand production");
	/*for(int i=0;i<n;i++)
	{
		System.out.println("enter dot before entering right hand production");
		ArrayList<String> left=new ArrayList<String>();
	    ArrayList<String> right=new ArrayList<String>();
		String s=as.next();
		while(!(s.equals(".")))
		{
			left.add(s);
			s=as.next();
		}
		System.out.println("enter dot at last");
		 s=as.next();
		while(!(s.equals(".")))
		{
			right.add(s);
			s=as.next();
		}
		h.put(left,right);
	} */
	System.out.println(h);
	HashSet<String>prime =new HashSet<String>();
	ArrayList<HashSet<String>>cand=findprime(h,relation,prime);
	System.out.println("candidate keys");
	System.out.println(cand);
	System.out.println("prime attributes");
	System.out.println(prime);
   boolean twonf=findtwonf(cand,prime,h);
    boolean threenf,bcnf;
    if(twonf==true)
	{
		threenf=findthreenf(cand,prime,h);
		if(threenf==true)
		{
			bcnf=findbcnf(cand,prime,h);
			if(bcnf==true)
			return "candidate keys are "+cand+"<br> prime attributes are "+prime+" <br> relation is in BCNF";
            else
             return "candidate keys are "+cand+"<br> prime attributes are "+prime+" <br> relation is in 3NF";			
			}
			else
				return "candidate keys are "+cand+"<br> prime attributes are "+prime+" <br> relation is in 2NF";
	}
	else
		return "candidate keys are "+cand+"<br> prime attributes are "+prime+" <br> relation is in 1NF";
  }
 public static  ArrayList<HashSet<String>> findprime(HashMap<ArrayList<String>,ArrayList<String>> h,HashSet<String> relation,HashSet<String>prime)
  {
	  int min=1000;
	  ArrayList<HashSet<String>> cand=new ArrayList<HashSet<String>>();
	  //HashSet<String>prime =new HashSet<String>();
	  
	  for(ArrayList<String>left: h.keySet())
	  {		  
 
          HashSet<String> main =new HashSet<String>();
	      HashSet<String> dup =new HashSet<String>();
         for(String s:left)
		 {
	  
	       main.add(s);
		   dup.add(s);
	     }
		 
		 //System.out.println("dup"+dup);
		 ArrayList<String>right=h.get(left);
		 for(String s:right)
		 dup.add(s);
	   
	     findclousre(dup,h);
		 
		 for(String s:relation)
		 {
		    if(!dup.contains(s))
			{
				main.add(s);
				dup.add(s);
				
				
				findclousre(dup,h);
	         }
		 }
		 
		 if(main.size()<min)
		 {
			 min=main.size();
			 prime.clear();
			 for(String s: main)
				 prime.add(s);
			 cand.clear();
			 cand.add(main);
			 
		 }
		else if(main.size()==min)
		 {
			 for(String s:main)
				 prime.add(s);
			 cand.add(main);
		 }
		 
	  }
	  return cand;
  }
public static void findclousre(HashSet<String> dup,  HashMap<ArrayList<String>,ArrayList<String>> h)
{
  
   for(ArrayList<String>left:h.keySet())
	  {		
         int flag=0;
        for(String s:left)
		{
			if(!dup.contains(s))
			{
				flag=1;
				break;
			}
		} 
         if(flag==0){
        
        ArrayList<String>right=h.get(left);
		 for(String s:right)
		 dup.add(s);
		 }
	  }	
}
public static boolean findtwonf(ArrayList<HashSet<String>> cand, HashSet<String> prime, HashMap<ArrayList<String>,ArrayList<String>> h) {
	
	 int mainflag=0;
	for(ArrayList<String> left : h.keySet())
	{
		ArrayList<String> right= h.get(left);
		int flag=0;
		for(String s: right)
		{
			if(!prime.contains(s))
			{
				flag=1;
				break;
			}
			
				
	   }
	   HashSet<String> check=new HashSet<String>();
	   for(String s: left)
		   check.add(s);
	   if(flag==1)
	   {
		   if(propersubset(check,cand))
			   mainflag=1;
		   
	   }
	   if(mainflag==1)
		   return false;
	}
	return true;
}
	   
		  
	   
	
public static boolean propersubset(HashSet<String> check,ArrayList<HashSet<String>> cand)
{
	int flag=1;
	for(HashSet<String> c : cand)
	{
		 if(check.size()>=c.size())
			 continue;
		 else
		 {
			 for(String s: check){
				if(!c.contains(s)){
				  flag=0;
				  break;
				}
			    
			  }
			  if(flag==1)
				  return true;
			 
		 }
		 
		}	
		return false;
}
public static boolean findthreenf(ArrayList<HashSet<String>> cand, HashSet<String> prime, HashMap<ArrayList<String>,ArrayList<String>> h) {
	int mainflag=0;
	for(ArrayList<String> left: h.keySet())
	{
		int flag=1;
		for(String s: h.get(left))
			if(!prime.contains(s)){
				flag=0;
				break;
			}
		if(flag==0){		
		HashSet<String> check =new HashSet<String>();
		for(String s:left)
			check.add(s);
		if(!findcand(check,cand))
			return false;
		}
		
		
	}
	return true;
}
public static boolean findbcnf(ArrayList<HashSet<String>> cand, HashSet<String> prime, HashMap<ArrayList<String>,ArrayList<String>> h) {
   for(ArrayList<String> left:h.keySet()){
   HashSet<String> check =new HashSet<String>();
		for(String s:left)
			check.add(s);
		if(!findcand(check,cand))
			return false;
	
      }
	  return true;
}
public static boolean findcand(HashSet<String> check,ArrayList<HashSet<String>> cand){
	
	
	
	for(HashSet<String> c : cand)
		
	{
		int flag=0;
		if(c.size()>check.size())
			return false;
		else{
			for(String s: c)
				if(!check.contains(s)){
					flag=1;
					break;
				}
			if(flag==0)
				return true;
		 }
	
		
			
      }
	  return false;
}
public static void main(String args[])
{
	Nfcheck c= new Nfcheck();
	HashSet<String> h=new HashSet<String>();
	h.add("a");
	h.add("b");
	h.add("c");
	h.add("d");
	HashMap<ArrayList<String>,ArrayList<String>> rel=new HashMap<ArrayList<String>,ArrayList<String>>();
	ArrayList<String> l=new ArrayList<String>();
	ArrayList<String> r=new ArrayList<String>();
	l.add("a");
	r.add("b");
	rel.put(l,r);
	l=new ArrayList<String>();
	r=new ArrayList<String>();
	l.add("c");
	r.add("d");
	rel.put(l,r);
	System.out.println(c.nfc(h,rel));
	
	
	
	
}
}
		 
		
		
		
	
    