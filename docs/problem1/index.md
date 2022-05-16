# Problem 1

## Overview

You've received a problem report. A user encountered an error while processing
a file. The system provided an error message, but it wasn't sufficient for
the user to resolve the problem on their own.

Use the logs to identify the steps the user took, then go as far as you can to
identify the cause and a workaround. Open a ticket with the information
(steps, workaround, cause).

## Background

The application is a busy multi-tenant app that allows users to process
CSV files.

The application allows users to upload a file and then perform a series of
processing steps. Some of the steps are optional. Each step produces
another CSV file that the user can feed into subsequent steps.

### Processing steps
First the user runs an *upload* step to upload a CSV file.

The user can optionally run a *cleaning* step. The cleaning step removes some
junk that can cause problems in a CSV file. This produces another CSV file.

The user can run a *processing* step. This takes a CSV file and imports it
into the database.

### Logs
The application logs each user action with the tenant ID, user ID,
and timestamp. The logs are searchable by any of these fields
as well as basic text.

Each request is logged with a unique request ID, so you can
find related messages from the same request. When the user performs an action,
the action and any errors will be logged as separate messages, but with the
same request ID.

For privacy reasons, the user email address is not logged, but you can
look up their user ID in the [user database](users.md).

You can [view the logs online](logs.md).

### Source code

Source code is available in the [Git repository]({{ site.github.repository_url }}/tree/develop/problem1)
under `problem1`. Once you find the error message, you might be able
to find some additional information in the source code that could help
with a workaround.

## Scenario

The user `user5@example.com` encountered an error while working with a CSV file
in the application.  The error message they received was a generic
system error, "Failed to process file", so they opened a ticket with the
support team.

We know the user uses the tenant with tenant ID `example1`. Their email address
is `user5@example.com` and you can find their user ID in the [user database](users.md).

The team doesn't know what time the error occurred, and it's not clear what
steps they took: we don't know which step they were running when they hit an
error, or what file they were processing the time.

The customer shared the [files](files) they were working with. One of these
files is the one they were having trouble with.

Use the [logs](logs.md) to identify the steps the user took, then go as far as
you can to identify the cause and a workaround. A good workaround would be
something the user can do to their CSV file to make it process successfully or
some other step they could take.

When you have the information you need, you can open a ticket by emailing your
interviewers.  Make sure to include the steps the user took, whatever you were
able to determine for the cause, and any workarounds you found. You can use
[Markdown
formatting](https://docs.github.com/en/get-started/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax)
to format the ticket in your email.
