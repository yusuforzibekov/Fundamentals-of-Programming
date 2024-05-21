package edu.epam.fop.io;

import java.io.File;
import java.util.Map;
import java.util.function.Predicate;

public interface SymbolsDistributor {

  void distribute(File inputFile, Map<Predicate<Integer>, File> outputMapping);
}
