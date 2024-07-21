package OOP.Encapsulation;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PDFCategorySorter {
    public static Map<String, List<Integer>> getPagesByCategory(String filePath) throws IOException {
        Map<String, List<Integer>> categoryPages = new HashMap<>();
        PDDocument document = PDDocument.load(new File(filePath));

        PDFTextStripper pdfStripper = new PDFTextStripper();
        int pageCount = document.getNumberOfPages();

        for (int page = 1; page <= pageCount; page++) {
            pdfStripper.setStartPage(page);
            pdfStripper.setEndPage(page);
            String pageText = pdfStripper.getText(document);
            String[] lines = pageText.split("\n");
            String category =lines[7];
            categoryPages.computeIfAbsent(category, k -> new ArrayList<>()).add(page);
        }

        document.close();
        return categoryPages;

    }

    public static void writeCustomerTypePagesToFile(Map<String, List<Integer>> customerTypePages, String outputPath) throws IOException {
        try (FileWriter writer = new FileWriter(outputPath)) {
            for (Map.Entry<String, List<Integer>> entry : customerTypePages.entrySet()) {
                String customerType = entry.getKey();
                List<Integer> pages = entry.getValue();
                writer.write(customerType + ":");
                for (int i = 0; i < pages.size(); i++) {
                    writer.write(pages.get(i).toString());
                    if (i < pages.size() - 1) {
                        writer.write(",");
                    }
                }
                writer.write(System.lineSeparator());
            }
        }
    }
}
