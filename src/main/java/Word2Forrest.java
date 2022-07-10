import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.model.ListData;
import org.apache.poi.hwpf.model.ListTables;
import org.apache.poi.hwpf.model.StyleDescription;
import org.apache.poi.hwpf.model.StyleSheet;
import org.apache.poi.hwpf.usermodel.BorderCode;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Word2Forrest {
    Writer _out;
    HWPFDocument _doc;
    public Word2Forrest(HWPFDocument doc, OutputStream stream) throws IOException
    {
        _out = new OutputStreamWriter (stream, StandardCharsets.UTF_8);
        _doc = doc;

        init ();
        openDocument ();
        openBody ();

        Range r = doc.getRange ();
        StyleSheet styleSheet = doc.getStyleSheet ();

        int sectionLevel = 0;
        int lenParagraph = r.numParagraphs ();
        boolean inCode = false;
        for (int x = 0; x < lenParagraph; x++)
        {
            Paragraph p = r.getParagraph (x);
            String text = p.text ();
            if (text.trim ().length () == 0)
            {
                continue;
            }
            StyleDescription paragraphStyle = styleSheet.getStyleDescription (p.
                    getStyleIndex ());
            String styleName = paragraphStyle.getName();
            if (styleName.startsWith ("Heading"))
            {
                if (inCode)
                {
                    closeSource();
                    inCode = false;
                }

                int headerLevel = Integer.parseInt (styleName.substring (8));
                if (headerLevel > sectionLevel)
                {
                    openSection ();
                }
                else
                {
                    for (int y = 0; y < (sectionLevel - headerLevel) + 1; y++)
                    {
                        closeSection ();
                    }
                    openSection ();
                }
                sectionLevel = headerLevel;
                openTitle ();
                writePlainText (text);
                closeTitle ();
            }
            else
            {
                int cruns = p.numCharacterRuns ();
                CharacterRun run = p.getCharacterRun (0);
                String fontName = run.getFontName();
                if (fontName.startsWith ("Courier"))
                {
                    if (!inCode)
                    {
                        openSource ();
                        inCode = true;
                    }
                    writePlainText (p.text());
                }
                else
                {
                    if (inCode)
                    {
                        inCode = false;
                        closeSource();
                    }
                    openParagraph();
                    writePlainText(p.text());
                    closeParagraph();
                }
            }
        }
        for (int x = 0; x < sectionLevel; x++)
        {
            closeSection();
        }
        closeBody();
        closeDocument();
        _out.flush();

    }

    public void init ()
            throws IOException
    {
        _out.write ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
        _out.write ("<!DOCTYPE document PUBLIC \"-//APACHE//DTD Documentation V1.1//EN\" \"./dtd/document-v11.dtd\">\r\n");
    }

    public void openDocument ()
            throws IOException
    {
        _out.write ("<document>\r\n");
    }
    public void closeDocument ()
            throws IOException
    {
        _out.write ("</document>\r\n");
    }


    public void openBody ()
            throws IOException
    {
        _out.write ("<body>\r\n");
    }

    public void closeBody ()
            throws IOException
    {
        _out.write ("</body>\r\n");
    }


    public void openSection ()
            throws IOException
    {
        _out.write ("<section>");

    }

    public void closeSection ()
            throws IOException
    {
        _out.write ("</section>");

    }

    public void openTitle ()
            throws IOException
    {
        _out.write ("<title>");
    }

    public void closeTitle ()
            throws IOException
    {
        _out.write ("</title>");
    }

    public void writePlainText (String text)
            throws IOException
    {
        _out.write (text);
    }

    public void openParagraph ()
            throws IOException
    {
        _out.write ("<p>");
    }

    public void closeParagraph ()
            throws IOException
    {
        _out.write ("</p>");
    }

    public void openSource ()
            throws IOException
    {
        _out.write ("<source><![CDATA[");
    }
    public void closeSource ()
            throws IOException
    {
        _out.write ("]]></source>");
    }

    public static void main(String[] args) throws IOException {
//        try (InputStream is = new FileInputStream("2014.doc");
//             OutputStream out = new FileOutputStream("test.xml")) {
//            new Word2Forrest(new HWPFDocument(is), out);
//        }
        File file = new File("2.doc");
        FileInputStream in = new FileInputStream(file);
        HWPFDocument doc = new HWPFDocument (in);
//        String text = doc.getText().toString();
//        WordExtractor extract = new WordExtractor(doc);
//        String text = extract.getText();
//        System.out.println(text);
        Range range = doc.getRange();
        int numP = range.numParagraphs();
        for (int i = 0; i < numP; ++i) {
            //从每一段落中获取文字
            Paragraph p = range.getParagraph(i);
            if(p.isInTable()){
                continue;
            }
            int Ilvl = p.getIlvl();
            int Ilfo = p.getIlfo();
            int p_run = p.numCharacterRuns();
            int level = p.getLvl();  // 大纲等级
            System.out.println("leve:" + level);
            for(int j=0; j<p_run; ++j){
                CharacterRun r_run = p.getCharacterRun(j);
                String fontName = r_run.getFontName();
                String r_text = r_run.text();
                int fontSize = r_run.getFontSize();
                boolean border = r_run.isBold();
                System.out.println(fontSize + "-" +border);
                System.out.println(Ilvl + ":" +Ilfo + "-" + fontName + ":" + r_text);
            }
        }
    }
}
