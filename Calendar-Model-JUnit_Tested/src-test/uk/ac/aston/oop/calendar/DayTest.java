package uk.ac.aston.oop.calendar;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * The test class DayTest.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DayTest {

	@Test
	public void appointmentStartOfDay() {
		// Arrange
		Day day = new Day(1);
		Appointment app = new Appointment("Meeting", 1);
		// Act
		boolean success = day.makeAppointment(Day.START_OF_DAY, app);
		Appointment fetched = day.getAppointment(Day.START_OF_DAY);
		// Assert
		assertTrue(success, "Making an appointment at the start of a new day should work");
		assertSame(app, fetched, "It should be possible to fetch the appointment we just made");
	}

	@Test
	public void appointmentBeforeStartOfDay() {
		// Arrange
		Day day = new Day(1);
		Appointment app = new Appointment("Meeting", 1);
		// Act
		boolean success = day.makeAppointment(Day.START_OF_DAY - 1, app);
		Appointment fetched = day.getAppointment(Day.START_OF_DAY - 1);
		// Assert
		assertFalse(success, "Making an appointment before the start of a new day should not work");
		assertNull(fetched, "A failed appointment should not be saved in the day");
	}
	
	@Test
	public void twoAppsOnSameTime() {
		// Arrange
		Day day = new Day(1);
		Appointment appA = new Appointment("Meeting A", 1);
		Appointment appB = new Appointment("Meeting B", 1);

		boolean successA = day.makeAppointment(Day.START_OF_DAY + 2, appA);
		Appointment fetchedA = day.getAppointment(Day.START_OF_DAY + 2);
		boolean successB = day.makeAppointment(Day.START_OF_DAY + 2, appB);
		Appointment fetchedB = day.getAppointment(Day.START_OF_DAY + 2);

		assertTrue(successA, "Making an appt in an empty slot should work");
		assertSame(appA, fetchedA, "Fetching the appointment made in an empty slot should work");
		assertFalse(successB, "Making an appt in an occupied slot should not work");
		assertSame(appA, fetchedB, "Fetching the appointment made in an occupied slot should return the old appt");
	}

	@Test
	public void twoHourAppointmentAtStart() {
		Day day = new Day(1);
		Appointment app = new Appointment("Meeting", 2);

		boolean success = day.makeAppointment(Day.START_OF_DAY, app);
		Appointment fetchedA = day.getAppointment(Day.START_OF_DAY);
		Appointment fetchedB = day.getAppointment(Day.START_OF_DAY + 1);

		assertTrue(success, "Making a 2-hour appt at the beginning of an empty day should work");
		assertSame(app, fetchedA, "The booked 2-hour appt should be visible in its first hour");
		assertSame(app, fetchedB, "The booked 2-hour appt should be visible in its second hour");
	}
	
	@Test
	public void twoHourAppointmentBeyondEnd() {
		Day day = new Day(1);
		Appointment app = new Appointment("Meeting", 2);

		boolean success = day.makeAppointment(Day.FINAL_APPOINTMENT_TIME, app);
		Appointment fetchedA = day.getAppointment(Day.FINAL_APPOINTMENT_TIME);
		Appointment fetchedB = day.getAppointment(Day.FINAL_APPOINTMENT_TIME + 1);

		assertFalse(success, "Making a 2-hour appt that goes beyond the end of the day should not be possible");
		assertNull(fetchedA, "An invalid 2-hour appt beyond the end of the should not have been registered in its first hour");
		assertNull(fetchedB, "An invalid 2-hour appt beyond the end of the should not have been registered in its second hour");
	}

	@Test
	public void overlappingTwoHourAppointments() {
		Day day = new Day(1);
		Appointment appA = new Appointment("Original", 2);
		Appointment appB = new Appointment("OverlapBefore", 2);
		Appointment appC = new Appointment("OverlapAfter", 2);

		boolean successA = day.makeAppointment(11, appA);
		boolean successB = day.makeAppointment(10, appB);
		boolean successC = day.makeAppointment(12, appC);
		Appointment fetched10 = day.getAppointment(10);
		Appointment fetched11 = day.getAppointment(11);
		Appointment fetched12 = day.getAppointment(12);
		Appointment fetched13 = day.getAppointment(13);

		assertTrue(successA, "Making a 2-hour appt from 11 to 13 on an empty day should work");
		assertFalse(successB, "Making a 10-12 appt when 11-13 is taken should not work");
		assertFalse(successC, "Making a 13-15 appt when 11-13 is taken should not work");
		assertNull(fetched10, "The failed 10-12 appt should not have registered");
		assertSame(appA, fetched11, "The successful 11-13 appt should have stayed on the 11-12 slot");
		assertSame(appA, fetched12, "The successful 11-13 appt should have stayed on the 12-13 slot");
		assertNull(fetched13, "The failed 13-15 appt should not have registered");
	}

}
