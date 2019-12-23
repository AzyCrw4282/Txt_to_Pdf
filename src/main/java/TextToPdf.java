import java.io.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/** Solution for converting text input to a formatted pdf file using iText 7 library.
 * Please see my README file for explanation on how this works.
 * @Author Azky Mubarack.
 * Used this as guidance https://www.tutorialspoint.com/itext/ to create the solution
 */

public class TextToPdf {

    private static Document document;
    private static Paragraph para = new Paragraph();
    private static boolean add_it = false;

    /**
     * Main runnable class
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        process_text();//calls a method to process the text
    }

    /**
     * Method which processes the input file and creates the output pdf file.
     * --Please see my README file for the logic behind this method. ---
     */
    private static void process_text() {
        try {
            File dir = new File("../new_file.pdf");//A relative dir sets the output pdf outside the current root directory
            PdfWriter writer = new PdfWriter(dir);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.addNewPage();
            document = new Document(pdfDoc);
        } catch (Exception e) {
            System.out.println(" :(( Error creating pdf document! Please check the relative dir set on line 23");
        }

        try {
            ArrayList<String> myList = new ArrayList<>();//list which folds the commands and texts
            File file = new File("../Gear_set_project/src/main/resources/input_file.txt");//loads in input file
            Scanner sc = new Scanner(file);
            boolean already_scanned = false;
            String cmd_or_text = "";
            String str_builder = "";

            while (sc.hasNextLine()) {//Scanner processes in the input token
                if (!already_scanned) {//if its not already scanned scan the line
                    cmd_or_text = sc.nextLine();
                    already_scanned = true;
                }

                if (cmd_or_text.charAt(0) == '.') {//Adds a command to a list
                    myList.add(cmd_or_text.substring(1));//extracts the cmd
                    already_scanned = false;
                }
                else {//if its a word to work with
                    do {//I use this while loop to detect paragraphs which are then concatenated to the string builder
                        if (!already_scanned) cmd_or_text = sc.nextLine();
                        if (cmd_or_text.charAt(0) == '.'){
                            myList.add(str_builder);
                            perform_operation(myList);//calls the method with the list as argument.
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
            perform_operation(myList);// This handles edge-case in which, no next line is present but something is in the list
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" :(( Error loading input file!!!");
        }
        document.close();//closes the document
        System.out.println("PDF Created");
    }

    /**
     * This handles formatting for a given list of commands and text.
     * @param list taken in an array list of commands and text
     * @throws IOException when it fails to write to the document
     */
    private static void perform_operation(ArrayList list) throws IOException {
        PdfFont italic_font = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);
        PdfFont bold_font = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
        PdfFont regular_font = PdfFontFactory.createFont(StandardFonts.COURIER);
        String cmd;
        String[] array_cmds;
        String input_text = (String) list.get(list.size()-1);//Saves the text which is always the last element in the list
        int indent = 0;

        for (int i = 0; i< list.size()-1 ;i++){//Iterates through the list
            cmd = (String) list.get(i);
            if (cmd.substring(0,3).equals("ind") && cmd.substring(0,6).equals("indent")){//Saves the indent value
                array_cmds = cmd.split(" ");
                cmd = array_cmds[0];
                indent = Integer.parseInt(array_cmds[1]);//Gets the value of indent, e.g. +2, -4, etc.
            }
            switch (cmd){//switch case handles all the formatting option. Gets the appropriate paragraph and adds the text
                case "large":
                    get_paragraph().add(new Text(input_text).setFontSize(16));
                    add_it = true;//This is used to check if the text should be added to the document
                    break;
                case "paragraph":
                    para = create_new_paragraph();
                    set_paragraph(para);
                    get_paragraph().add(new Text(input_text));
                    break;
                case "fill":
                    //Not used
                    add_it = true;
                    break;
                case "nofill":
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
            //Adds to the document if add_it is set to *True* and its running the last command
            if(list.size()-2 == i && add_it){
                document.add(para);//adds it to document
                add_it = false;
            }
        }
    }

    /**
     * Gets a new paragraph
     * @return an existing paragraph
     */
    private static Paragraph get_paragraph(){
        return (para);
    }

    /**
     * Sets a paragraph. Used to make sure cmds are applied to the right paragraph
     * @param p
     */
    private static void set_paragraph(Paragraph p){
        para = p;
    }

    /**
     * Create a new paragraph
     * @return a new paragraph object
     */
    private static Paragraph create_new_paragraph(){
        return (para = new Paragraph());
    }

    /**
     * Adds the required indentation, handling + and - cases.
     * @param para is the existing paragraph to add this formatting
     * @param indent value of the indent
     */
    private static void add_indentation(Paragraph para ,int indent){
//        para.setFirstLineIndent(indent+30);//adds indentation fot the specified amount
        if (indent > 0) para.setMarginLeft(indent+30);
        else para.setMarginLeft(indent-5);
    }
}
