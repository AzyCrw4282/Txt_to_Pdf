import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.junit.Test;
import com.itextpdf.layout.element.Text;
import org.junit.Before;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import static javafx.beans.binding.Bindings.when;
import static org.junit.Assert.assertThat;


/**Main test class to test all functionalities.
 * @Author Azky
 */
public class Unit_tests {
    private static Paragraph para = new Paragraph();
    private static String input_test = "Test Me!!!!";
    private static Document document;

    /**
     * Tests loading and saving a file to a disk using a relative path method.
     */
    @Before
    public void txt_to_pdf(){
        try {
            File dir = new File("../input_file_test.pdf");
            PdfWriter writer = new PdfWriter(dir);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.addNewPage();
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
            File file = new File("../Gear_set_project/src/main/resources/input_file_test.txt");
            Scanner sc = new Scanner(file);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" :(( Error loading input file!!!");
        }
        Paragraph para = new Paragraph();
        para.setFirstLineIndent(45);
        para.add(new Text("Hello world"));
        document.add(para);
        System.out.println("PDF Created");
    }
    /**
     * Test to check setting font to an increased size.
     */
    @Test
    public void set_to_large(){
        para.add(new Text(input_test).setFontSize(16));
        document.add(para);
        document.close();
    }
    /**
     * Test to check indentation
     */
    @Test
    public void add_indentation(){
        para.add(new Text(input_test).setFontSize(16));
        para.setMarginLeft(30);
        document.add(para);
        document.close();

    }
    /**
     * Test to check setting text to bold
     */
    @Test
    public  void set_to_bold(){
        para.add(new Text(input_test).setBold());
        document.add(para);
        document.close();
    }
    /**
     * Test to check setting text to italic font
     */
    @Test
    public void set_to_italic(){
        para.add(new Text(input_test).setItalic());
        document.add(para);
        document.close();
    }
    /**
     * Test to check setting font to regular
     */
    @Test
    public void set_to_regular(){
        para.add(new Text(input_test));
        document.add(para);
        document.close();
    }

    //This is based on the mockito testing library for java
//An example of writing a test for behavioul driven development(BDD)
    public void testSomething() {
        when(helloWorld.say()).thenReturn("Something Cool");
        String result = subject.say();
        assertThat(result, is("Something Cool"));
    }

}
