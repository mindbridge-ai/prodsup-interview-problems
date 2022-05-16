package ai.mindbridge.interview.prodsup.simulator;

import java.util.List;
import java.util.Map;

/** Sample data for generating random log messages */
public class SampleData {

    public static final Map<String, List<String>> TENANT_USERS = Map.of(
            "example1", List.of("624b01da0f610570ee2b66d3", "628131e9c22f509de0db2d00", "5fc131f8c22f509de0db2d01"),
            "sample2", List.of("5e713212c22f509de0db2d03", "62813917c22f509de0db2d04", "5f313922c22f509de0db2d05"),
            "demo3", List.of("61d13949c22f509de0db2d07"),
            "tenant4", List.of("60e13956c22f509de0db2d08", "5c3813939c22f509de0db2d06"));

    public static final List<String> FILENAMES =
            List.of("example1", "example2", "example3", "example4", "GL final 2021", "GL FY2020", "GL-FY19",
                    "Balances 2022", "Full GL extract 2021");
}
