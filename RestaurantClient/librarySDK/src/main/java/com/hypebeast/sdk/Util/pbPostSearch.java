package com.hypebeast.sdk.Util;

import com.hypebeast.sdk.api.exception.NotFoundException;
import com.hypebeast.sdk.api.model.popbees.pbpost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by hesk on 9/7/15.
 */
public class pbPostSearch implements Comparator<pbpost>, Comparable<pbpost> {

    public static pbpost findByIdBinary(long id, List<pbpost> l) throws NotFoundException {
        if (l.size() == 0) throw new NotFoundException("not found");
        pbpost o = new pbpost();
        o.id = id;
        final int h = Collections.binarySearch(l, o, new pbPostSearch());
        if (h < 0) throw new NotFoundException("not found");
        return l.get(h);
    }


    public static pbpost findById(long id, List<pbpost> list) throws NotFoundException {
        for (int i = 0; i < list.size(); i++) {
            pbpost l = list.get(i);
            if (l.id == id) {
                return list.get(i);
            }
        }
        throw new NotFoundException("not found");
    }

    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(pbpost another) {
        return 0;
    }

    /**
     * Compares the two specified objects to determine their relative ordering. The ordering
     * implied by the return value of this method for all possible pairs of
     * {@code (lhs, rhs)} should form an <i>equivalence relation</i>.
     * This means that
     * <ul>
     * <li>{@code compare(a,a)} returns zero for all {@code a}</li>
     * <li>the sign of {@code compare(a,b)} must be the opposite of the sign of {@code
     * compare(b,a)} for all pairs of (a,b)</li>
     * <li>From {@code compare(a,b) > 0} and {@code compare(b,c) > 0} it must
     * follow {@code compare(a,c) > 0} for all possible combinations of {@code
     * (a,b,c)}</li>
     * </ul>
     *
     * @param lhs an {@code Object}.
     * @param rhs a second {@code Object} to compare with {@code lhs}.
     * @return an integer < 0 if {@code lhs} is less than {@code rhs}, 0 if they are
     * equal, and > 0 if {@code lhs} is greater than {@code rhs}.
     * @throws ClassCastException if objects are not of the correct type.
     */
    @Override
    public int compare(pbpost lhs, pbpost rhs) {
        return lhs.id == rhs.id ? 1 : 0;
    }
}
