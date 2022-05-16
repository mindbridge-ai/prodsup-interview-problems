package ai.mindbridge.interview.prodsup.steps.processing;

import ai.mindbridge.interview.prodsup.exceptions.CsvProcessingException;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This is the main class for the processing step. It loads a file into the database
 * using data from the account, debit, and credit columns.
 */
@Component
public class ProcessingService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String DEBIT = "debit";
    private static final String CREDIT = "credit";
    private static final String ACCOUNT = "account";

    public void processFile(String filename) {
        logger.info("Processing file {}", filename);
        CsvParser parser = openCsv(filename);
        int rowNumber = 0;
        try {
            rowNumber += 1;
            Map<String, Integer> headerMap = findHeaders(parser.parseNext());

            while (true) {
                rowNumber += 1;
                String[] row = parser.parseNext();
                if (row == null) {
                    break;
                }

                String account = row[headerMap.get(ACCOUNT)];
                Optional<BigDecimal> debit = parseNumber(row, headerMap.get(DEBIT));
                Optional<BigDecimal> credit = parseNumber(row, headerMap.get(CREDIT));

                // If account is present, and debit/credit, store the row.
                if (StringUtils.isNotBlank(account)
                    && (debit.isPresent() || credit.isPresent())) {
                    // store row in database (not shown here)
                }
            }
        }
        catch (Exception e) {
            throw new CsvProcessingException(rowNumber, "Failed to process file", e);
        }
        finally {
            parser.stopParsing();
        }
        logger.info("Processed file {}: {} rows", filename, rowNumber);
    }

    private CsvParser openCsv(String filename) {
        var parser = new CsvParser(new CsvParserSettings());
        parser.beginParsing(new File(filename), StandardCharsets.UTF_8);
        return parser;
    }

    /** Builds a map from header name to column number. */
    private Map<String, Integer> findHeaders(String[] headerRow) {
        Map<String, Integer> headerMap = new HashMap<>();
        for (int i = 0; i < headerRow.length; ++i) {
            if (StringUtils.isNotBlank(headerRow[i])) {
                headerMap.put(headerRow[i], i);
            }
        }
        return headerMap;
    }

    private Optional<BigDecimal> parseNumber(String[] row, int position) {
        if (StringUtils.isNotBlank(row[position])) {
            return Optional.of(new BigDecimal(row[position]));
        }
        return Optional.empty();
    }
}
