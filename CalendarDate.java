// CalendarDate.java

public class CalendarDate {

  // The parameter count: the order of the days in the calendar
  // make the whole calendar 371 days and the first day is set with 'count = -1'
  // Jan-1 is set with 'count = 1' and the last day in the calendar is set with 'count = 369'
  // label with day with corresponding date in the form just like 'Jan-1'
  // Return: the date in the form of a String

  public String getDate(int count) {
    String dateString = "";
    if (count <= 0) {
      dateString = "Dec-" + (count + 31);
    } else if (count <= 31) {
      dateString = "Jan-" + count;
    } else if (count <= 59) {
      dateString = "Feb-" + (count - 31);
    } else if (count <= 90) {
      dateString = "Mar-" + (count - 59);
    } else if (count <= 120) {
      dateString = "Apr-" + (count - 90);
    } else if (count <= 151) {
      dateString = "May-" + (count - 120);
    } else if (count <= 181) {
      dateString = "Jun-" + (count - 151);
    } else if (count <= 212) {
      dateString = "Jul-" + (count - 181);
    } else if (count <= 243) {
      dateString = "Aug-" + (count - 212);
    } else if (count <= 273) {
      dateString = "Sep-" + (count - 243);
    } else if (count <= 304) {
      dateString = "Oct-" + (count - 273);
    } else if (count <= 334) {
      dateString = "Nov-" + (count - 304);
    } else if (count <= 365) {
      dateString = "Dec-" + (count - 334);
    } else if (count > 365) {
      dateString = "Jan-" + (count - 365);
    }
    return dateString;
  }


}
