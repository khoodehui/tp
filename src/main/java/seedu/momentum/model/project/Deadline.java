package seedu.momentum.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import seedu.momentum.commons.core.Clock;
import seedu.momentum.commons.core.Date;
import seedu.momentum.commons.core.Time;

/**
 * Represents a Project's deadline in the project book.
 * Guarantees: immutable; is valid
 */
public class Deadline implements Comparable<Deadline> {

    public static final String MESSAGE_CONSTRAINTS = Date.MESSAGE_CONSTRAINTS + "\n" + Time.MESSAGE_CONSTRAINTS;
    private final Optional<Date> date;
    private final Optional<Time> time;

    /**
     * Constructs an empty deadline.
     */
    public Deadline() {
        this.date = Optional.empty();
        this.time = Optional.empty();
    }

    /**
     * Constructs a {@code Deadline}.
     *
     * @param date A valid date.
     */
    public Deadline(String date) {
        requireNonNull(date);
        checkArgument(Date.isValid(date), Date.MESSAGE_CONSTRAINTS);
        this.date = Optional.of(new Date(date));
        this.time = Optional.empty();
    }

    /**
     * Constructs a {@code Deadline}.
     *
     * @param date A valid date.
     * @param time A valid time.
     */
    public Deadline(String date, String time) {
        requireNonNull(date, time);
        checkArgument(Date.isValid(date), Date.MESSAGE_CONSTRAINTS);
        checkArgument(Time.isValid(time), Time.MESSAGE_CONSTRAINTS);
        this.date = Optional.of(new Date(date));
        this.time = Optional.of(new Time(time));
    }

    public boolean isEmpty() {
        return this.date.isEmpty();
    }

    public Date getDate() throws NoSuchElementException {
        return this.date.get();
    }

    public boolean hasTime() {
        return this.time.isPresent();
    }

    public Time getTime() throws NoSuchElementException {
        return this.time.get();
    }

    public String getFormattedDeadline() {
        return isEmpty() ? "No deadline set"
            : this.date.map(Date::getFormatted).orElse("")
            + this.time.map(time -> " " + time.getFormatted()).orElse("");
    }

    /**
     * Gets the number of days to the date of the deadline, from the current time.
     *
     * @return Number of days to deadline.
     */
    public long daysToDeadline() {
        //return Date.getTimeBetween(Clock.now().getDate(), getDate(), ChronoUnit.DAYS);
        return ChronoUnit.DAYS.between(Clock.now().getDate(), getDate().get());
    }

    @Override
    public String toString() {
        return this.date.map(Date::toString).orElse("")
                + this.time.map(Time::toString).orElse("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.date.equals(((Deadline) other).date)
                && this.time.equals(((Deadline) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.date, this.time);
    }

    @Override
    public int compareTo(Deadline other) {

        Date thisDate = this.getDate();
        Date otherDate = other.getDate();

        if (thisDate.get().isBefore(otherDate.get())) {
            return -1;
        } else if (thisDate.get().isAfter(otherDate.get())) {
            return 1;
        } else {
            return sameDateCompare(other);
        }
    }

    /**
     * Compares time of two deadlines with same date.
     *
     * @param other other deadline.
     * @return integer to indicate order.
     */
    private int sameDateCompare(Deadline other) {

        if (!this.hasTime() && other.hasTime()) {
            return -1;
        } else if (this.hasTime() && !other.hasTime()) {
            return 1;
        } else if (this.hasTime() && other.hasTime()) {
            Time thisTime = this.getTime();
            Time otherTime = other.getTime();
            return thisTime.compareTo(otherTime);
        } else {
            return 0;
        }
    }

}