# ILIASTableParser

## General
Parses html tables on ILIAS to CSV-files.

## Use case
Any "white and gray" table on ILIAS can be parsed and extracted.
This is especially useful to extract the results of tests or any list of students.

## How-To
Save the page with **all** rows of the table visible as an HTML-document.
Open ILIASTableParser and select the HTML-document.
If parsing was successful, a CSV-file will be created in the same folder.
**Warning** If the folder already contains a results.csv, it will be replaced.

## Dependencies
Parsing is done with jsoup, see https://github.com/jhy/jsoup.
