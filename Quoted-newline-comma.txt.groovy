// This extractor quotes everything (unless they are numbers) adds newlines and adds a comma on each newline

/*
 * Available context bindings:
 *   COLUMNS     List<DataColumn>
 *   ROWS        Iterable<DataRow>
 *   OUT         { append() }
 *   FORMATTER   { format(row, col); formatValue(Object, col) }
 *   TRANSPOSED  Boolean
 * plus ALL_COLUMNS, TABLE, DIALECT
 *
 * where:
 *   DataRow     { rowNumber(); first(); last(); data(): List<Object>; value(column): Object }
 *   DataColumn  { columnNumber(), name() }
 */

SEPARATOR = ","
QUOTE     = "'"
NEWLINE   = System.getProperty("line.separator")

first = true
ROWS.each { row ->
  COLUMNS.each { column ->
    value = FORMATTER.format(row, column)
    def q = !value.isNumber()
    OUT.append(first ? "" : SEPARATOR)
       .append(q ? QUOTE : "")
       .append(value.replace(QUOTE, QUOTE + QUOTE))
       .append(q ? QUOTE : "")
    first = false
  }
  OUT.append(NEWLINE)
}