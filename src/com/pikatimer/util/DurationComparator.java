/* 
 * Copyright (C) 2017 John Garner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.pikatimer.util;

import java.time.Duration;
import java.util.Comparator;
import java.util.logging.Logger;

/**
 *
 * @author jcgarner
 */
public class DurationComparator implements Comparator {
    private final static Logger LOGGER = Logger.getLogger("Pikatimer");
    @Override
    public int compare(Object o1, Object o2) {
        LOGGER.info("DurationComparator called...");
        if (!(o1 instanceof Duration) || !(o2 instanceof Duration))
        {
            throw new ClassCastException(); 
            //return 0;
        }
        Duration d1 = (Duration)o1;
        Duration d2 = (Duration)o2;
        //LOGGER.info("Comparing two durations: " + d1.toString() +"("+d1.isZero()+")" + " vs " + d2.toString()+"("+d2.isZero()+")" + " default result of " + d1.compareTo(d2));

        
//        if (d1.isZero() || d1.isZero()) {
//            if (d1.isZero() && d2.isZero()) {
//                LOGGER.info("  Both are zero, returning 0");
//                return 0;
//            }
//            if (d1.isZero() && d1.compareTo(d2) == -1) { 
//                LOGGER.info("  d1 is zero, returning 1 instead of -1");
//                return 1;
//            } else if (d2.isZero() && d1.compareTo(d2) == 1 ){
//                LOGGER.info("  d2 is zero, returning -1 instead of 1");
//                return -1;
//            }
//            
//            
//        }
//        
        //LOGGER.info("  returning default...");
        return d1.compareTo(d2);

    }
}