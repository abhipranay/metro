package com.zendesk.metro.processing.contracts;

import com.zendesk.metro.processing.data.InfoTuple;

import java.io.IOException;
import java.text.ParseException;

/**
 * In case in future data is provided from some other source or format then That source should
 * implement this interface
 */
public interface IStationDataReader {
    Iterable<InfoTuple> readData() throws IOException, ParseException;
}
