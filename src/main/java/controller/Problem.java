package controller;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.*;
import java.util.List;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import storage.Database;


/*
 * setup pt a merge:
 * am instalat mysql
 * am adaugat baza de date {coltul din dreapta sus unde scrie database) unde am adaugat nume/username/parola ale bazei de date create anterior si am pornit-o
 * am adaucat la File->Project structure->Module->Dependencies jar-ul (se gaseste in folderul din C unde este instalat mysql sau daca nu trebuie descarcat de pe net*/

public class Problem {
    Connection myConn = Database.getConnection();

    // returnez un document XML
    public static Document createXML(ResultSet rs)
            throws ParserConfigurationException, SQLException {
        //se creeaza un nou document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        //elementul ROOT
        //radacina documentului XML
        Element results = doc.createElement("Teste");
        doc.appendChild(results);
        //Obținem meta-datele
        //acestea sunt folosite pentru a structura documentul
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();

        while (rs.next()) {   //adaugam elementele de tip Row
            Element row = doc.createElement("Row");
            results.appendChild(row);

            for (int i = 1; i <= colCount; i++) {
                String columnName = rsmd.getColumnName(i); //numele coloanei
                Object value = rs.getObject(i); //vaoarea coloanei
                //un nou element este creat pentru fiecare coloana si adaugat la randul corespunzator
                Element node = doc.createElement(columnName);
                node.appendChild(doc.createTextNode(value.toString()));
                row.appendChild(node);
            }
        }
        /*
        // sciem continutul intr-un fisier XML
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("E:\\teste.xml"));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return doc;
    }

    //o testare simpla sa vad daca merge
    public void testIfFunctioning() {

        try {
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("select * from enunturi_probleme");
            while (myRs.next()) {
                System.out.println(myRs.getString("titlu") + ": " + myRs.getString("enunt"));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public String getEnuntProblema(int id) {
        try {
            //Statement myStmt = myConn.createStatement();
            PreparedStatement statement = myConn.prepareStatement("SELECT * FROM enunturi_probleme" + " WHERE id = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                //System.out.println(rs.getString("enunt"));
                return rs.getString("enunt");
            }
            return rs.getString("enunt");
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }

    public Document getTeste(int id) {
        Document doc = null;
        try {
            //Statement myStmt = myConn.createStatement();
            PreparedStatement statement = myConn.prepareStatement("select test1_in, test1_out, test2_in, test2_out, test3_in, test3_out, " +
                    "test4_in, test4_out from teste_probleme" + " WHERE id_problema = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            doc = createXML(rs);

            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doc;
    }

    public int getPunctaj(int userId, int problemId) {
        try {
            PreparedStatement statement = myConn.prepareStatement("select punctaj from punctaje WHERE id_utilizator = ? and id_problema = ?");
            statement.setInt(1, userId);
            statement.setInt(2, problemId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) return rs.getInt("punctaj");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // nu avem punctaj pentru problema data
    }

    int getAvailableIdForInsertion(PreparedStatement stmt) {
        //depinde daca o sa reciclam id-urile nefolosite sau vom folose autoincrement
        //momentan se gaseste cel mai mai mare id disponibil
        int availableId;
        try {
            ResultSet rs = stmt.executeQuery("select max(id) + 1 as maxId from enunturi_probleme");
            if (rs.next()) {
                if (rs.getString("maxId") == null)
                    return 1;
                else
                    availableId = Integer.parseInt(rs.getString("maxId"));
                return availableId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public void addProblem(Document problemXML) {

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();


            //Get available id for problem
            PreparedStatement stmt = myConn.prepareStatement("select max(id) + 1 as maxId  from enunturi_probleme");
            int problemAvailableId = getAvailableIdForInsertion(stmt);

            //Get available id for test
            stmt = myConn.prepareStatement("select max(id) + 1 as maxId  from teste_probleme");
            int testAvailableId = getAvailableIdForInsertion(stmt);


            stmt = myConn.prepareStatement("insert into enunturi_probleme values(" +
                    Integer.toString(problemAvailableId) + ",'" +
                    problemXML.getElementsByTagName("titlu").item(0).getTextContent() + "','" +
                    problemXML.getElementsByTagName("enunt").item(0).getTextContent() + " ',' " +
                    problemXML.getElementsByTagName("rezolvare").item(0).getTextContent() + "'," +
                    Integer.parseInt(problemXML.getElementsByTagName("categorie").item(0).getTextContent()) + ")");
            stmt.executeUpdate();

            stmt = myConn.prepareStatement("insert into teste_probleme values(" +
                    Integer.toString(testAvailableId) + "," +
                    Integer.toString(problemAvailableId) + ",'" +
                    problemXML.getElementsByTagName("test1_in").item(0).getTextContent() + "','" +
                    problemXML.getElementsByTagName("test1_out").item(0).getTextContent() + "','" +
                    problemXML.getElementsByTagName("test2_in").item(0).getTextContent() + "','" +
                    problemXML.getElementsByTagName("test2_out").item(0).getTextContent() + "','" +
                    problemXML.getElementsByTagName("test3_in").item(0).getTextContent() + "','" +
                    problemXML.getElementsByTagName("test3_out").item(0).getTextContent() + "',)");
                   // problemXML.getElementsByTagName("test4_in").item(0).getTextContent() + "','" +
                    //problemXML.getElementsByTagName("test4_out").item(0).getTextContent() + "')");
            stmt.executeUpdate();

        } catch (ParserConfigurationException | SQLException e) {
            e.printStackTrace();
        }
    }
}