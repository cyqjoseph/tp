package seedu.address.model.booking;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateBookingException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents a list of unique bookings.
 */
public class UniqueBookingList implements Iterable<Booking> {

    private final ObservableList<Booking> internalList = FXCollections.observableArrayList();
    private final ObservableList<Booking> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);


    /**
     * Checks if the list contains a given booking.
     *
     * @param toCheck The booking to check for.
     * @return true if the list contains the booking, false otherwise.
     */
    public boolean contains(Booking toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a booking to the list.
     *
     * @param toAdd The booking to add.
     * @throws DuplicatePersonException if the booking already exists in the list.
     */
    public void add(Booking toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes a booking from the list.
     *
     * @param toRemove The booking to remove.
     * @throws PersonNotFoundException if the booking does not exist in the list.
     */
    public void remove(Booking toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Replaces the booking {@code target} in the list with {@code editedBooking}.
     * {@code target} must exist in the list.
     * The booking identity of {@code editedBooking} must not be the same as another existing booking in the list.
     */
    public void setBooking(Booking target, Booking editedBooking) {
        requireNonNull(editedBooking);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException(); // Consider creating a BookingNotFoundException for clarity
        }

        if (!target.equals(editedBooking) && contains(editedBooking)) {
            throw new DuplicatePersonException(); // Consider creating a DuplicateBookingException for clarity
        }

        internalList.set(index, editedBooking);
    }


    /**
     * Returns an unmodifiable view of the list.
     *
     * @return An unmodifiable ObservableList of bookings.
     */
    public ObservableList<Booking> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns an iterator over the elements in this list.
     *
     * @return An iterator.
     */
    public Iterator<Booking> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns the hash code value for this list.
     *
     * @return The hash code value for this list.
     */
    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns a string representation of the list.
     *
     * @return A string representation of the list.
     */
    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code bookings} contains only unique bookings.
     */
    private static boolean bookingsAreUnique(List<Booking> bookings) {
        for (int i = 0; i < bookings.size() - 1; i++) {
            for (int j = i + 1; j < bookings.size(); j++) {
                if (bookings.get(i).equals(bookings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setBookings(List<Booking> bookings) {
        requireNonNull(bookings);
        if (!bookingsAreUnique(bookings)) {
            throw new DuplicateBookingException();
        }
        internalList.setAll(bookings);
    }

}
