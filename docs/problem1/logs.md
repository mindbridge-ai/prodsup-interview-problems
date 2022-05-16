---
title: Log search
---
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.0/css/jquery.dataTables.css">
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.0/js/jquery.dataTables.js"></script>
</head>

## Log search

You can also view the [raw logs](logs.json).

<table id="logs" class="display compact">
</table>

<script>
$(document).ready( function () {
    $('#logs').DataTable({
        ajax: {
            url: "logs.json",
            // The file is an array of objects.
            dataSrc: "",
        },
        columns: [
            { data: "@timestamp", title: "Timestamp", type: "date", className: "dt-left" },
            { data: "severity_label", title: "Severity", className: "dt-left" },
            { data: "thread_name", title: "Thread", className: "dt-left" },
            {
                data: "logger_name",
                title: "Logger name",
                render: function (data) {
                    if (data) {
                        // Shorten to the last component of the class name
                        return data.replace(/^.*\./, "");
                    }
                    return "";
                },
            },
            { data: "message", title: "Message", className: "dt-left" },
            { data: "request_id", title: "request_id", className: "dt-left" },
            { data: "user_id", title: "user_id", className: "dt-left" },
            { data: "tenant_id", title: "tenant_id", className: "dt-left" },
            {
                data: "stack_trace",
                title: "Stack trace",
                className: "dt-left",
                render: function (data) {
                    if (data) {
                        return data.replace(/\n/g, "<br>").replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;");
                    }
                    return "";
                },
            },
        ],
    });
} );
</script>
