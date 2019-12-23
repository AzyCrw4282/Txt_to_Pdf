import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

import java.io.*;

import com.sun.javafx.font.FontFactory;
import javafx.scene.text.Font;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


/** Solution for converting text input to a formatted pdf file
 * @Author Azky Mubarack. Contains adapted code from here https://www.tutorialspoint.com/itext/itext_adding_paragraph.htm
 */

public class TextToPdf {

    private static Document document;
    private static boolean text_available;
    private static Paragraph para = new Paragraph();
    private static boolean add_it = false;

    /**
     * Main runnable class
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        process_text();
    }

    private static void process_text() {
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
            System.out.println(" :(( Error creating pdf document! Please check the relative dir set on line 23");
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
            boolean already_scanned = false;
            String cmd_or_text = "";
            //FileInputStream input_stream = new FileInputStream(file);
            String str_builder = "";

            while (sc.hasNextLine()) {
                if (!already_scanned) {
                    cmd_or_text = sc.nextLine();
                    already_scanned = true;
                }

                if (cmd_or_text.charAt(0) == '.') {
                    myList.add(cmd_or_text.substring(1));//extracts the cmd
                    already_scanned = false;

                }
                else {//if its a word to work with
                    do {
                        if (!already_scanned) cmd_or_text = sc.nextLine();
                        if (cmd_or_text.charAt(0) == '.'){
                            myList.add(str_builder);
                            perform_operation(myList);
                            myList.clear();//list is cleared for new set of cmds and text
                            str_builder = "";
                            already_scanned = true;
                            break;
                        }
                        else{ //if it's a text
                            str_builder += " " + cmd_or_text;
                            already_scanned = false;

                        }
                    }while (sc.hasNextLine());
                }
            }
            myList.add(str_builder);
            perform_operation(myList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" :(( Error loading input file!!!");
        }
        // Closing the document
        document.close();
        System.out.println("PDF Created");
    }


    //Methods for formatting.
    private static void perform_operation(ArrayList list) throws IOException {
        PdfFont italic_font = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);
        PdfFont bold_font = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        PdfFont regular_font = PdfFontFactory.createFont(StandardFonts.COURIER);
        String cmd;

        String[] array_cmds;
//        Paragraph para = new Paragraph();
        String input_text = (String) list.get(list.size()-1);;
        int indent = 0;

        for (int i = 0; i< list.size()-1 ;i++){//iterate though only the cmds
            cmd = (String) list.get(i);
            System.out.println("cmd: "+ cmd + ". Text: " + input_text);
            if (cmd.substring(0,3).equals("ind") && cmd.substring(0,6).equals("indent")){
                array_cmds = cmd.split(" ");
                cmd = array_cmds[0];
                System.out.println("101 "+ cmd + " " + array_cmds[1]);
                indent = Integer.parseInt(array_cmds[1]);//Gets the n value of indent
            }
            switch (cmd){
                case "large":
                    get_paragraph().add(new Text(input_text).setFontSize(16));
                    add_it = true;
                    break;
                case "paragraph":
                    para = create_new_paragraph();
                    set_paragraph(para);
                    get_paragraph().add(new Text(input_text));

                    break;
                case "fill":
                    //Not needed
                    add_it = true;
                    break;
                case "nofill":// new paragrapgh,
                    create_new_paragraph().add(new Text(input_text));
                    break;
                case "regular":
                    get_paragraph().add(new Text(input_text));
                    add_it = true;
                    break;
                case "italics":
                    get_paragraph().add(new Text(input_text).setFont(italic_font));
                    break;
                case "bold":
                    get_paragraph().add(new Text(input_text).setFont(bold_font));
                    break;
                case "normal":
                    create_new_paragraph().setFont(regular_font);
                    break;
                case "indent":
                    add_indentation(get_paragraph(),indent);
                    break;

            }
            //only add with a condition of ane pargrapfg
            if(list.size()-2 == i && add_it){
                document.add(para);//adds it to document
                add_it = false;

            }
        }
    }

    private static Paragraph get_paragraph(){
        return (para);
    }

    private static void set_paragraph(Paragraph p){
        para = p;
    }

    private static Paragraph create_new_paragraph(){
        return (para = new Paragraph());
    }

    private static void add_indentation(Paragraph para ,int indent){
//        para.setFirstLineIndent(indent+30);//adds indentation fot the specified amount
        if (indent > 0) para.setMarginLeft(indent+30);
        else para.setMarginLeft(indent-5);
    }

    private static void set_to_bold(Paragraph para ){


    }

    private static void set_to_italic(Paragraph para ){


    }

    private static void set_to_regular(Paragraph para ){


    }

    private static void set_to_nofill(Paragraph para ){

        //activates default. called after each formating option
    }

    private static void set_to_fill(Paragraph para ){


    }

    private static void require_new_page(Paragraph para ){


    }






}
