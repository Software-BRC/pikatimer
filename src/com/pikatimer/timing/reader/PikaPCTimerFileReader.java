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
package com.pikatimer.timing.reader;

import com.pikatimer.timing.RawTimeData;
import com.pikatimer.util.DurationFormatter;
import com.pikatimer.util.DurationParser;
import java.time.Duration;
import java.util.logging.Logger;
import javafx.application.Platform;



/**
 *
 * @author jcgarner
 */
public class PikaPCTimerFileReader extends NonTailingReader{
    private final static Logger LOGGER = Logger.getLogger("Pikatimer");
    @Override
    public void process(String s) {
        LOGGER.info("PikaPCTimerFileReader::process: " + s);
        // the file is either pipe or comma delimited
        // and we don't care which one since the rest of it is just numbers
        // and colons 
        String[] tokens = s.split(",", -1); 
        // we only care about the following fields:
        // 0 -- The place
        // 1 -- bib
        // 2 -- time (as a string)
        // 3+ -- it should not be here
        if (tokens.length < 5 ) {
            LOGGER.info("  Unable to parse " + s);
            return;
        }

        // numbers only for the bib. 
        // This also gets rid of the extra spaces and quotes
        String bib = tokens[4].replaceAll("\\D+", ""); 
        
        // one way to get rid of the header line.... 
        if (bib == null || bib.isEmpty()) { // invalid bib
            LOGGER.info("  Empty bib: " + s);
            return;
        }
        if (bib.equals("0")) { // invalid bib
            LOGGER.info("  Zero bib: " + s);
            return;
        }
        
        // strip any extra quotes, spaces, etc
        String time = tokens[3].replaceAll("\\D+", ""); 
        time = time.replaceAll("(\\d\\d)(\\d\\d)(\\d\\d)(\\d\\d)", "$1:$2:$3\\.$4");
        
        LOGGER.info("bib: " + bib + " -> " + time);
        
        
        
        
        
        Duration timestamp = offset; // We get this from the NonTailingReader class
        LOGGER.info("  Offset is: " + offset);


        // First look for timestams without a date attached to them
        if(time.matches("^\\d{1,2}:\\d{2}:\\d{2}\\.\\d{2}$")) {
            if (DurationParser.parsable(time)){ 
                timestamp = timestamp.plus(DurationParser.parse(time));
                //LocalTime timestamp = LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME );
                RawTimeData rawTime = new RawTimeData();
                rawTime.setChip(bib);
                
                rawTime.setTimestampLong(timestamp.toNanos());
                String status = "Added raw time: " + bib + " at " + DurationFormatter.durationToString(timestamp, 3);
                Platform.runLater(() -> {
                    statusLabel.textProperty().setValue(status);
                });
                timingListener.processRead(rawTime); // process it
            } else {
                String status = "Unable to parse the time in " + time;
                LOGGER.info(status);
                Platform.runLater(() -> {
                    statusLabel.textProperty().setValue(status);
                });
            }
        } else {
            String status="Unable to parse the time: " + time;
            LOGGER.info(status);
            Platform.runLater(() -> {
                statusLabel.textProperty().setValue(status);
            });
            
        }

    }
    
   @Override
    public Boolean chipIsBib() {
        return Boolean.TRUE; 
    }
    
}
