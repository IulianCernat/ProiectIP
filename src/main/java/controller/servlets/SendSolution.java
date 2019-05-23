package controller.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;

@WebServlet(name = "SendSolution")
public class SendSolution extends HttpServlet {

        private String message;
        private HashMap<String, String> testInput;

        //initializare perechi
        public void initializarePerechiIO(){
            testInput = new HashMap<>();
            testInput.put("5 3", "8");
            testInput.put("-1 7", "6");
            testInput.put("7 13", "20");
            testInput.put("0 0", "0");
        }

        public String getPath(String fileName, String directoryname) throws UnsupportedEncodingException {
            String path = this.getClass().getClassLoader().getResource("").getPath();
            String fullPath = URLDecoder.decode(path, "UTF-8");
            String pathArr[] = fullPath.split("/WEB-INF/classes/");
            System.out.println(fullPath);
            System.out.println(pathArr[0]);
            fullPath = pathArr[0];
            String reponsePath = "";
            reponsePath = new File(fullPath).getPath() + File.separatorChar + directoryname + "/" + fileName;
            return reponsePath;
        }

        public boolean compareOutput(String s1, String s2){
            return s1.trim().equals(s2.trim());
        }

        public  int generareScor (String name)  throws IOException{ //  HashMap <int , Boolean >

            int iteratii=0;

            String path = System.getProperty("user.dir") + "/" + name;
            // Use relative path for Unix systems
            File f = new File(path);

            f.getParentFile().mkdirs();
            f.createNewFile();

            initializarePerechiIO();
            int scor = 0;
            FileWriter fileWriter;
            PrintWriter printWriter;
            for (String i : testInput.keySet()){

                fileWriter = new FileWriter(f);
                printWriter = new PrintWriter(fileWriter);
                printWriter.print(i);
                printWriter.close();  //punem in fisierul creat cheia hashmapului
                fileWriter.close();

                Process tempProcess = Runtime.getRuntime().exec("bash " + getPath("sandbox.sh", "compiler"));

                try{
                    tempProcess.waitFor();
                }
                catch(Exception e){
                    return 1000;
                }

                BufferedReader br = new BufferedReader(new FileReader(getPath("docker", "compiler") + "/sandbox/output.txt"));

                String st =  br.readLine();

                //String st="0";

                if(compareOutput(st, testInput.get(i)) == true )    //scor = scor +1;

                    iteratii++;

            }
            return scor;

        }

        public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            // Set response content type
            response.setContentType("text/html");

            PrintWriter out = response.getWriter();
            String cod= request.getParameter("solutionText");
            System.out.println("Solution Text" + cod);
            // punem codul din textbox in fisierul main.c


            PrintWriter scriitor = new PrintWriter( getPath("docker", "compiler") + "/" + "main.c");
            scriitor.println(cod);
            scriitor.close();


            String docType =
                    "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

            int val = generareScor("suma.in");


            out.println(docType +
                    "<html>\n" +
                    "<body>\n" +
                    "<p>" + "Fisier compilat cu succes. Scor: " + val + "</p>" + "\n" +
                    "</body>" +
                    "</html>"
            );



            out.close();

        }


        public void destroy() {
            // do nothing.
        }
    }







