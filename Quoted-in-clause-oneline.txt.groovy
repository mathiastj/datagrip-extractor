// This extractor makes it easy to select a bunch of rows and copy paste them into an IN () clause since the rows are always quoted (unless they are numbers).

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
}

