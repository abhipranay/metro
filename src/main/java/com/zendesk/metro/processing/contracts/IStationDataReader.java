package com.zendesk.metro.processing.contracts;

import com.zendesk.metro.processing.InfoTuple;

import java.io.IOException;
import java.text.ParseException;

public interface IStationDataReader {
    Iterable<InfoTuple> readData() throws IOException, ParseException;
}
