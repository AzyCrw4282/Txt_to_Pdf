import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import org.junit.Before;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Unit_tests {

    @Test
    public void txt_to_pdf(){
        Document document = null;
        try {
            String dest = "C:\\Users\\User1\\OneDrive - Royal Holloway University of London\\Others/sample.pdf";
            PdfWriter writer = new PdfWriter(dest);
            // Creating a PdfDocument
            PdfDocument pdfDoc = new PdfDocument(writer);
            // Adding a new page
            pdfDoc.addNewPage();
            // Creating a Document
            document = new Document(pdfDoc);
        } catch (Exception e) {
            System.out.println(" :(( Error creating pdf document! Please check the relative dir you set on line 23");
        }

        try {
            //A relative dir
            ArrayList<String> myList = new ArrayList<>();
            File directory = new File("./");
            System.out.println(directory.getAbsolutePath());
            //A relative dir approach
            File file = new File("../Gear_set_project/src/main/resources/input_file.txt");
//            File file = new File("C:\\Users\\User1\\OneDrive - Royal Holloway University of London\\Others\\Gear_set_project\\src\\main\\resources\\input_file.txt");
            Scanner sc = new Scanner(file);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" :(( Error loading input file!!!");
        }
        // Closing the document

        Paragraph para = new Paragraph();
        para.setFirstLineIndent(45);

        para.add(new Text("Hello world"));
        document.add(para);
        document.close();
        System.out.println("PDF Created");
    }

    @Test
    public void test_all(){

    }

    @Test
    public void set_to_large(){

    }
//    @Test
//    void Paragraph create_paragraph(){
//        Paragraph para = new Paragraph();
//        return (para);
//    }
    @Test
    public void add_indentation(){

    }
    @Test
    public  void set_to_bold(){


    }
    @Test
    public void set_to_italic(){


    }
    @Test
    public void set_to_regular(){


    }
    @Test
    public void set_to_nofill(){

        //activates default. called after each formating option
    }
    @Test
    public void set_to_fill(){


    }
    @Test
    public void require_new_page(){


    }

}
