package uk.ac.aston.oop.calendar;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Additional tests for AutoFeedback.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ExtraDayTest {

	private Day day;

	@BeforeEach
	public void setUp() {
		this.day = new Day(1);
	}

	// Tests for validTime

	@Test
	public void invalidBeforeStart() {
		assertFalse("An hour before the start should be invalid",
			day.validTime(Day.START_OF_DAY - 1));
	}

	@Test
	public void validAtStart() {
		assertTrue("Exactly on the start should be valid",
			day.validTime(Day.START_OF_DAY));
	}
	
	@Test
	public void validInMiddle() {
		assertTrue("In the middle should be valid",
			day.validTime(13));
	}
	
	@Test
	public void validAtEnd() {
		assertTrue("At the end should be valid",
			day.validTime(Day.FINAL_APPOINTMENT_TIME));
	}
	
	@Test
	public void invalidAfterEnd() {
		assertFalse("After the end should be invalid",
			day.validTime(Day.FINAL_APPOINTMENT_TIME + 1));
	}

	// Tests for makeAppointment + getAppointment

	@Test
	public void makeAppointmentBeforeStart() {
		assertFalse(
			"Making an appointment before the start should not be possible",
			day.makeAppointment(Day.START_OF_DAY - 1, app(1))
		);
		assertNull("A failed appt should not show up",
			day.getAppointment(Day.START_OF_DAY - 1));
	}

	@Test
	public void makeAppointmentAtStart() {
		assertTrue(
			"Making a one-hour appointment at the start should work",
			day.makeAppointment(Day.START_OF_DAY, app(1))
		);
		assertNotNull("A successful appt should show up",
			day.getAppointment(Day.START_OF_DAY));
	}

	@Test
	public void makeAppointmentAtStartTooLong() {
		assertFalse(
			"Making an appointment that goes beyond the end of the day should not work",
			day.makeAppointment(Day.START_OF_DAY, app(Day.MAX_APPOINTMENTS_PER_DAY + 1))
		);
	}
	
	@Test
	public void makeAppointmentAtLastHour() {
		assertTrue(
			"Taking up the last hour of the day should work",
			day.makeAppointment(Day.FINAL_APPOINTMENT_TIME, app(1))
		);
	}
	
	@Test
	public void makeAppointmentBeyondLastHour() {
		assertFalse(
			"Taking a 1-hour appointment after the end of the day should not work",
			day.makeAppointment(Day.FINAL_APPOINTMENT_TIME + 1, app(1))
		);
	}

	@Test
	public void makeAppointmentsOverlap() {
		final Appointment firstTwoHour = app(2);
		assertTrue(
			"Booking 10 to 12 from an empty day should work",
			day.makeAppointment(Day.START_OF_DAY + 1, firstTwoHour)
		);
		assertSame("A successful multi-hour appt should show up in all slots (first hour)",
			firstTwoHour, day.getAppointment(Day.START_OF_DAY + 1));
		assertSame("A successful multi-hour appt should show up in all slots (second hour)",
			firstTwoHour, day.getAppointment(Day.START_OF_DAY + 2));

		assertFalse(
			"Should not be able to book 10-11 if 10-12 is booked",
			day.makeAppointment(Day.START_OF_DAY + 1, app(1))
		);
		assertFalse(
			"Should not be able to book 11-12 if 10-12 is booked",
			day.makeAppointment(Day.START_OF_DAY + 2, app(1))
		);
		assertFalse(
			"Should not be able to book overlapping 9-11 if 10-12 is booked",
			day.makeAppointment(Day.START_OF_DAY, app(2))
		);
		assertTrue(
			"Should be able to book 9-10 if 10-12 is booked",
			day.makeAppointment(Day.START_OF_DAY, app(1))
		);
		assertTrue(
			"Should be able to book 12-13 if 10-12 is booked",
			day.makeAppointment(Day.START_OF_DAY + 3, app(1))
		);
	}

	private Appointment app(int hours) {
		return new Appointment("test", hours);
	}
}
