package servlets;

import controller.Problem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "addProblemServlet")
public class addProblemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try  {
            // Set the MIME type for the response message
            response.setContentType("text/html");
            // Get a output writer to write the response message into the network socket
            Problem problem = new Problem();
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            //<problema>
            Element rootElement = doc.createElement("problema");
            doc.appendChild(rootElement);
                //<date>
            Element date = doc.createElement("date");
            rootElement.appendChild(date);
                //<titlu>
            Element titlu = doc.createElement("titlu");
            titlu.appendChild(doc.createTextNode(request.getParameter("titlu")));
            date.appendChild(titlu);
                //<enunt>
            Element enunt = doc.createElement("enunt");
            enunt.appendChild(doc.createTextNode(request.getParameter("enunt")));
            date.appendChild(enunt);
                //rezolvare
            Element rezolvare = doc.createElement("rezolvare");
            rezolvare.appendChild(doc.createTextNode(request.getParameter("rezolvare")));
            date.appendChild(rezolvare);
                //categorie
            Element categorie = doc.createElement("categorie");
            categorie.appendChild(doc.createTextNode(request.getParameter("nivel")));
            date.appendChild(categorie);
            //teste
            Element teste = doc.createElement("teste");
            rootElement.appendChild(teste);
                //test1_in
            Element test1_in= doc.createElement("test1_in");
            test1_in.appendChild(doc.createTextNode(request.getParameter("input1")));
            teste.appendChild(test1_in);
                //test1_out
            Element test1_out= doc.createElement("test1_out");
            test1_out.appendChild(doc.createTextNode(request.getParameter("output1")));
            teste.appendChild(test1_out);
                //test2_in
            Element test2_in= doc.createElement("test2_in");
            test2_in.appendChild(doc.createTextNode(request.getParameter("input2")));
            teste.appendChild(test2_in);
                //test2_out
            Element test2_out= doc.createElement("test2_out");
            test2_out.appendChild(doc.createTextNode(request.getParameter("output2")));
            teste.appendChild(test2_out);
                 //test3_in
            Element test3_in= doc.createElement("test3_in");
            test3_in.appendChild(doc.createTextNode(request.getParameter("input3")));
            teste.appendChild(test3_in);
                //test3_out
            Element test3_out= doc.createElement("test3_out");
            test3_out.appendChild(doc.createTextNode(request.getParameter("input3")));
            teste.appendChild(test3_out);
            problem.addProblem(doc);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.println("<p>success<p>");
    }

}
