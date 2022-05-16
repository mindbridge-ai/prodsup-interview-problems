package ai.mindbridge.interview.prodsup.steps.cleaning;

import ai.mindbridge.interview.prodsup.exceptions.CsvProcessingException;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This is the main class for the cleaning step. It ensures every row has the
 * same number of columns as the header row.
 */
@Component
public class CleaningService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String DEBIT = "debit";
    private static final String CREDIT = "credit";
    private static final String ACCOUNT = "account";

    public void processFile(String filename, String outputFile) {
        logger.info("Processing file {}", filename);
        CsvParser parser = openCsv(filename);
        int rowNumber = 0;
        try {
            rowNumber += 1;
            int headerLength = parser.parseNext().length;

            while (true) {
                rowNumber += 1;
                String[] row = parser.parseNext();
                if (row == null) {
                    break;
                }

                if (row.length < headerLength) {
                    // Pad the row so the array is the same length as the header
                    row = Arrays.copyOf(row, headerLength);
                }

                // not shown: code to output the new CSV file
            }
        }
        catch (Exception e) {
            throw new CsvProcessingException(rowNumber, "Failed to process file", e);
        }
        finally {
            parser.stopParsing();
        }
        logger.info("Processed file {}: {} rows, and created output file {}", filename, rowNumber, outputFile);
    }

    private CsvParser openCsv(String filename) {
        var parser = new CsvParser(new CsvParserSettings());
        parser.beginParsing(new File(filename), StandardCharsets.UTF_8);
        return parser;
    }
}
