 
// Import required java libraries
import java.io.*;
import javax.tools.*;


import javax.servlet.*;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

import java.util.ArrayList;

//import testCaseList;





// Extend HttpServlet class
public class getSolution extends HttpServlet {
 
   private String message;


   private HashMap<Integer,Boolean> rezultat = new HashMap<Integer,Boolean>();
   private String eroareCompilare;

   testCaseList testCases = new testCaseList();

   public void init()  {
      // Do required initialization
   
   }



/*

public void initializarePerechiIO(){

   testCases.flush();

   testCases.addTestCase(1, "5 3","8");
   testCases.addTestCase(2, "-1 7","6");
   testCases.addTestCase(3, "7 13","20");
   testCases.addTestCase(4, "0 0","12");


}

*/


private boolean compareOutput(String s1, String s2){
   return s1.trim().equals(s2.trim());
}




public HashMap<Integer,Boolean> rezultateTestCaseuri (String filename , testCaseList testCases) throws IOException{
  
   String path = "./Docker/" + filename;
   // Use relative path for Unix systems
   File f = new File(path);
 
   rezultat = new HashMap<Integer,Boolean>();
   
   f.getParentFile().mkdirs();
   f.createNewFile();
 
    
         FileWriter fileWriter;
         PrintWriter printWriter;
      
         for(int i = 1 ; i <= testCases.getTestCount() ; i++){
           fileWriter = new FileWriter(f);
           printWriter = new PrintWriter(fileWriter);
           printWriter.print(testCases.getTestInput(i));
           printWriter.close();
 
           Process tempProcess = Runtime.getRuntime().exec("./sandbox.sh");


           String compilerOutput = "";

           BufferedReader stdout = new BufferedReader(new InputStreamReader(tempProcess.getInputStream()));
   
            //asteptam finalizarea procesului creat

            try{
               tempProcess.waitFor();
            }
            catch(Exception e){
               return rezultat;
            }

            //luam outputul de la proces
            String s = null;
            while ((s = stdout.readLine()) != null){
               compilerOutput = compilerOutput + s + "<br>";
            }
 
            stdout.close();



   
            if(compilerOutput.contains("error")==true){ //daca a fost eroare la compilare
               eroareCompilare = compilerOutput.substring(compilerOutput.indexOf("main.cpp"),compilerOutput.lastIndexOf((int)'^')+1);

               rezultat.put(-1,false);
               return rezultat;
            }   
               



                BufferedReader br = new BufferedReader(new FileReader("./Docker/sandbox/output.txt")); 
                String st =  br.readLine();
                br.close();

                if(compareOutput(st, testCases.getTestOutput(i) ) == true ) rezultat.put(i,true) ; 
                    else rezultat.put(i,false);

          }

        return rezultat;
         
        }






   public String getEroareCompilare(){

      return eroareCompilare;

   }


   public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      

      //initializarePerechiIO();


      /********************************************************************************************
       * 
       *  Aici trebuie initializat obiectul testCases.
       * 
       *  testCases=getProblemTests(int id_problema);
       * 
       ********************************************************************************************/

   

      // Set response content type
      response.setContentType("text/html");

      PrintWriter out = response.getWriter();
      String cod= request.getParameter("sol");

      // punem codul din textbox in fisierul main.cpp

      PrintWriter scriitor = new PrintWriter("Docker/main.cpp");

      scriitor.println(cod);

      scriitor.close();




  String docType =
  "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

   //int val = generareScor("suma.in");

   HashMap<Integer, Boolean> result = rezultateTestCaseuri("input.txt", testCases);


   String stringResult = "";

   for (Integer i : result.keySet()) {
      stringResult = stringResult + "key: " + i + " value: " + result.get(i) + "<br>";
    }





    if(result.get(-1)==null)
    out.println(docType +
    "<html>\n" +
       "<body>\n" +
          "<p>" + "Fisier compilat cu succes. " + "<br>" + stringResult + "</p>"  +
       "</body>" +
    "</html>"
 );


else

   out.println(docType +
   "<html>\n" +
      "<body>\n" +
         "<p>" + "Eroare compilare <br>" + eroareCompilare + "</p>" + "\n" +
      "</body>" +
   "</html>"
);


      out.close();

      }


   public void destroy() {
      // do nothing.
   }
}


